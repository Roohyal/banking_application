# Banking Application

## Overview
This banking application allows users to perform essential banking operations, such as creating an account, signing in, checking balances, debiting and crediting an account, retrieving a user's name using their account number, and uploading a profile picture. The application uses MySQL as the database.

## Features
- **User Registration**: Create a new account.
- **User Authentication**: Secure login functionality.
- **Check Balance**: Retrieve the current balance of an account.
- **Credit Account**: Add funds to an account.
- **Debit Account**: Withdraw funds from an account.
- **Retrieve User Name**: Get the name of the user using their account number.
- **Upload Profile Picture**: Users can upload their profile pictures.

## Technologies Used
- **Backend**: Java (Spring Boot)
- **Database**: MySQL
- **Security**: Spring Security (JWT authentication)
- **File Storage**: Cloudinary (for profile pictures)

## Setup Instructions
### Prerequisites
Ensure you have the following installed on your system:
- Java 17+
- Maven
- MySQL
- Postman (for testing API requests, optional)

### Installation Steps
1. Clone the repository:
   ```sh
   git clone https://github.com/your-repo/banking-app.git
   cd banking-app
   ```
2. Configure the database in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/banking_db
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   ```
3. Build and run the application:
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```

## API Endpoints
### Authentication
- **Register User**: `POST /api/auth/register`
- **Login User**: `POST /api/auth/login`

### Account Operations
- **Check Balance**: `GET /api/account/balance/{accountNumber}`
- **Credit Account**: `POST /api/account/credit`
  ```json
  {
    "accountNumber": "1234567890",
    "amount": 5000
  }
  ```
- **Debit Account**: `POST /api/account/debit`
  ```json
  {
    "accountNumber": "1234567890",
    "amount": 2000
  }
  ```
- **Get User Name by Account Number**: `GET /api/account/user/{accountNumber}`
- **Upload Profile Picture**: `POST /api/account/upload-picture`
   - Uses `multipart/form-data`

## Database Schema
### User Table
| Column       | Type        | Description         |
|-------------|------------|---------------------|
| id          | UUID       | Primary key        |
| name        | VARCHAR(255) | User full name     |
| email       | VARCHAR(255) | Unique email      |
| password    | VARCHAR(255) | Encrypted password |
| accountNumber | VARCHAR(20) | Unique account number |
| balance     | DECIMAL(10,2) | Account balance   |
| profilePicture | VARCHAR(255) | Profile image URL |

## Security
- Uses JWT for authentication and authorization.
- Passwords are securely hashed.
- API endpoints are protected based on user roles.

## Future Improvements
- Implement transaction history.
- Add multi-factor authentication.
- Introduce notifications for account activities.

## License
This project is open-source and available under the MIT License.
