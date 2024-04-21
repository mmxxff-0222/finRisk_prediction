from multiprocessing import cpu_count
from sanic import Sanic
from sanic.response import json
from sanic_limiter import Limiter, get_remote_address, RateLimitExceeded
from xhsSearch import CrawlerFactory
import pandas as pd
import pickle

app = Sanic(__name__)
limiter = Limiter(app, key_func=get_remote_address, strategy="moving-window")

# 加载机器学习模型
with open('XGBoost_v1.pkl', 'rb') as f:
    # 使用pickle.load()从文件中读取序列化的对象并还原为原来的Python对象
    model = pickle.load(f)

@app.exception(RateLimitExceeded)
def handle_over_rate_limit(request, exception):
    return json({"status": -1, "message": f"RateLimitExceeded({exception},{request})"}, status=429)

def returns(request, msg="参数错误"):
    if "成功" in msg:
        status = 1
    else:
        status = 2
    return json({
        "url": request.url,
        "query_string": request.query_string,
        "args": request.args,
        "status":str(status),
        "status_msg": str(msg),
        "task_status": "2",
    })

@app.route("/mt_price_info")
@limiter.limit("10000/second;10000/minute")
async def mt_price_info(request):
    creator_id = request.args.get('creator_id')
    if not creator_id:
        response = {
            "code": 400,
            "message": "ERROR: Creator ID is required'",
        }
        return json(response)
    else:
        # 调用异步函数并将协程对象转换为响应对象
        factory = CrawlerFactory()
        await factory.search999(str(creator_id).replace("\"", ""))
    return returns(request)

@app.route('/predict', methods=['POST'])
def make_prediction(request):
    # 获取 GET 请求中的特征值
    data = request.json
    print(data)
    features_df = pd.DataFrame([data['features']], index=[0])  # 假设特征信息以 'features' 字段传递
    # print(features_df.T)
    # 调用预测函数进行预测
    prediction: pd.DataFrame = model.predict(features_df)
    # print('result:',prediction)
    # 返回预测结果
    response = {
        "code" : 200,
        "message" : "模型成功预测风险等级",
        "data" : { "predict" : str(prediction["result"][0])}
    }
    return json(response)

@app.route('/hello', methods=['GET'])
def crawler(request):
    print("ok!")
    return json({'hello': 'world'})

if __name__ == '__main__':
    app.run(port=8787, debug=False, auto_reload=True, workers=cpu_count())


