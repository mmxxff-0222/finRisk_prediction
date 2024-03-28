import pickle

from flask import Flask, request
import joblib
import numpy as np

app = Flask(__name__)
# 加载机器学习模型
with open('/Flask_server/XGBoost_v1/XGBoost_v1.pkl', 'rb') as f:
    # 使用pickle.load()从文件中读取序列化的对象并还原为原来的Python对象
    model = pickle.load(f)

# 定义预测函数
def predict(features):
    # 预处理特征
    # features = preprocess_features(features)
    # 进行预测
    prediction = model.predict(np.array([features]))
    return prediction


# 定义路由
@app.route('/predict', methods=['GET'])
def make_prediction():
    # 获取 GET 请求中的特征值
    feature1 = float(request.args.get('feature1'))
    feature2 = float(request.args.get('feature2'))
    # 依次获取剩余的特征值

    # 调用预测函数进行预测
    # prediction = predict([feature1, feature2, ...])

    # 返回预测结果
    return f'{feature1},{feature2}'


if __name__ == '__main__':
    app.run(debug=True)
