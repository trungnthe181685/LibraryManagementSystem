Library Management System
A simple web-based application to manage library books, users, and rentals using Java Spring Boot and MySQL.

Getting Started
Follow the steps below to set up and run the project locally.

1. Configure Database Connection
Open the application.properties file and update your MySQL username and password:
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password

2. Create the Database
In your MySQL server, create a new database named:
CREATE DATABASE book_rental;

3. Run the Project
Run the file Library Management System-main to start the Spring Boot application.

4. Initialize Schema
Execute the Include Create Schema SQL file in your MySQL Workbench or preferred SQL tool to create necessary tables and initial data.

5. Restart the App
After schema creation, restart Library Management System-main to reload the database.

6. Access the Application
Open your browser and go to:
http://localhost:8080
You should now see the Library Management System web interface.

7. Login Information
- Once the app starts, you will be redirected to the Login Page.
- You can:
+ Login using Google
+ Create a new account
+ Or use the default admin account to access full features:
Email:    admin1@gmail.com
Password: 123

Thanks and enjoy exploring our project!
