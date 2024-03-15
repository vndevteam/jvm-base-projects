# FROM eclipse-temurin:17-jre-alpine AS builder
# Should move to use Ubuntu for production environment
# Refer: https://www.dotcms.com/blog/post/moving-to-ubuntu-for-our-docker-image
FROM eclipse-temurin:17.0.10_7-jre-alpine AS builder
ARG JAR_FILE=../build/libs/*.jar
WORKDIR /workspace/
COPY $JAR_FILE ./app.jar
RUN java -Djarmode=layertools -jar ./app.jar extract

FROM eclipse-temurin:17.0.10_7-jre-alpine
RUN addgroup webappgroup; adduser  --ingroup webappgroup --disabled-password webapp
USER webapp
WORKDIR /workspace/
COPY --from=builder /workspace/dependencies/ ./
COPY --from=builder /workspace/spring-boot-loader/ ./
COPY --from=builder /workspace/snapshot-dependencies/ ./
COPY --from=builder /workspace/application/ ./
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "org.springframework.boot.loader.JarLauncher"]

EXPOSE 8081
EXPOSE 5005
