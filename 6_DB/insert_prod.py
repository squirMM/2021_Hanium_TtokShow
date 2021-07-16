import con_db as conn
from openpyxl import load_workbook

# 기준파일 + 절대 경로 찾는 법을 생각해보자!
st_wb = load_workbook('C:/Users/전수민/Desktop/[대한상공회의소_바코드정보_가공식품] 샘플파일.xlsx', data_only=True)
st_ws = st_wb.active

# DB 커넥터
cur = conn.connect()


# DB에 넣어주기
def insert_db(barcord_id, name, img_link):
    sql = "INSERT INTO product (barcord_id,name,image_link) VALUES (%s,%s,%s)"
    val = (barcord_id, name, img_link)
    cur.execute(sql, val)


# 처음 넣을 때 반복문 돌기
def first_insert():
    for row in st_ws.rows:
        if row == 1: continue
        insert_db(row[0].value, row[3].value, row[11].value)
    conn.commit()
