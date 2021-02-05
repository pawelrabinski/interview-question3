Forum System API
==================

## Remarks
* Project is using Java and Maven. Please make sure you have them installed on youe machine.
* Build project using `mvn clean install`
* Run application using `mvn spring-boot:run`
* Application should be available at URL `http://localhost:5000/questions`
* Application is using Lombok. Please install Lombok plugin in your IDE.
* Application is running with in memory H2 database
* Javadoc is attached in directory `/javadoc`

# API Endpoints

Forum API has 4 endpoints:

### POST new question: `http://localhost:5000/questions`
Request body:
```json
{
  "author": "Daniel",
  "message": "Message text"
}
```
Response body:
```json
{
  "id": 1,
  "author": "Daniel",
  "message": "Message text",
  "replies": 0
}
```

### POST reply to specific question: `http://localhost:5000/questions/{questionId}/reply`
Request body:
```json
{
  "author": "Reply author",
  "message": "Message reply text"
}
```
Response body:
```json
{
  "questionId": 1,
  "id": 5,
  "author": "Reply author",
  "message": "Message reply text"
}
```

### GET a thread: `http://localhost:5000/questions/{questionId}`, 
Response body:
```json
{
  "id": 1,
  "author": "Daniel",
  "message": "Message text",
  "replies": [
    {
       "id": 5,
       "author": "Reply author",
       "message": "Message reply text"
    },
    ...
  ]
}
```

### GET a list of questions: `http://localhost:5000/questions`
Response body:
```json
[
    {
      "id": 1,
      "author": "Daniel",
      "message": "Message text",     
      "replies": 0
    },
    ...
]
```

