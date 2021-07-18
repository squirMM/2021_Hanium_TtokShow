import con_db

cur = con_db.connect()
# CREATE_PRO
cre_productT = """CREATE TABLE product (
barcord_id INT NOT NULL,
name VARCHAR(255) NOT NULL,
image_link VARCHAR(255) DEFAULT 'None',
star_avg INT DEFAULT 0, 
count INT DEFAULT 0,
PRIMARY KEY (barcord_id),
UNIQUE KEY(NAME) 
)"""

# DELETE_TABLE
del_productT = "DROP TABLE product"


# CREATE_FUN
def cre_proT():
    cur.execute(cre_productT)
    con_db.commit()


# DELETE_FUN
def del_proT():
    cur.execute(del_productT)
    con_db.commit()