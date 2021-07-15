from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.chrome.options import Options
import time

option = Options()
option.add_argument('headless')
option.add_argument('--window-size=1920,1080')  #캡쳐할 사이즈에 맞추기
browser = webdriver.Chrome(options=option)

path=r'\usr\local\bin'
browser.get('http://172.20.10.6:81/stream') #스트리밍 주소 입력
time.sleep(2)
screenshot=browser.save_screenshot('screenshot.png')
browser.quit
