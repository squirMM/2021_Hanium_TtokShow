import pymysql as db

# CONNECT_DB
local = 'dbtest.cuslvraxrcdc.ap-northeast-2.rds.amazonaws.com'
con = db.connect(
    host=local,
    user='user',
    db='ttockshow',
    password='qwert123',
    charset='utf8'
)
cur = con.cursor()

# CREATE_PRO
cre_pro = """CREATE TABLE pro (
barcord_id INT,
name VARCHAR(255) NOT NULL,
image_link VARCHAR(255) DEFAULT 'None',
count INT DEFAULT 0,
PRIMARY KEY (barcord_id) )"""

# DELETE TABLE
del_pro = "DROP TABLE PRO"

def c_pro():
    cur.execute(cre_pro)

con.commit()
cur.close()
