FROM openjdk:8-jre

WORKDIR /app

COPY target/word-count-producer-1.0-SNAPSHOT.jar /app/

CMD ["java", "-jar", "word-count-producer-1.0-SNAPSHOT.jar", "org.example.word_count_producer"]
