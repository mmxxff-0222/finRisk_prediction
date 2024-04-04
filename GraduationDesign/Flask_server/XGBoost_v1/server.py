import pickle

import pandas as pd
from flask import Flask, request, jsonify
import joblib
import numpy as np
from flask_cors import CORS

app = Flask(__name__)
CORS(app)
# 加载机器学习模型
with open('XGBoost_v1.pkl', 'rb') as f:
    # 使用pickle.load()从文件中读取序列化的对象并还原为原来的Python对象
    model = pickle.load(f)


@app.route('/hello', methods=['GET'])
def hello():
    response = {
        'code': 200,
        'message': "模型成功预测风险等级",
        'data': {'predict': 2}
    }
    return response

@app.route('/postTest', methods=['POST'])
def postTest():
    data = request.json
    print(data)
    response = {
        'code': 200,
        'message': "模型成功预测风险等级",
        'data': {'predict': 2}
    }
    return response

# 定义路由
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
    app.run(debug=True,port=8787)
