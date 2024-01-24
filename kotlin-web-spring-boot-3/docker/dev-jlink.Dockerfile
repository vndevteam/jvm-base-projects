FROM eclipse-temurin:17.0.10_7-jdk-alpine AS builder
ARG JAR_FILE=../build/libs/*.jar
WORKDIR /workspace/
RUN $JAVA_HOME/bin/jlink --add-modules java.se,jdk.jdwp.agent --strip-debug --no-man-pages --no-header-files --compress=2 --output ./jre/

COPY $JAR_FILE ./app.jar
RUN java -Djarmode=layertools -jar ./app.jar extract

FROM ubuntu:jammy
RUN addgroup webappgroup; adduser  --ingroup webappgroup --disabled-password webapp
USER webapp
ENV JAVA_HOME=/opt/java/jre
ENV PATH "${JAVA_HOME}/bin:${PATH}"
COPY --from=builder /workspace/jre/ $JAVA_HOME

WORKDIR /workspace/
COPY --from=builder /workspace/dependencies/ ./
COPY --from=builder /workspace/spring-boot-loader/ ./
COPY --from=builder /workspace/snapshot-dependencies/ ./
COPY --from=builder /workspace/application/ ./
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "org.springframework.boot.loader.JarLauncher"]

EXPOSE 8081
EXPOSE 5005
