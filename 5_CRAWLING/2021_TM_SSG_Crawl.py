import time
import urllib.request
import urllib.parse
import math
from selenium import webdriver

#크롬드라이버 연결
options = webdriver.ChromeOptions()
options.add_experimental_option("excludeSwitches", ["enable_logging"])
driver = webdriver.Chrome(options=options)
data_list = []

product = input("검색어를 입력하시오 : ")
plusUrl = urllib.parse.quote_plus(product)
url = f'http://emart.ssg.com/search.ssg?target=all&query={plusUrl}&src_area=recom'
driver.get(url)

driver.find_element_by_css_selector('.thmb').click()
time.sleep(2)

review_total = driver.find_element_by_css_selector('.num').text 
review_total = review_total.replace(",","")
print("리뷰 개수:",review_total)

#페이지별 리뷰 개수
review_per_page = 10 
total_page = int(review_total) / review_per_page 
total_page = math.ceil(total_page) 
print("리뷰 페이지 수:", total_page) 

# 상품명 확인 
product = driver.find_element_by_css_selector('.cdtl_info_tit').text 
print("상품명:",product) 
review_grade = driver.find_element_by_css_selector('.cdtl_grade_total').text
print("평점:", review_grade)

users = driver.find_elements_by_css_selector('.user') # 사용자명 수집 
ratings = driver.find_elements_by_css_selector('.sp_cdtl.cdtl_cmt_per') # 평점 수집 
reviews = driver.find_elements_by_css_selector('.cdtl_cmt_tx.v2') #리뷰 수집
for i in range(len(users)):
    data = {}
    data['username'] = users[i].text
    data['rating'] = ratings[i].text
    data['rating'] = data['rating'].replace("구매 고객 평점 별 5개 중 ","")
    data['rating'] = data['rating'].replace("개","")
    data['review'] = reviews[i].text
    data['review'] = data['review'].replace("사진" , "")
    data['review'] = data['review'].replace("\\n" , "")
    print(data)

'''
def get_page_data(): 
    users = driver.find_elements_by_css_selector('.user.in') # 사용자명 수집 
    ratings = driver.find_elements_by_css_selector('.cdtl_txt') # 평점 수집 

    # 사용자명수와 평점수가 같을 경우만 수집 
    if len(users) == len(ratings):
        for index in range(len(users)): 
            data = {} 
            data['username'] = users[index].text 
            data['rating'] = ratings[index].get_attribute('style')
            print(data) 
            data_list.append(data) 
print("수집 시작") # 첫 페이지 수집하고 시작 

get_page_data() # 버튼을 눌러서 페이지를 이동해 가면서 계속 수집. # 예외처리를 해줘야 함. 하지 않으면 중지됨. 

for page in range(1, total_page): 
    try: 
        print(str(page) + " page 수집 끝") 
        button_index = page % 10 + 2 # 데이터 수집이 끝난 뒤 다음 페이지 버튼을 클릭 
        print("한태희 개새끼1")
        driver.find_element_by_xpath(f'//*[@id="comment_navi_area"]/a[{page}]').click() 
        print("한태희 개새끼2")
        time.sleep(2) #1 0page 수집이 끝나서 11로 넘어가기 위해서는 > 버튼을 눌러야 함. 
        if(page % 10 == 0): 
            driver.find_element_by_xpath('//*[@id="comment_navi_area"]/a[10]').click() 
            time.sleep(2) # 해당 페이지 데이터 수집
         
        get_page_data()
    except: 
        print("수집 에러") 
print(str(page) + " page 수집 끝") 
print("수집 종료") 

df = pd.DataFrame(data_list) 
print(df) # 엑셀로 저장 
df.to_excel("ssg-crawling-example.xlsx")
'''