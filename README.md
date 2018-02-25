# Fleet of Vehicles.
Система Автопарк. В систему могут зайти Водители и Администраторы. В Автопарке есть автобусы, маршруты. Администратор может назначать на Маршруты свободные автобусы, в автобусы свободных Водителей, а также освобождать их, делая свободными. Водитель может увидеть свое место работы, а также он должен подтвердить свое новое Назначение.

# Setup Requirements:
- JDK 1.8 or higher
- Tomcat 8.5 or higher
- MySQL 5.7 or higher
- Apache Maven 3.0 or higher

# Installation:
- Download and unzip project.
- Start MySQL server and execute script "schemeDB.sql" from \src\main\resources to init database.
- cd to root project folder and execute command "mvn clean install".
- copy artifact "fleetofvehicles-1.0.war" from target folder to %TOMCAT%\webapps.
- Run Tomcat. When server starts, application will be available by URL: http://localhost:8080/fleetofvehicles-1.0
