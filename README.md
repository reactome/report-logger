[<img src=https://user-images.githubusercontent.com/6883670/31999264-976dfb86-b98a-11e7-9432-0316345a72ea.png height=75 />](https://reactome.org)

# Reactome Report Logger

## What is the Reactome Report Logger

This project is meant to centralise different types of events across different services that require periodic reports.

Detaching this feature from other projects allows easy maintenance and the possibility of creating enhanced centralised views for the gathered data.

## Configuration details

The mail.properties file contains 7 parameters in the mail.properties file, meant to be added in the maven profile.

* mail.host: the SMTP host
* mail.port: the SMTP port<br>
* mail.username: in case the SMTP server requires authentication, it must have the username
* mail.password= in case the SMTP server requires authentication, it must have the password
* mail.enable.auth: security protocol
* mail.report: the email address where the report is sent
* mail.report.hostname: the automatic report will ONLY be sent when the project is running in a specific server

Reactome has different servers for curation, development, release and production. This project only sends emails when running in the server which hostname is specified by 'mail.report.hostname'

## Create or Update database - DDLSchemaGenerator

### Pre-Steps

Create report database first and then run the schema generator

```console
mysql -u <user> -p[password] -e "CREATE DATABASE IF NOT EXISTS <dbName>";
```

### Running DDLSchemaGenerator

Options:

* CREATE: scans all the classes in the domain package and generate full DDL. Useful when creating a database from scratch. Output file: sql/ddl-report-create_UUID.sql. Ideally then content of this file should be copied and pasted to sql/ddl-report-create.sql.
Do not push *_UUID.sql files to GitHub

```java
java DDLSchemaGenerator CREATE
```

* UPDATE: scans all the classes looking for changes and generate DDL only for the new content: Output file: sql/ddl-report-update_UUID.sql. Ideally then content of this file should be copied and pasted to sql/ddl-report-update.sql.
Keep all the changes of a given task in ddl-report-update.sql and execute the script in other environments.

Important: DDLSchemaGenerator is not performing any changes in the database.

```java
java DDLSchemaGenerator UPDATE
```

### GitHub

Before pushing all the changes to github regarding DDL, please make sure ddl-report-create.sql can create a valid and correct database. All you need to do is ```java DDLSchemaGenerator CREATE``` and copy the content of the output file to ddl-report-create.sql.


### Troubleshooting

persistence.properties -> Hibernate is set to Validate the schema, it means the model can't be different from the database that the application is trying to connect. Tomcat won't startup.
