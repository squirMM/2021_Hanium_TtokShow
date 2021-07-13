import pymysql as db

local = 'dbtest.cuslvraxrcdc.ap-northeast-2.rds.amazonaws.com'
con = db.connect(
    host=local,
    user='user',
    db='ttockshow',
    password='qwert123',
    charset='utf8'
)
cur = con.cursor()

query = "SELECT * FROM PRODUCT"
try:
    cur.execute(query)
except db.Error as e:
    print(e)
finally:
    cur.close()
    con.close()