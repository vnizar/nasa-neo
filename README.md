# springboot-neo-app

REST API to get data from [NASA API](https://api.nasa.gov/) and process it based on requirements.

## Requirements

For building and running the application you need:

- [JDK 17](https://jdk.java.net/17/)
- [Redis](https://redis.io/docs/install/install-stack/)
- [NASA API Key] (https://api.nasa.gov/)

## Running the application locally

Before running the application, make sure you have API Key from NASA API and run Redis server first, the default configuration for Redis are :
```
redis.host=localhost
redis.port=6379
```
You can change the API Key in :
```
apikey=<your-api-key>
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

## Configuration
There are some configurations that can be change, for Redis host and port, api key for NASA API, and TTL in seconds for cache.
```
apikey=<your-api-key>
cache.timeout=5
redis.host=localhost
redis.port=6379
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
The idea is to add cache in the service so it won't call NASA API since 3rd party API call is expensive. Here's the overview architecture.
![Architecture](https://i.ibb.co/Qv3534B/Screen-Shot-2024-03-12-at-01-15-28.png)