import pandas as pd 
from selenium import webdriver 
import time 
import math 
import urllib.request
import requests
from bs4 import BeautifulSoup
from selenium.webdriver.chrome.options import Options

# 초기 설정 
interval = 2 
chrome_driver='C:/Users/USER/Desktop/홍머/한이음/데이터/chromedriver'
options = Options()
data_list = []

options = webdriver.ChromeOptions()
options.add_argument('lang=ko_KR')
options.add_argument('headless')
options.add_argument('window-size=1920x1080')
options.add_argument(f'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36')
driver = webdriver.Chrome(chrome_driver)


plusUrl = urllib.parse.quote_plus(input('검색어를 입력하시오 : '))
url = f"https://www.coupang.com/np/search?component=&q={plusUrl}&channel=user"

driver.get(url)

driver.find_element_by_css_selector('.search-product').click()
time.sleep(interval) 

# 상품평 클릭 상품상세에서 상품평으로 이동함. 
driver.find_element_by_css_selector('.count').click() 
time.sleep(interval) 

# 총 리뷰수 확인 
review_total = driver.find_element_by_css_selector('.sdp-review__average__total-star__info-count').text 
review_total = review_total.replace(",", "") 
print(review_total) 

#페이지별 리뷰 개수 
review_per_page = 5 

total_page = int(review_total) / review_per_page 
# 리뷰가 9개 일 경우 페이지수는 9 / 5 = 1.8 -> 2페이지 올림해야 함
total_page = math.ceil(total_page) 
print(total_page) 

# 상품명 확인 
product = driver.find_element_by_css_selector('.prod-buy-header__title').text 
print(product) 

# 사용자명과 평점 수집 함수 정의 
def get_page_data(): 
    
    # 사용자명 수집 
    users = driver.find_elements_by_css_selector('.sdp-review__article__list__info__user__name.js_reviewUserProfileImage') 
    
    # 평점 수집 
    ratings = driver.find_elements_by_css_selector('.sdp-review__article__list__info__product-info__star-orange.js_reviewArticleRatingValue') 
    
    # 사용자명수와 평점수가 같을 경우만 수집 
    if len(users) == len(ratings): 
        for index in range(len(users)): 
            data = {} 
            data['username'] = users[index].text 
            data['rating'] = int(ratings[index].get_attribute('data-rating')) 
            print(data) 
            data_list.append(data) 

print("수집 시작") 

# 첫 페이지 수집하고 시작 
get_page_data() 

# 버튼을 눌러서 페이지를 이동해 가면서 계속 수집.
# 예외처리를 해줘야 함. 하지 않으면 중지됨. 
 
for page in range(1, total_page): 
    try: 
        print(str(page) + " page 수집 끝") 
        button_index = page % 10 + 2 
        
        # 데이터 수집이 끝난 뒤 다음 페이지 버튼을 클릭 
        driver.find_element_by_xpath('//*[@id="btfTab"]/ul[2]/li[2]/div/div[5]/section[4]/div[3]/button[' + str(button_index) +']').click() 
        time.sleep(interval) 
        
        #1 0page 수집이 끝나서 11로 넘어가기 위해서는 > 버튼을 눌러야 함. 
        if(page % 10 == 0): 
            driver.find_element_by_xpath('//*[@id="btfTab"]/ul[2]/li[2]/div/div[5]/section[4]/div[3]/button[12]').click()
            time.sleep(interval) 
        
        # 해당 페이지 데이터 수집 
        get_page_data() 
    except: 
        print("수집 에러") 

print(str(page) + " page 수집 끝") 
print("수집 종료") 
df = pd.DataFrame(data_list) 
print(df) 

# 엑셀로 저장 
df.to_excel("coupang-crawling-example.xlsx")

