FROM openjdk:21
ADD  target/speedy-code-0.0.1-SNAPSHOT.jar speedy-code-0.0.1.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "speedy-code-0.0.1.jar" ]