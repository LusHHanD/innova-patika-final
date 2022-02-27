FROM adoptopenjdk:11-jre-hotspot
EXPOSE 8080
ADD target/innova-patika-final-project.jar innova-patika-final-project.jar
ENTRYPOINT ["java", "-jar", "/innova-patika-final-project.jar"]


