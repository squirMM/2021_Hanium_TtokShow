import time

import TM_SSG_Crawl_DB_Save as ssg
import TM_LotteOn_Crawl_DB_Save as lotte
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

#sql_find = "SELECT barcord_id, name ,ssg , lotte from product "
sql_find = """select review.barcord_id ,product.name ,product.ssg , product.lotte ,count(review.barcord_id) as 'count'
from product inner join review
on product.barcord_id = review.barcord_id
group by review.barcord_id """
cur.execute(sql_find)
result = cur.fetchall()
print(result)
#cur.close()

CalAvg="""select round(avg(star_rank),2), barcord_id  from review
    where barcord_id =%s
    group by barcord_id"""
ApAvg="""UPDATE product SET star_avg=%s WHERE barcord_id =%s"""

def average(pro):
    cur.executemany(CalAvg, pro)
    result_avg = cur.fetchall()
    cur.executemany(ApAvg, result_avg)
    con.commit()

# 0번부터 시작
# 후랑크 8번 / 밀가루 16 ,17 (Error) /
#num=int(input("몇 번?"))
cnt =0
while cnt < len(result):
    pro = result[cnt]
    if pro[0] == "8801007377926": continue
    print(pro)
    ssg.crawl(pro, cur)
    con.commit()
    lotte.crawl(pro, cur)
    con.commit()
    average(pro)
    con.commit()
    cnt+=1

# AvgAll
# CalAvgAll="""select round(avg(star_rank),2), barcord_id  from review
#     group by barcord_id"""
# cur.executemany(CalAvg)
# result=cur.fetchall()
# print(result)
# ApAvgAll="""UPDATE product SET star_avg=%s WHERE barcord_id =%s"""
# cur.executemany(ApAvg,result)
# con.commit()

cur.close()