import unittest

from .utils import *

class DoctorTest(unittest.TestCase):

    def setUp(self):
        self.driver = get_driver()
        login(self.driver, "admin@example.com.com", "admin")


    def tearDown(self):
        self.driver.close()
        
    def test_basic(self):
        # pass test 
        self.assertTrue(True)