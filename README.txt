#####################
SKT-MICROSERVICE-TASK
#####################

Modules
-------
1. Management App:
    a. 2 JSP one to create the products and another one to list them.
    b. Controller to handle the requests.
    c. Service to send the message through a queue (using 4) to the microservice (3).
2. Common Library:
    a. Contains the POJO that will be used to serialize and deserialize between 1 and 3.
    b. Serializer and deserializer if required.
3. Microservice:
    a. Connects to the DB to save and get the data (stored procedure (SP) required).
    b. Consume the message from queue (using 2)
    c. Send the list of products that will be shown in the view list on 1 to a queue (using 2)
4. The queue or topic depending on if is using RabbitMQ or Kafka
5. The DB that will save the data
    a. The insert and select will be through a Stored Procedure

Requirements
------------
● SpringBoot 1.5.7.RELEASE (Skytouch actual implementation)
● JDK 1.8
● Maven 3
● RabbitMQ / Kafka / SQS
● Docker for all DB’s and Brokers (i.e. RabbitMQ, queues)
● JUnit
● AssertJ (Optional but recommended)

Expectations
------------
● Send and receive a POJO in JSON format.
● Listen to messages in RabbitMQ or Kafka using a non-default port.
● 80% test coverage is expected on unit tests
● Implement an error handling mechanism
● Consider that applications must support concurrency

Nice to have
------------
● Use a Spring Configuration server to externalize the application settings, like RabbitMQ and Kafka, to a local Git repository
● Use docker-compose to get RabbitMQ or Kafka and the DB running