FROM openjdk:8

COPY /target/demo-0.0.1-SNAPSHOT.war demo.war

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=production","/demo.war"]