# kotlin-spring-mvc-template

Spring 5 MVC template with Kotlin and OpenAPI 3.0. (Also Supports Swagger 2.0)

<p align="center">
<img src="https://raw.githubusercontent.com/cdimascio/kotlin-spring-mvc-template/master/assets/spring-mvc-openapi.png" width="600"/>
</p>

**Features**:

- Automatic request and response validation via atlassian OpenApi 3.0
- Automatic documentation generation via Swagger UI
- 12 factor compliant configuration via dotenv
- Automatic code formatting and linting via ktlint
- Fully operational sample REST API
- API integration tests using Unirest

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
