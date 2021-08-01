import time
import urllib.request
import urllib.parse
import math
from selenium import webdriver
import pandas as pd
import sys
# 크롬드라이버 연결

options = webdriver.ChromeOptions()
options.add_experimental_option("excludeSwitches", ["enable_logging"])
driver = webdriver.Chrome(options=options)
data_list = []


def crawl(cur,pro):
    # sql_find = "SELECT barcord_id, name from product where barcord_id=8805999203461"
    # cur.execute(sql_find)
    # pro = cur.fetchone()

    product = pro[1]
    plusUrl = urllib.parse.quote_plus(product)
    url = f'http://www.ssg.com/search.ssg?target=all&query={plusUrl}'
    driver.get(url)
    
    try:
        a = driver.find_element_by_css_selector('.tip_txt')
        print(a.text)
        driver.quit()
        sys.exit()
    except Exception:
        driver.find_element_by_css_selector('.thmb').click()
        time.sleep(1)

    review_total = driver.find_element_by_css_selector('.num').text
    print("리뷰 개수:", review_total)
    comma = ","
    # 페이지별 리뷰 개수
    review_per_page = 10
    if comma in review_total:
        review_total = review_total.replace(comma, "")
    print(review_total)
    total_page = int(review_total) / review_per_page
    total_page = math.ceil(total_page)
    print("리뷰 페이지 수:", total_page)

    # 상품명 확인
    product = driver.find_element_by_css_selector('.cdtl_info_tit').text
    print("상품명:", product)

    # 찾고자 하는 상품인지 확인
    # if product in pro[1] or pro[1] in product: return

    review_grade = driver.find_element_by_css_selector('.cdtl_grade_total').text
    print("평점:", review_grade)

    def get_page_data():
        numbers = driver.find_elements_by_css_selector('.number')  # 번호 수집
        users = driver.find_elements_by_css_selector('.user')  # 사용자명 수집
        ratings = driver.find_elements_by_css_selector('.sp_cdtl.cdtl_cmt_per')  # 평점 수집
        reviews = driver.find_elements_by_css_selector('.cdtl_cmt_tx.v2')  # 리뷰 수집

        # 사용자명수와 평점수가 같을 경우만 수집
        if len(reviews) == len(ratings):
            for i in range(len(ratings)):
                number = numbers[i + 1].text
                user = users[i + 1].text
                rating = ratings[i].text
                rating = rating.replace("구매 고객 평점 별 5개 중 ", "")
                rating = rating.replace("개", "")
                rating = int(rating)
                review = reviews[i].text
                review = review.replace("사진\n", "")
                num = (2 * i + 1) % 20
                date = driver.find_element_by_xpath(f'//*[@id="cdtl_cmt_tbody"]/tr[{num}]/td[5]/div')
                date = date.text.replace("-", "")
                data_tu = (pro[0], user, date, rating, review, "ssg")
                data_list.append(data_tu)
                print(data_tu)

    print("수집 시작")  # 첫 페이지 수집하고 시작

    get_page_data()  # 버튼을 눌러서 페이지를 이동해 가면서 계속 수집. # 예외처리를 해줘야 함. 하지 않으면 중지됨.
    print("1 page 수집 끝")
    driver.find_element_by_xpath('//*[@id="comment_navi_area"]/a[1]').click()
    for page in range(1, total_page):
        try:
            get_page_data()
            if page >= 1 and page < 11:
                print(str(page) + " page 수집 끝")
                button_index = page + 1  # 데이터 수집이 끝난 뒤 다음 페이지 버튼을 클릭
                driver.find_element_by_xpath(f'//*[@id="comment_navi_area"]/a[{button_index}]').click()
                time.sleep(1)
            else:
                print(str(page + 1) + " page 수집 끝")
                button_index = page % 10 + 3  # 데이터 수집이 끝난 뒤 다음 페이지 버튼을 클릭
                driver.find_element_by_xpath(f'//*[@id="comment_navi_area"]/a[{button_index}]').click()
                time.sleep(1)
        except Exception as e:
            print("수집 에러")
    print(str(page) + " page 수집 끝")
    print("수집 종료")

    # print(data_list)

    sql = "INSERT IGNORE INTO review (barcord_id,user_id,date, star_rank,contents,cite) VALUES (%s,%s,%s,%s,%s,%s)"
    cur.executemany(sql, data_list)


