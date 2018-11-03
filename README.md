# kotlin-spring-mvc-template

Spring 5 MVC template using Kotlin, OpenAPI 3.0.0

Features 

- automatic documentation generation via Swagger UI
- automatic request and response validation via atlassian OpenApi 3.0.0.
- 12 factor compliant configuration via dotenv
- automatic code formatting and linting via ktlint
- api integration tests using Unirest

## Setup

- Clone this repo
- copy `.env.template` to `src/main/resources/.env`



## Build

```shell
./gradlew build
```

## Format/Lint 

```shell
./gradlew formatKotlin
```

## Run
```shell
./gradlew bootRun
```

## Test

```shell
./gradlew test
```

## Package / Dist

```shell
./gradlew jar

# Run it
java -jar ./build/libs/example-service-1.0.0.jar  
```

## Try It

Navigate to [http://localhost:8080/api_explorer/index.html](http://localhost:8080/api_explorer/index.html)

## License
Apache 2.0
