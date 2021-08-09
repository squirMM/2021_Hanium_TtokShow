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
print(result)
#cur.close()

# 0번부터 시작
# 후랑크 8번 / 밀가루 16 ,17 (Error) /
num=int(input("몇 번?"))
cnt = num
try:
    while cnt < len(result):
        pro = result[cnt]
        print(pro)
        try:
            ssg.crawl(pro,cur)
        except Exception as e:
            print(num+"번 상품에서 멈춤\n"+e)
        finally:
            con.commit()
        cnt += 1
except Exception as e:
    print("Loop Error"+e)
finally:
    cur.close()