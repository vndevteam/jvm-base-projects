# Kotlin Web Spring Boot 3

Base project cho API Web service

## Yêu cầu

- OpenJDK 17

## Môi trường phát triển

### Docker

#### own

```bash
cd kotlin-web-spring-boot-3/
./docker/docker-build.sh dev ../build/libs/kotlin-web-spring-boot-3-0.0.1-SNAPSHOT.jar
# or
# ./docker/docker-build.sh dev-debug ../build/libs/kotlin-web-spring-boot-3-0.0.1-SNAPSHOT.jar
# or
# ./docker/docker-build.sh dev-jlink ../build/libs/kotlin-web-spring-boot-3-0.0.1-SNAPSHOT.jar
```

#### jib
```bash
./gradlew jibDockerBuild --image=vndevteam/kotlin-web-spring-boot-3:jib-v1
```

#### buildpacks
```bash
./gradlew bootBuildImage --imageName=vndevteam/kotlin-web-spring-boot-3:buildpacks-v1
```

## Tổng quan về cấu trúc dự án

## Một số cấu hình

- MessageSource:
  - `com.vndevteam.kotlinwebspringboot3.infrastructure.config.MessageConfig`
  - Tham khảo thêm: [Custom Validation MessageSource in Spring Boot](https://www.baeldung.com/spring-custom-validation-message-source)

## Kiến trúc
Domain Driven Design:
- Application layer:
  - User interfaces
  - RESTful controllers
  - JSON serialization libraries
- Domain layer (Phần cốt lõi của ứng dụng):
  - Implement business logic
  - Interface để giao tiếp với các phần khác bên ngoài
- Infrastructure:
  - Cấu hình Spring
  - Cấu hình Database
  - Implement các interface được định nghĩa ở Domain layer

## Tham khảo

- https://www.baeldung.com/hexagonal-architecture-ddd-spring
- https://github.com/gothinkster/spring-boot-realworld-example-app
