# Galah Cache Event Consumer

This service is responsible for consuming all UI events from Kafka cluster and update the same in Redis cache. This Service also offers to service end points to interact with cache data.

## Getting Started

This project is using spring-boot so no need to install local application server like tomcat to run this project.

```
1. Clone git repository https://github.com/siva583/galah-event-consumer-cache.git
2. Import project as maven project
3. Run project using "mvn spring-boot:run" command or run GalahEventConsumerApplication.java class as Java Application.
4. Great, now you are ready to play with service. Type http://localhost:8091/swagger-ui.htmlin browser and start playing with service. 
```

### Prerequisites

Service is using redis as cache and Kafka as messaging system between UI and redis. So both should be installed in local machine and use below ports.

##### Redis Instructions:
```
1. Download and install Redis.
2. Start redis server using "redis-server start" command
3. Validate the server using redis-cli(Optional)
```

##### Kafka Instructions:
```
1. Download and install Kafka.
2. Start zookeeper server using "kafka_2.11-1.0.0/bin/zookeeper-server-start.sh kafka_2.11-1.0.0/config/zookeeper.properties" command
3. Start kafka server using "kafka_2.11-1.0.0/bin/kafka-server-start.sh kafka_2.11-1.0.0/config/server.properties" command
4. Create needed topics using these commands
	kafka_2.11-1.0.0/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic USER_CREATE
	kafka_2.11-1.0.0/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic USER_UPDATE
	kafka_2.11-1.0.0/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic USER_DELETE
	kafka_2.11-1.0.0/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic USER_FOLLOW_REQUEST
	kafka_2.11-1.0.0/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic USER_FOLLOW_ACCEPT
	kafka_2.11-1.0.0/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic USER_UNFOLLOW
	kafka_2.11-1.0.0/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic POST_CREATE
	kafka_2.11-1.0.0/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic POST_UPDATE
	kafka_2.11-1.0.0/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic POST_DELETE
5. Create a consumer using below command
	kafka_2.11-1.0.0/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic USER_CREATE --from-beginning  --consumer-property group.id=fanout-consumer-group

```

Looks like a lot, no need to worry. There is one other way we can install this service and make it work locally with out kafka. Look at the swagger page(http://localhost:8091/swagger-ui.html) 

## Built With

* [Spring Boot](https://projects.spring.io/spring-boot/) - The framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Redis](https://redis.io/) - Used as cache
* [Kafka](https://kafka.apache.org/) - Kafka distributed streaming

## Swagger Page - End points available

#####follow-relation-cache-controller : Follow Relation Cache Controller 

```
Method:DELETE 
End point:/follower/{userId}
Use case: deleteFollower
```
```
Method: POST
End point: /follower/{userId} 
Use case: addFollower
```
```
Method: POST 
End point: /followerReq/{userId} 
Use case: addFollowerRequests
```
```
Method: GET 
End point: /followerReqs/{userId} 
Use case: getUserFollowerReq
```
```
Method: GET 
End point: /followers/{userId} 
Use case: getUserFollowers
```
```
Method: GET 
End point: /following/{userId} 
Use case: getUserFollowing
```
#####post-cache-controller : Post Cache Controller 
```
Method: GET 
End point: /post 
Use case: getAllPosts
```
```
Method: GET 
End point: /post/{postId} 
Use case: getPost
```
```
Method: POST
End point: /post/{postId} 
Use case: createPost
```
```
Method: PUT 
End point: /post/{postId} 
Use case: updatePost
```
```
Method:GET 
End point: /posts/{userId} 
Use case: getUserPosts
```
#####timeline-cache-controller : Timeline Cache Controller 
```
Method:GET 
End point: /feed/{userId} 
Use case: getUserFeed
Query Params: pageSize, pageNumber
```
#####user-cache-controller : User Cache Controller 
```
Method:GET 
End point: /user 
Use case: getAllUser
```
```
Method: DELETE 
End point: /user/{userId} 
Use case: deleteUser
```
```
Method:GET 
End point: /user/{userId} 
Use case: getUser
```
```
Method: POST 
End point: /user/{userId} 
Use case: createUser
```
```
Method: PUT 
End point: /user/{userId} 
Use case: updateUser
```

## Sample model JSON objects

##### User Json Object
```
{
  "createdAt": "2017-12-17T07:39:50.604Z",
  "email": "string",
  "firstName": "string",
  "lastName": "string",
  "middleName": "string",
  "updatedAt": "2017-12-17T07:39:50.604Z",
  "userId": "string",
  "userName": "string"
}
```
###### Post Json Object
```
{
  "content": "string",
  "createdAt": "2017-12-17T07:39:50.604Z",
  "postId": "string",
  "userId": "string"
}
```
###### Follow* Relation Json Object
```
{
  "date": "2017-12-17T07:39:50.603Z",
  "fId": "string",
  "userId": "string"
}
```


