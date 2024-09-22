# Phone Management API

A simple Spring-Boot service to store phone numbers.

---
Requirements:

- Java 11+
- The `PHONE_VALIDATION_SERVICE_API_KEY` environment variable set with your [AbstractAPI](https://www.abstractapi.com/)
  api key

How to build:

```shell
mvn clean install
```

How to run:

- Start the MySQL Docker container with `docker-compose up -d`
- run: `./run.sh` to start the _phone-management-service_ Spring App
- You can use the `./curls.sh` script to exercise the app. It contains cURL commands for the basic operations.

How to test:

- The `phone-management-service` module has unit tests with mocked dependencies.
    - Just run `mvn clean test` to execute the test suite.
- The `phone-number-validation-service` module has integration tests with
  the [AbstractAPI](https://www.abstractapi.com/) service.
    - To execute the tests just run: `mvn verify -P integration-tests`

Developed environment:

- System Version: macOS 14.5 (23F79)
- java 17.0.2 2022-01-18 LTS
- Docker version 24.0.6, build ed223bc