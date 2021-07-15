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


# DB_CONNECTOR
def connect():
    return cur


# DB_COMMIT
def commit():
    con.commit()
    cur.close()