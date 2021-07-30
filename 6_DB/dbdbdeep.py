import pymysql.err

import product_table as pro
import review_table as rev
import pymysql as db
import TM_SSG_Crawl_DB_Save as ssg

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
# cur or con error

# pro.cre_proT(cur)
# insp.first_insert(cur)
#rev.cre_reT(cur)
ssg.crawl(cur)

val = ""

# try:
#     sql_p = """SELECT * FROM product where barcord_id = %s"""
#     # 바코드 인식 에러 처리 필요
#     val = "8801062475667"
#     cur.execute(sql_p, val)
# except Exception as e:
#     print(e)
#     # 에러 발생한 경우 완전 종료 필요
# else:
#     # 쿼리 결과 반환 ->
#     result = cur.fetchone()
#     print(result)
# try:
#     sql_r = """SELECT * FROM review where barcord_id =%s"""
#     cur.execute(sql_r, val)
# except Exception as e:
#     print(e)
# else:
#     # 쿼리 결과 반환 -> 리뷰 출력용
#     result = cur.fetchall()

# DB 연결 해제
con.commit()
cur.close()
