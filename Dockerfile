FROM openjdk

WORKDIR /CollectorAnalyzerA

COPY . /CollectorAnalyzerA

RUN ./gradlew build

CMD ["java", "-jar", "./build/libs/CollectorA-0.0.1.jar"]
