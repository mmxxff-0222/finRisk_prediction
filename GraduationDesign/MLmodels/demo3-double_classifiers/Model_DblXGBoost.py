from xgboost import XGBClassifier
from sklearn.base import BaseEstimator, RegressorMixin
import pandas as pd

class DblXGBoost(BaseEstimator, RegressorMixin):
    def __init__(self, params1={}, params2={}):
        self.params1 = params1
        self.params2 = params2
        self.model1 = XGBClassifier(**self.params1)
        self.model2 = XGBClassifier(**self.params2)

    def fit(self, X:pd.DataFrame, y:pd.DataFrame):
        df_loan = pd.concat([X,y],axis=1)
        # 设置分类器1的target : 按label分类，无风险（0）和有风险（1、2、3）分成两类
        df_loan['risk'] = df_loan['label'].apply(lambda row: 0 if row == 0 else 1)
        # 训练model1
        X1_train = df_loan.drop(columns=['risk', 'label'])
        y1_train = df_loan['risk']
        self.model1.fit(X1_train, y1_train)

        df_loan['label'] = y
        # 训练model2
        X2_train = df_loan[df_loan['risk'] == 1]
        y2_train = X2_train['label'] - 1
        X2_train = X2_train.drop(columns=['label','risk'])
        self.model2.fit(X2_train, y2_train)


    def predict(self, X):
        c1_input = X
        c1_output = self.model1.predict(c1_input.values)  # c1_output 表示数据是否有风险(0,1)

        # 按预测结果筛选出 classifier2 的 input
        X['pred'] = c1_output
        c2_input = X[X['pred'] == 1]
        X.drop(columns=['pred'], inplace=True)
        c2_input.drop(columns=['pred'], inplace=True)
        # classifier2 预测分类结果
        c2_output = self.model2.predict(c2_input.values)

        # classifier2 结果
        finalRes = pd.DataFrame()
        finalRes['result'] = c2_output + 1
        finalRes['index'] = c2_input.index
        # classifier1 结果
        tmp = pd.DataFrame()
        tmp['result'] = c1_output
        tmp['index'] = c1_input.index
        tmp = tmp[tmp['result'] == 0]
        # 合并结果
        finalRes = pd.concat([finalRes, tmp], ignore_index=True)
        finalRes = finalRes.set_index('index').loc[X.index].reset_index()

        return finalRes


