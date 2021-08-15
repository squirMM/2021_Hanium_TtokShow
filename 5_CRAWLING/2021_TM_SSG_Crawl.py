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

product = "아이스 브레이커스 민트"
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

# 상품명 확인 
product = driver.find_element_by_css_selector('.cdtl_info_tit').text 
print("상품명:",product) 

def get_page_data(): 
    numbers = driver.find_elements_by_css_selector('.number') #번호 수집
    users = driver.find_elements_by_css_selector('.user') # 사용자명 수집 
    ratings = driver.find_elements_by_css_selector('.sp_cdtl.cdtl_cmt_per') # 평점 수집 
    reviews = driver.find_elements_by_css_selector('.cdtl_cmt_tx.v2') #리뷰 수집
    
    # 리뷰개수와 평점수가 같을 경우만 수집 
    if len(reviews) == len(ratings):
        for i in range(len(ratings)):
            number = numbers[i+1].text
            user = users[i+1].text
            rating = ratings[i].text
            rating = rating.replace("구매 고객 평점 별 5개 중 ","")
            rating = rating.replace("개","")
            rating = int(rating)
            review = reviews[i].text
            review = review.replace("사진\n" , "")
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
for page in range(1, total_page): 
    try:
        get_page_data()
        if page == 1:
            print("1 page 수집 끝")
            driver.find_element_by_xpath('//*[@id="comment_navi_area"]/a[1]').click()
            time.sleep(1)
        elif page > 1 and page < 11:
            print(str(page) + " page 수집 끝") 
            button_index = page + 1  # 데이터 수집이 끝난 뒤 다음 페이지 버튼을 클릭 
            driver.find_element_by_xpath(f'//*[@id="comment_navi_area"]/a[{button_index}]').click() 
            time.sleep(1)
        else:
            print(str(page+1) + " page 수집 끝") 
            button_index = page % 10 + 3 # 데이터 수집이 끝난 뒤 다음 페이지 버튼을 클릭 
            driver.find_element_by_xpath(f'//*[@id="comment_navi_area"]/a[{button_index}]').click() 
            time.sleep(1) 
    except: 
        print("수집 에러") 
print("수집 종료") 

print(data_list)

df = pd.DataFrame(data_list) 

