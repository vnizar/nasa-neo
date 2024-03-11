# springboot-neo-app

REST API to get data from [NASA API](https://api.nasa.gov/) and process it based on requirements.

## Requirements

For building and running the application you need:

- [JDK 17](https://jdk.java.net/17/)
- [Redis](https://redis.io/docs/install/install-stack/)

## Running the application locally

Before running the application, make sure you run Redis server first, the default configuration for Redis are :
```
redis.host=localhost
redis.port=6379
```

Then you can start the application, if you use IDE, you can simply run the `AppApplication.java`.
Alternatively, you can run manually via CLI.

## Running via CLI
First, you need to build the JAR by using command 
```shell
mvn clean package
```
This will create JAR file inside folder `target`. Then, you can run the application using command 
```shell
java -jar <your-jar-file>.jar
```

## Endpoints
There are 2 simple endpoints for this application :
```
[GET] /v1/feed
```
Parameters :

| Parameter  | Type              | Required |
|------------|-------------------|----------|
| start_date | Date (dd-mm-yyyy) | yes      |
| end_date   | Date (dd-mm-yyyy) | yes      |
| size       | int               | no       |

Description :
Get the nearest asteroid to earth from NASA API based on dates provided and the data size.

```
[GET] /v1/lookup/{id}
```

Description :
Get the detail information of the asteroid.

## Architecture Overview
Here's the overview architecture.
[![Architecture](https://ibb.co/9HkMk7X)]