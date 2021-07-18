# CREATE_REVIEW
cre_reviewT = """CREATE TABLE review(
barcord_id INT NOT NULL,
user_id VARCHAR(255) NOT NULL,
date DATE NOT NULL,
contents VARCHAR(2000),
star_rank INT DEFAULT 0 ,
cite VARCHAR(20),
FOREIGN KEY(barcord_id) references product (barcord_id) ON DELETE CASCADE, 
PRIMARY KEY(barcord_id , user_id , date)
)"""

# DELETE_REVIEW
del_reviewT = "DROP TABLE review"


# CREATE_FUN
def cre_reT(cur):
    cur.execute(cre_reviewT)


# DELETE_FUN
def del_reT(cur):
    cur.execute(del_reviewT)