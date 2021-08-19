import time
import urllib.request
import urllib.parse
import math
from selenium import webdriver
import pandas as pd
import sys

#크롬드라이버 연결
options = webdriver.ChromeOptions()
options.add_experimental_option("excludeSwitches", ["enable_logging"])
driver = webdriver.Chrome(options=options)
data_list = []

product = "CJ 더 건강한 후랑크"
plusUrl = urllib.parse.quote_plus(product)
url = f'http://www.ssg.com/search.ssg?target=all&query={plusUrl}'
driver.get(url)

a = driver.find_element_by_css_selector('.csrch_top.v2').text
if "상품이 없습니다." in a:
    print("해당 상품 없음")
    driver.quit()
    sys.exit()

driver.find_element_by_css_selector('.thmb').click()
time.sleep(1)

# 상품명 확인 
product = driver.find_element_by_css_selector('.cdtl_info_tit').text 
print("상품명:",product)
price = driver.find_element_by_css_selector('.ssg_price').text 
price = price.replace(",","")
print("가격:",price) 


def get_page_data(): 
    numbers = driver.find_elements_by_css_selector('.number') #번호 수집
    users = driver.find_elements_by_css_selector('.user') # 사용자명 수집 
    ratings = driver.find_elements_by_css_selector('.sp_cdtl.cdtl_cmt_per') # 평점 수집 
    reviews = driver.find_elements_by_css_selector('.cdtl_cmt_tx.v2') #리뷰 수집
    print(len(numbers), len(users), len(ratings), len(reviews))
    # 리뷰개수와 평점수가 같을 경우만 수집 
    if len(reviews) == 10:
        for i in range(len(reviews)):
            number = numbers[i+1].text
            user = users[i+1].text
            rating = ratings[i].text
            rating = rating.replace("구매 고객 평점 별 5개 중 ","")
            rating = rating.replace("개","")
            rating = int(rating)
            review = reviews[i].text
            review = review.replace("사진\n" , "")
            review = review.replace("비디오\n" , "")
            num = (2*i+1) % 20
            date = driver.find_element_by_xpath(f'//*[@id="cdtl_cmt_tbody"]/tr[{num}]/td[5]/div').text
            date = date.replace("-","")
            data = (int(number), user, int(rating), review, int(date))
            data_list.append(data)
            print(data)
try: # 리뷰 없을때
    nodata = driver.find_element_by_css_selector(".cdtl_tx_nodata")
    print(nodata.text)
    driver.quit()
    sys.exit()
except Exception: # 리뷰 있을때
    # 최신순 클릭
    driver.find_element_by_css_selector('.cdtl_opt').click()
    driver.find_element_by_xpath('//*[@id="cmt_select_sort"]/div/div/ul/li[2]').click()
    review_total = driver.find_element_by_css_selector('.num').text 
    review_grade = driver.find_element_by_css_selector('.cdtl_grade_total').text
    print("평점:", review_grade) 

print("리뷰 개수:",review_total)
#페이지별 리뷰 개수
review_per_page = 10 
review_total = review_total.replace(",","")
total_page = int(review_total) / review_per_page 
total_page = math.ceil(total_page) 
print("리뷰 페이지 수:", total_page)    

print("수집 시작") # 첫 페이지 수집하고 시작 
get_page_data()
for page in range(0, total_page): 
    try:
        if page == 0:
            print("1 page 수집 끝")
            driver.find_element_by_xpath('//*[@id="comment_navi_area"]/a[1]').click()
            time.sleep(1)
            get_page_data()
        elif page > 0 and page < 10:
            print(str(page+1) + " page 수집 끝") 
            button_index = page + 2  # 데이터 수집이 끝난 뒤 다음 페이지 버튼을 클릭 
            driver.find_element_by_xpath(f'//*[@id="comment_navi_area"]/a[{button_index}]').click() 
            time.sleep(1)
            get_page_data()
        else:
            print(str(page+1) + " page 수집 끝") 
            button_index = page % 10 + 3 # 데이터 수집이 끝난 뒤 다음 페이지 버튼을 클릭 
            driver.find_element_by_xpath(f'//*[@id="comment_navi_area"]/a[{button_index}]').click() 
            time.sleep(1)
            get_page_data() 
    except: 
        print("수집 에러") 
print("수집 종료") 

print(data_list)

df = pd.DataFrame(data_list) 

