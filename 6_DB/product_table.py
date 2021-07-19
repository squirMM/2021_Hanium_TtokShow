# CREATE_FUN
def cre_proT(cur):
    sql = """CREATE TABLE product (
    barcord_id VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    image_link VARCHAR(255) DEFAULT 'None',
    star_avg FLOAT DEFAULT 0, 
    count INT DEFAULT 0,
    PRIMARY KEY (barcord_id)
    )"""
    cur.execute(sql)


# DELETE_FUN
def del_proT(cur):
    sql = "DROP TABLE product"
    cur.execute(sql)



