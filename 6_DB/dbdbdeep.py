import product_table as pro
import insert_prod as insp
import review_table as rev
import pymysql as db

# DB 연걸
local = 'dbtest.cuslvraxrcdc.ap-northeast-2.rds.amazonaws.com'
con = db.connect(
    host=local,
    user='user',
    db='ttockshow',
    password='qwert123',
    charset='utf8'
)
cur = con.cursor()

#pro.cre_proT()
insp.first_insert(cur)


# DB 연결 해제
con.commit()
cur.close()