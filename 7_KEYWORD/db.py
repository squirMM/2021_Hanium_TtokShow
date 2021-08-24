import pymysql as db
import time

local = 'dbtest.cuslvraxrcdc.ap-northeast-2.rds.amazonaws.com'
con = db.connect(
    host=local,
    user='user',
    db='ttockshow',
    password='qwert123',
    charset='utf8'
)

cur=con.cursor()

sql_find = "SELECT contents from review"
cur.execute(sql_find)
result = cur.fetchall()
print(result)