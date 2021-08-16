from openpyxl import load_workbook

# 기준파일 + 절대 경로 찾는 법을 생각해보자!
st_wb = load_workbook('C:/Users/전수민/Desktop/[대한상공회의소_바코드정보_가공식품] 샘플파일.xlsx', data_only=True)
st_ws = st_wb.active

val = []


# DB에 한번에 넣어줄수 있도록
def mapping_sql(barcord_id, name, img_link, type):
    sam = (barcord_id, name, img_link, type)
    val.append(sam)


# 처음 넣을 때 반복문 돌기
def first_insert(cur):
    for row in st_ws.rows:
        if row == 1: continue
        if row[0].value is not None:
            mapping_sql(row[0].value, row[3].value, row[11].value, row[15].value)
    sql = "INSERT IGNORE INTO product (barcord_id,name,image_link ,type) VALUES (%s,%s,%s,%s)"
    cur.executemany(sql, val)


# insert del
def del_insert(cur):
    sql = "DELETE FROM product"
    cur.execute(sql)
