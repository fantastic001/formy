import unittest
import requests
import json

class APITest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        # Load API base URL from the configuration file
        with open('config.json') as config_file:
            config = json.load(config_file)
        cls.base_url = config['api_base_url']

    def test_form_data_endpoint(self):
        # Example test for an endpoint GET '/forms/1/data'
        response = requests.get(f"{self.base_url}/forms/1/data")
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.json(), {"test": "test"})

# Add more test cases as needed

if __name__ == '__main__':
    unittest.main()
