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

