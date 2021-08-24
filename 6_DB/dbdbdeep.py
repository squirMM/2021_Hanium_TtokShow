import pymysql.err
import pymysql as db
import SSG_Count as sc
import LotteOn_Count as lc

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

# sql_set="""update product set ssg =0 , lotte=0"""
# cur.execute(sql_set)

SQL="""select review.barcord_id, product.name ,count(review.barcord_id) as 'count'
from product inner join review
on product.barcord_id = review.barcord_id
group by review.barcord_id"""
cur.execute(SQL)
result=cur.fetchall()

cnt=0
while cnt<len(result):
    pro=result[cnt]
    sc.crawl(pro,cur)
    con.commit()
    lc.crawl(pro,cur)
    con.commit()
    cnt+=1
    sql="""select name , ssg, lotte from product where barcord_id=%s"""
    cur.execute(sql,pro[0])
    res=cur.fetchall()
    print(res)

# DB 연결 해제
con.commit()
cur.close()
