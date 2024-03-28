import json

# 打开 JSON 文件并加载数据
with open('report.json', 'r') as file:
    data: dict = json.load(file)

print(data.get('classifier1'))
print(data.get('classifier2'))
print(data.get('final'))