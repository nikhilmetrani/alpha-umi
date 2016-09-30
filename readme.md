#α-UMi (Alpha UMi)

## Universal Software Marketplace

[![Build Status](https://travis-ci.com/nikhilmetrani/alpha-umi.svg?token=bQkogbxFfYCzp5uJhLW7&branch=master)](https://travis-ci.com/nikhilmetrani/alpha-umi)
[![codecov](https://codecov.io/gh/nikhilmetrani/alpha-umi/branch/master/graph/badge.svg?token=KQyi4NZj87)](https://codecov.io/gh/nikhilmetrani/alpha-umi)

##Environment setup

###Dependencies

1. MySQL Server and MySQL Workbench

###Developer 

####Developer dependencies
1. Maven or Java IDE with Maven Support

`NetBeans, Intellij Idea, Eclipse or any other desirable IDE`
2. NodeJS 5.xx or later

####Database Setup

`Linux / Mac OS: Run the script - ./scripts/mysql_setup_dev.sh`

`Windows: Coming soon...`

####Launching the project

`Launch the Java IDE and import the maven project - pom.xml`

`Import all dependencies`

`The server can be launched by using the spring-boot:run task of Maven plugin`

###Test 

####Test dependencies
1. Maven or Java IDE with Maven Support

`NetBeans, Intellij Idea, Eclipse or any other desirable IDE`
2. NodeJS 5.xx or later

3. POSTMAN

####Database Setup

`Linux / Mac OS: Run the script - ./scripts/mysql_setup_test.sh`

`Windows: Coming soon...`

####Launching the project

`Launch the Java IDE and import the maven project - pom.xml`

`Import all dependencies`

####Unit testing

`Run the Java tests from the Java IDE`

####API testing

`Launch POSTMAN once the server is running`

`From postman, request authorization token`

`Attach the token to the request header before making API request`

####UI testing

`This information will be available in the future`
