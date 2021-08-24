import pymysql.err
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

# sql_mining="""ALTER TABLE product ADD COLUMN mining VARCHAR(255) DEFAULT 'None'"""
# sql_ssg="""ALTER TABLE product ADD COLUMN ssg INT DEFAULT 0"""
# sql_lotte="""ALTER TABLE product ADD COLUMN lotte INT DEFAULT 0"""
#
# cur.execute(sql_mining)
# cur.execute(sql_ssg)
# cur.execute(sql_lotte)

# SQL="""select product.name ,review.barcord_id ,product.ssg , product.lotte,count(review.barcord_id) as 'count'
# from product inner join review
# on product.barcord_id = review.barcord_id
# #where  review.cite="ssg"
# group by review.barcord_id"""
# cur.execute()


sql_check=""""""

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
