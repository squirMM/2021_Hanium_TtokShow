import con_db as conn
import TM_SSG_Crawl_DB_Save as ssg

cur=conn.connect()

sql_find = "SELECT barcord_id, name from product "
cur.execute(sql_find)
result = cur.fetchall()

cur.close()

cnt = 0
while cnt < len(result):
    pro = result[cnt]
    print(pro)
    ssg.crawl(pro)
    cnt += 1