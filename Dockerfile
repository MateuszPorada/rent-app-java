FROM openjdk
ADD target/RentAppJava-0.0.1-SNAPSHOT.jar .
CMD java -jar RentAppJava-0.0.1-SNAPSHOT.jar
