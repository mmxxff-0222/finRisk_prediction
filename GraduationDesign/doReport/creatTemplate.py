import pandas as pd
from pystache import Renderer
from docxtpl import DocxTemplate, RichText

# 选择需要的 特征列
columnList = ['loan_amnt', 'term', 'int_rate', 'installment', 'grade', 'emp_length',
       'home_ownership', 'annual_inc', 'verification_status',
       'purpose', 'addr_state', 'dti', 'delinq_2yrs', 'open_acc', 'pub_rec',
       'revol_bal', 'revol_util', 'total_acc', 'total_pymnt',
       'total_rec_late_fee', 'last_pymnt_amnt', 'application_type',
       'acc_now_delinq', 'tot_coll_amt', 'tot_cur_bal']
df_loan = pd.read_csv("/Users/mxfmeng/PycharmProjects/GraduationDesign/MLmodels/dataset/loan_old.csv")
df_loan.drop(columns = df_loan.columns.difference(columnList),inplace=True)
df_loan.dropna(inplace=True)

# 准备数据
selectData = df_loan.iloc[1234] # 随机选一条数据
reportData: dict = selectData.to_dict()
otherData = {
    'name' : 'Zhang San',
    'phone' : '13522380331',
    'risk_grade' : 'high'
}
reportData.update(otherData)


# 模版替换数据
tpl = DocxTemplate("模版.docx")
tpl.render(reportData)
tpl.save("风险报告.docx")




