# kotlin-spring-mvc-template

Spring MVC template using Kotlin. 

Features 

- automatic documentation generation via Swagger
- automatic request validation via JSR-303 Bean Validation.
- 12 factor compliant configuration via dotenv
- automatic code formatting and linting via ktlint

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
No tests yet!!

```shell
./gradlew test
```

## Try It

Navigate to [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## License
Apache 2.0
