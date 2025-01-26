FROM openjdk:17
COPY ./out/productions/MyNotionsApplication/ /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "MyNotionsApplication"]