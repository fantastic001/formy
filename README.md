# Formy

# How to run

## Linux

	cd scripts
	./runDB.sh 
	./setupDB.sh
	./runBackend.sh ../backend
	./runFrontend.sh ../frontend

API documentation is available at http://localhost:9000/swagger-ui.html

## Windwos


	cd scripts/windows
	./runDB.ps1

Login to your postgresql database (port 6666, localhost, user postgres and password is admin)
	create server with port 6666, localhost;
	create database "ISA"; 
	grant ALL on database "ISA" to "postgres";

In Powershell then type:

	./runBackend.ps1 -BackendPath ../../backend
	./runFrontend.ps1 -FrontendPath ../../frontend
./runBackendTest.ps1 -BackendPath ../../backend


# Running E2E tests 

go to e2e test environment

	cd e2e_tests
	virtualenv env 
	. env/bin/activate 
	pip install -r requirements.txt 

Run tests:

	python -m e2e



## Contributing to Formy

Thank you for considering contributing to Formy! This project is a Java Spring application on the backend and Vue.js on the frontend. The following guide will help you set up the project locally, contribute changes, and submit your contributions for review.

### Setting Up Your Development Environment

#### Clone the Repository

Start by cloning the Formy repository to your local machine:

```bash
git clone https://github.com/fantastic001/formy.git
cd formy
```

#### Database Setup

To set up and run the database, follow these steps:

```bash
./scripts/runDB.sh    # Start the database server
./scripts/setupDB.sh  # Set up the database schema and initial data
```

#### Backend Setup

The backend can be managed using the `runBackend.sh` script. Here are some common commands:

- **Run the backend**:
  ```bash
  ./scripts/runBackend.sh backend/
  ```

- **Run backend tests**:
  ```bash
  ./scripts/runBackend.sh backend/ test
  ```

- **Generate API documentation**:
  ```bash
  ./scripts/runBackend.sh backend/ doc
  ```

- **Check code style**:
  ```bash
  ./scripts/runBackend.sh backend/ checkstyle
  ```

#### Frontend Setup

To start the frontend, which includes an Nginx server serving both backend and frontend, execute:

```bash
./scripts/runFrontend.sh frontend/
```

### Contributing Code

1. **Create a Branch**: For any new feature or bug fix, create a branch off `main`:
   ```bash
   git checkout -b feature/<your-feature-name>
   # or
   git checkout -b bugfix/<your-bugfix-name>
   ```

2. **Implement Your Changes**: Make your code changes in accordance with the project's coding standards.

3. **Commit Your Changes**: Use clear and descriptive commit messages.
   ```bash
   git commit -am "Add a descriptive commit message"
   ```

4. **Push to GitHub**:
   ```bash
   git push origin <your-branch-name>
   ```

5. **Open a Pull Request (PR)**: Navigate to the [Formy repository on GitHub](https://github.com/fantastic001/formy) and open a PR against the `master` branch. Provide a comprehensive description of your changes.

### Code Review Process

Your PR will be reviewed by the project maintainers. Engage constructively with any feedback or requests for changes. This collaborative review process ensures high-quality contributions.

### Merging Your PR

Once your PR is approved and passes all checks, it will be merged into the `master` branch. Congratulations on contributing to Formy!

Should you have any questions or need further assistance, please don't hesitate to reach out to the project maintainers.

