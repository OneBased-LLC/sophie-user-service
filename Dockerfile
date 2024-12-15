FROM openjdk:17-jdk-slim
COPY target/spring-boot-github-action-demo.jar spring-boot-github-action-demo.jar
EXPOSE 80
ENTRYPOINT ["java","-jar","/spring-boot-github-action-demo.jar"]