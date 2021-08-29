from konlpy.tag import Okt
import sys
from collections import Counter
from wordcloud import WordCloud
import pymysql as db
import matplotlib.pyplot as plt

local = 'dbtest.cuslvraxrcdc.ap-northeast-2.rds.amazonaws.com'
con = db.connect(
    host=local,
    user='user',
    db='ttockshow',
    password='qwert123',
    charset='utf8'
)

cur_tm=con.cursor()

def insert_db(link,barcode):
    #UPDATE 테이블명 SET 컬럼1 = 수정값1 [, 컬럼2 = 수정값2 ...] [WHERE 조건];
    sql_miningLink="""UPDATE product SET mining=%s WHERE barcord_id=%s"""
    link=link.replace("https://drive.google.com/file/d/","https://drive.google.com/uc?id=")
    link=link.replace("/view?usp=sharing","")
    cur_tm.execute(sql_miningLink,(link,barcode))

def fetchReview(codeNum):
    sel_rev = """select contents from review where barcord_id = %s"""
    cur_tm.execute(sel_rev, [codeNum])
    rev = list(cur_tm.fetchall())
    return rev

def get_noun(news):
    okt = Okt()
    noun = okt.nouns(news)
    print("1")
    for i,v in enumerate(noun):
        if len(v) < 10:
            noun.pop(i)

    print("2")
    count = Counter(noun)

    print("3")
    noun_list = count.most_common(100)

    return noun_list

def visualize(noun_list):
    maxLen = 100000
    strReview = [x[0] for x in noun_list]
    print("string done.")
    # print(strReview)
    if len(strReview) > maxLen:
        strReview = strReview[0:maxLen]
    print(strReview)
    wc = WordCloud(font_path='./훈정글북R.ttf', background_color="white",width=800,height=1200,max_words=200,max_font_size=400)
    wc.generate_from_frequencies(dict(noun_list))
    wc.to_file('main.png')

def excuteMining(maxLen,barcode):
    review_list = [x[0] for x in fetchReview(barcode)]
    print("fetch done.")
    # print(review_list)
    strReview = " ".join(review_list)
    print("string done.")
    # print(strReview)
    if len(strReview) > maxLen:
        strReview = strReview[0:maxLen]
    print(strReview)
    visualize(get_noun(strReview))
    # newVisualize(get_noun(strReview))
    print("visualization done. :)")

if __name__=="__main__":
    #sql="""select barcord_id from review group by barcord_id"""
    #cur_tm.execute(sql)
    #result=cur_tm.fetchall()
    #cnt=0
    #num=int(input("NUM : "))
    #cnt=num
    """while(cnt<len(result)):
        pro=result[cnt]
        print(pro[0])
        excuteMining(100000,pro[0])  # #리뷰내용최대길이(전체리뷰합친길이), 바코드번호
        cnt+=1
    con.commit()
    cur_tm.close()"""
    mainlist="""ESP-32 CAM 아두이노 보드를 영상 스트리밍 기능
      - 블루투스 시리얼 통신을 WiFi 통신 연결 지원
      - 똑쇼 앱을 스트리밍되는 영상에서 바코드 번호 추출
      - 바코드 번호를 상공회의소 DB에서 상품명을 찾아내고 상품 리뷰 크롤링 똑쇼 똑쇼 똑쇼 똑쇼 똑쇼 똑쇼 똑쇼 똑쇼 똑쇼 똑쇼 똑쇼 똑쇼 똑쇼 똑쇼 똑쇼 똑쇼 똑쇼 똑쇼 똑쇼 똑쇼 똑쇼 똑쇼 똑쇼 똑쇼 똑쇼 
      - 수집된 리뷰를 분석하고 DB에 저장
      - 똑쇼 앱에서 데이터마이닝을 도식화하여 출력 쇼핑을 할 때 모든 구매품목에 대한 정보를 정확히 알고 구매하지는 않는다. 가령 샴푸를 하나 구매한다고 해도 그저 상품에 쓰여진 일부의 정보나 판매원의 홍보 등의 한정된 정보로 판단하며, 가끔은 디자인이나 직감에 따라 구매했다가 후회를 하는 경우도 적지 않다. 쇼핑을 위해 준비된 스마트 안경이 다양한 상품들의 후기나 정보 등을 인터넷 상에서 찾아 보기 좋게 정리해준다면 더욱 알차고 합리적인 쇼핑을 할 수 있을 것이다. 본 프로젝트에서는 바코드가 존재하는 상품들을 안경의 카메라 모듈을 통해 인식하고 데이터를 분석하여 정리해주는 프로토타입과 그와 연동된 모바일 앱을 만들어 내는 것이 주요 목표이다 똑쇼 스마트안경을 이용한 똑쇼 똑똑한쇼핑 상품의 리뷰를 인터넷 똑쇼 스마트안경을 이용한 똑쇼 똑똑한쇼핑 상품의 리뷰를 인터넷에 직접 검색하지 않아도 손쉽게 이어폰으로 확인할 수 있어 더욱 편리하고 합리적인 쇼핑을 할 수 있게 된다"""
    visualize(get_noun(mainlist))