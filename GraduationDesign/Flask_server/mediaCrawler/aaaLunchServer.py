import pickle
import asyncio
import pandas as pd
from flask import Flask, request, jsonify
import joblib
import numpy as np
from flask_cors import CORS
from quart_cors import cors
import xhsSearch
from quart import Quart, request
from xhsSearch import CrawlerFactory

# flask
app = Flask(__name__)

CORS(app)
# quart
# app = Quart(__name__)
# app = cors(app)

# # # 在调用异步函数前创建事件循环
# loop = asyncio.new_event_loop()
# asyncio.set_event_loop(loop)
# app.config["EVENT_LOOP"] = loop

# def uvicorn_run():
#     asyncio.set_event_loop(asyncio.new_event_loop())
# def begin():
#

# 加载机器学习模型
with open('XGBoost_v1.pkl', 'rb') as f:
    # 使用pickle.load()从文件中读取序列化的对象并还原为原来的Python对象
    model = pickle.load(f)




# @app.route('/crawler', methods=['GET'])
# async def crawler():
#     # 5483d3762e1d93421eaa2917
#     xhsID = request.args.get('xhsID')
#     print(xhsID)
#     result = await xhsSearch.search999(xhsID)
#
#     if result == "error":
#         code = 400
#         message = "爬取异常"
#     else:
#         code = 200
#         message = f"{xhsID}信息爬取成功"
#
#     print(xhsID)
#     response = {
#         "code": code,
#         "message": message
#     }
#     return str(response)

@app.route('/crawler2', methods=['GET'])
def crawler2():
    print("ok!")
    crawler = CrawlerFactory()
    result = crawler.search999('555f430362a60c2c9a17ee16') # 爬虫
    print(result)
    return result

@app.route('/predict', methods=['POST'])
def make_prediction():
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
        "data" : { "predict" : prediction["result"][0]}
    }
    return str(response)


if __name__ == '__main__':
    app.run(port=8787)
