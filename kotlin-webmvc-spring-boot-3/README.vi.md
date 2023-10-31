# Kotlin Web MVC Spring Boot 3

Base project cho Web MVC service

## Yêu cầu

- OpenJDK 17

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
