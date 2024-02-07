# API Testing Documentation

## Overview

This document explains how to set up and run API tests using a Python script that utilizes the `requests` library and the `unittest` framework.

## Setup

1. **Install Dependencies**: Ensure Python and `pip` are installed on your system, and then run `pip install requests` to install the required Python package.

2. **Configure API Base URL**: Edit `config.json` to set your API's base URL.

    ```json
    {
      "api_base_url": "http://yourapi.com/api"
    }
    ```

3. **Run database and backend**: Run database and set it up and run backend:

    ```bash 
    ./scripts/runDB.sh 
    ./scripts/setupDB.sh
    ./scripts/runBackend.sh ./backend
    ```


4. **Running Tests**: Execute the tests using the command line.

    ```bash
    python api_test.py
    ```

## Writing Tests

- Tests are defined in `api_test.py`.
- Create a method for each API endpoint you wish to test, using `def test_endpoint_name(self):` format.
- Use `requests` to make API calls and `self.assert*` methods for assertions.

## Example

An example test case is provided in `api_test.py` for demonstration purposes.
