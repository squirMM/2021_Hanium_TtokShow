import TM_SSG_Crawl_DB_Save as ssg
import pymysql as db

local = 'dbtest.cuslvraxrcdc.ap-northeast-2.rds.amazonaws.com'
con = db.connect(
    host=local,
    user='user',
    db='ttockshow',
    password='qwert123',
    charset='utf8'
)

cur=con.cursor()

sql_find = "SELECT barcord_id, name from product "
cur.execute(sql_find)
result = cur.fetchall()
#cur.close()

cnt = 0
while cnt < len(result):
    pro = result[cnt]
    print(pro)
    ssg.crawl(pro,cur)
    con.commit()
    cnt += 1


cur.close()