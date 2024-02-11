from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait # available since 2.4.0
from selenium.webdriver.support import expected_conditions as EC # available since 2.26.0
import os 
import time

def get_driver():
    driver = webdriver.Chrome()
    driver.get("http://192.168.33.10:300/frontend/")
    time.sleep(2)
    try:
        logout(driver)
    except Exception as e:
        print(e)
    return driver

def logout(driver):
    logoutbtn = driver.find_element_by_link_text("Log out")
    time.sleep(2)
    logoutbtn.click()
    time.sleep(2)

def login(driver, email, password):
    loginbtn = driver.find_element_by_link_text("Login")
    time.sleep(1)
    loginbtn.click()
    time.sleep(2)
    inputs = driver.find_elements_by_class_name("form-control")
    inputs[0].send_keys(email)
    inputs[1].send_keys(password)
    driver.find_element_by_class_name("btn-primary").click()
    time.sleep(3)