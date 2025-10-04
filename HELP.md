# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.6/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.5.6/maven-plugin/build-image.html)
* [Spring for Apache Kafka](https://docs.spring.io/spring-boot/3.5.6/reference/messaging/kafka.html)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.




- start the kafka
```sh
kafka-server-start.sh /Users/danishmahajan/Desktop/projects/kafka_2.13-3.9.1/config/kraft/server.properties
```

- [download SpringBoot project](https://start.spring.io/)
- add packaging in pom.xml
```xml
<packaging>pom</packaging>
```

- create a module inside the project for producer
	- new module java
	- build system maven
	- Change the file name according to you
	- inside the module go in pom.xml file and add 
	```xml
<packaging>jar</packaging>
	```
	
	- inside the resource folder
		- create a application.properties file
		- create Kafka topic builder
		- down dependencies for https data
			- okhttp event source maven
			- jackson json maven
			- Jackson Databind
		- `kafka-console-consumer.sh --topic wikimedia_recentchange --from-beginning --bootstrap-server localhost:9092`
```properties
spring.kafka.producer.bootstrap-servers= localhost:9092  
spring.kafka.producer.key-serializer= org.apache.kafka.common.serialization.StringSerializer  
spring.kafka.producer.value-serializer= org.apache.kafka.common.serialization.StringSerializer
```


- consumer

```properties
spring.kafka.consumer.bootstrap-servers= localhost:9092  
spring.kafka.consumer.group-id= 1_learning_springboot_kafka_project  
spring.kafka.consumer.auto-offset-reset=earliest  
spring.kafka.consumer.key-deserializer= org.apache.kafka.common.serialization.StringDeserializer  
spring.kafka.consumer.value-deserializer= org.apache.kafka.common.serialization.StringDeserializer
```


