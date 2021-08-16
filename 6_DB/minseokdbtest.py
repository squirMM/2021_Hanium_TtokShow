import pymysql

# DB 연걸
local = 'dbtest.cuslvraxrcdc.ap-northeast-2.rds.amazonaws.com'
db = pymysql.connect(
    host=local,
    user='user',
    db='ttockshow',
    password='qwert123',
    charset='utf8'
)
curs = db.cursor()

sql = """select * from review"""
curs.execute(sql)
select = list(curs.fetchall())
db.commit()

for i in range(len(select)):
    print(select[i])
