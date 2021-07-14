import con_db as conn

cur = conn.connect()

# CREATE_REVIEW
cre_reviewT = """CREATE TABLE review(

)"""

# DELETE_REVIEW
del_reviewT = "DROP TABLE review"

# CREATE_FUN
def cre_proT():
    cur.execute(cre_reviewT)
    conn.commit()


# DELETE_FUN
def del_proT():
    cur.execute(del_reviewT)
    conn.commit()