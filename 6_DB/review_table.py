# CREATE_FUN
def cre_reT(cur):
    sql1= """CREATE TABLE review(
    barcord_id VARCHAR(26) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    date DATE ,
    contents VARCHAR(2000),
    star_rank INT DEFAULT 0 ,
    cite VARCHAR(20),
    FOREIGN KEY(barcord_id) references product (barcord_id) ON DELETE CASCADE, 
    PRIMARY KEY(barcord_id , user_id , date)
    )"""
    cur.execute(sql1)

    #sql2 = "CREATE per_pro ON review (barcord_id)"
    #cur.execute(sql2)


# DELETE_FUN
def del_reT(cur):
    sql = "DROP TABLE review"
    cur.execute(sql)


# INDEX_REBUILD
def rebuild(cur):
    sql = "ALTER INDEX per_pro REBUILD"
    cur.execute(sql)