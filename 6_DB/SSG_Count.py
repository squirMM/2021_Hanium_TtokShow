import time
import urllib.request
import urllib.parse
import math
from selenium import webdriver
# 크롬드라이버 연결

options = webdriver.ChromeOptions()
options.add_experimental_option("excludeSwitches", ["enable_logging"])
driver = webdriver.Chrome(options=options)
data_list = []


def crawl(pro,cur):

    product = pro[1]
    plusUrl = urllib.parse.quote_plus(product)
    url = f'http://www.ssg.com/search.ssg?target=all&query={plusUrl}'
    driver.get(url)
    
    a = driver.find_element_by_css_selector('.csrch_top.v2').text
    if "상품이 없습니다." in a:
        print("해당 상품 없음")
        return
        # driver.quit()
        # sys.exit()
    driver.find_element_by_css_selector('.thmb').click()
    time.sleep(1)

    # 상품명 확인 
    product = driver.find_element_by_css_selector('.cdtl_info_tit').text 
    print("상품명:",product)

    try: #리뷰 없을 때
        review_none = driver.find_element_by_css_selector('.cdtl_review_txt').text 
        print(review_none)
        return
        
    except Exception: #리뷰 있을 때
        # 최신순 클릭
        driver.find_element_by_css_selector('.cdtl_opt').click()
        driver.find_element_by_xpath('//*[@id="cmt_select_sort"]/div/div/ul/li[2]').click()
    
    
    try: #리뷰 게시가 안되어있을때
        table = driver.find_element_by_class_name('cdtl_cmt_tbl')
        nodata = table.find_element_by_tag_name('p').text
        print(nodata)
        return
    except Exception:
        review_total = driver.find_element_by_css_selector('.num').text 
        print("리뷰 개수:",review_total)
        review_grade = driver.find_element_by_css_selector('.cdtl_grade_total').text
        print("평점:", review_grade)

    sql = "INSERT IGNORE INTO product (ssg) VALUE (%s)"
    cur.executemany(sql, review_total)