import pandas as pd 
from selenium import webdriver 
import time 
import math 
import urllib.request
import requests
from bs4 import BeautifulSoup
from selenium.webdriver.chrome.options import Options

options = webdriver.ChromeOptions()
options.add_experimental_option("excludeSwitches", ["enable_logging"])
driver = webdriver.Chrome(options=options)
data_list = []

product = "아이스 브레이커스 민트"
plusUrl = urllib.parse.quote_plus(product)
url = f'http://www.ssg.com/search.ssg?target=all&query={plusUrl}'
driver.get(url)

driver.find_element_by_css_selector('.thmb').click()
time.sleep(1)

driver.find_element_by_css_selector('.cdtl_opt').click()
driver.find_element_by_xpath('//*[@id="cmt_select_sort"]/div/div/ul/li[2]').click()