# Simple tasks manager

This is a _Spring Boot_ application.

> For the first approach, the data is kept in an H2 in-memory database, 
so all created tasks will be lost when the application is stopped.

To compile the project and execute all tests, just execute:
```bash
mvn clean install
```

To execute the app you can execute:
```bash
mvn spring-boot:run
```

Also, you have available two profiles:
- **local**: This profile enable CorsFilter, 
  useful to test the app in local
- **fakedata**: This profile creates 100 of random tasks, 
  with random data and random complete status.

To execute in local with some data loaded, execute:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local,fakedata
```

This app uses OpenAPI to expose the documented API.
It will be available under `/api/v1/swagger-ui.html`.
In local will be at http://localhost:8080/api/v1/swagger-ui.html endpoint