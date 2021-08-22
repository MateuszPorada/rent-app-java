FROM openjdk
ADD target/RentAppJava-0.0.1-SNAPSHOT.jar .
EXPOSE 8000
CMD java -jar RentAppJava-0.0.1-SNAPSHOT.jar
