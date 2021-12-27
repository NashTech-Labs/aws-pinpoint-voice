# AWS pinpoint Voice

Rest API for sending voice messages in scala using was pinpoint voice channel.

## Prerequisite
* AWS Credentials for amazon pinpoint.

## Start Services with SBT
- sbt clean compile
- Run HttpServer object under src/main/scala/org/knoldus

Note: Before running the server export AWS credentials.

## Test with SBT
- sbt test

## Running Api's
You can use postman for running the api's
- localhost:7000/
    * GET - api/send/<to-number> - Sending voice message