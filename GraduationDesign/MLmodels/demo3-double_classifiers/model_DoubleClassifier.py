import pandas as pd

from sklearn.model_selection import train_test_split

from sklearn.metrics import classification_report

import xgboost as xgb
from sklearn.ensemble import GradientBoostingClassifier
from sklearn.neighbors import KNeighborsClassifier
from sklearn.svm import SVC
from sklearn.ensemble import RandomForestClassifier

GBDT = GradientBoostingClassifier()

'''
分类器及参数如下：
KNN ：k=30
RF : 默认参数
GBDT: 默认参数
XGboost : 默认  xgb.XGBClassifier()
SVC : 默认

'''
SVC()

classifier1Set = [xgb.XGBClassifier(), RandomForestClassifier(), GradientBoostingClassifier(), ]
classifier2Set = []


reports = {}

# 加载数据集
df_loan = pd.read_csv("/MLmodels/dataset/loan_new_3_1.csv")
df_loan.drop(columns=['loan_status'], inplace=True)

# 设置分类器1的target : 按label分类，无风险（0）和有风险（1、2、3）分成两类
df_loan['risk'] = df_loan['label'].apply(lambda row: 0 if row == 0 else 1)

# 划分 训练集 和 测试集
X_train, X_finalTest, y_train, y_finalTest = train_test_split(df_loan, df_loan[['risk']], test_size=0.2,
                                                              random_state=0)

''' 分类器1：划分"无风险"和"有风险" '''
# 对classifier1的训练集再次划分测试集，用于单独统计正确率
X1_train, X1_test, y1_train, y1_test = train_test_split(X_train.drop(columns=['label', 'risk']), y_train, test_size=0.2,
                                                        random_state=0)
classifier1 = KNeighborsClassifier(n_neighbors=30)
classifier1.fit(X1_train, y1_train)
Y1_pred = classifier1.predict(X1_test.values)
# 保存结果1
reports['classifier1'] = classification_report(y1_test, Y1_pred)

''' 分类器2：将"有风险"的进一步划分成"低"、"中"、"高" '''
# 筛选出classifier2的训练数据（risk=1）
X2 = X_train[X_train['risk'] == 1]
y2 = X2['label'] - 1  # {1,2,3} 不合法，转换为{0,1,2}
# 对classifier2的训练集再次划分测试集，用于单独统计正确率
X2_train, X2_test, y2_train, y2_test = train_test_split(X2.drop(columns=['label', 'risk']), y2, test_size=0.2,
                                                        random_state=0)
classifier2 = xgb.XGBClassifier()
classifier2.fit(X2_train, y2_train)
Y2_pred = classifier2.predict(X2_test.values)
# 保存结果2
reports['classifier2'] = classification_report(y2_test, Y2_pred)

''' 连接两个classifier: 用之前划分的测试集 X_finalTest 和 y_finalTest ,测试最终分类效果 '''
y_finalTest = X_finalTest['label']

c1_input = X_finalTest.drop(columns=['risk', 'label'])
c1_output = classifier1.predict(c1_input.values)  # c1_output 表示数据是否有风险(0,1)
# 按预测结果筛选出 classifier2 的 input
X_finalTest['pred'] = c1_output
c2_input = X_finalTest[X_finalTest['pred'] == 1]
c2_input.drop(columns=['label', 'risk', 'pred'], inplace=True)
# classifier2 预测分类结果
c2_output = classifier2.predict(c2_input.values)


# classifier2 结果
finalRes = pd.DataFrame()
finalRes['result'] = c2_output + 1
finalRes['index'] = c2_input.index
# classifier1 结果
tmp = pd.DataFrame()
tmp['res'] = c1_output
tmp['index'] = X_finalTest.index
tmp = tmp[tmp['res'] == 0]
tmp.rename(columns={'res': 'result'}, inplace=True)
# 合并结果
finalRes = pd.concat([finalRes, tmp], ignore_index=True)
finalRes.sort_values(by='index', inplace=True)
# 统计准确率
y_finalTest.sort_index(inplace=True)
reports['final'] = classification_report(y_finalTest, finalRes['result'])


print('\nclassifier1:\n', reports['classifier1'])
print('\nclassifier2:\n', reports['classifier2'])
print('\nfinal:\n', reports['final'])
