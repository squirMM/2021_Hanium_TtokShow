import con_db as conn
import product_table as pro
import insert_prod as insp
import review_table as rev

pro.cre_proT()
insp.first_insert()
conn.commit()