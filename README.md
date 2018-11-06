# kotlin-spring-mvc-template

Spring 5 MVC template with Kotlin and OpenAPI 3.0. (Also Supports Swagger 2.0)

Features automatic request/response validation and interactive API doc

<p align="left">
<img src="https://raw.githubusercontent.com/cdimascio/kotlin-spring-mvc-template/master/assets/spring-mvc-openapi.png" width="600"/>
</p>

**Features**:

- Automatic request and response validation via atlassian [OpenApi 3.0](https://swagger.io/docs/specification/about/)
- Automatic documentation generation via [Swagger UI](https://swagger.io/tools/swagger-ui/)
- 12 factor compliant configuration via [java-dotenv](https://github.com/cdimascio/java-dotenv)
- Automatic code formatting and linting via [ktlint](https://ktlint.github.io)
- Fully operational sample REST API
- API integration tests with [Unirest](http://unirest.io/java.html) REST client
- Simple HTTP errors via [japi-errors](https://github.com/cdimascio/japi-errors)
- Dockerfile

## Setup

- Clone this repo
- copy `.env.template` to `src/main/resources/.env`



## Build

```shell
./gradlew build
```

## Format/Lint 

```shell
./gradlew lintKotlin # see lint errors
./gradlew formatKotlin # attempt to automatically fix lint errors
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

# Run the dist
java -jar ./build/libs/example-service-1.0.0.jar  
```

## Try It

Navigate to [http://localhost:8080/api_explorer/index.html](http://localhost:8080/api_explorer/index.html)

## License
Apache 2.0
