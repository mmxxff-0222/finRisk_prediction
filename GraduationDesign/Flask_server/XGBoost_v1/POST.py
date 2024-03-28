import requests
import json

features = {'loan_amnt': 12000.0,
 'term': 0.0,
 'int_rate': 12.12,
 'installment': 399.26,
 'grade': 4.0,
 'emp_length': 0.0,
 'home_ownership': 2.0,
 'annual_inc': 64000.0,
 'verification_status': 2.0,
 'purpose': 1.0,
 'addr_state': 25.0,
 'dti': 17.33,
 'delinq_2yrs': 0.0,
 'open_acc': 16.0,
 'pub_rec': 0.0,
 'revol_bal': 23825.0,
 'revol_util': 45.6,
 'total_acc': 31.0,
 'total_pymnt': 13971.58,
 'total_rec_late_fee': 0.0,
 'last_pymnt_amnt': 399.26,
 'application_type': 0.0,
 'acc_now_delinq': 0.0,
 'tot_coll_amt': 226.0,
 'tot_cur_bal': 143211.0}

data = {'features' : features}
# json_data = json.dumps(data)

# 构造 POST 请求的数据
data = {'features': features}
url = 'http://127.0.0.1:5000/predict'
response = requests.post(url, json=data)
# response = requests.get(url)
print(response.text)