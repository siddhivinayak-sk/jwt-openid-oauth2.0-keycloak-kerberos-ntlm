FROM openjdk:8-jre-alpine
LABEL maintainer="Sandeep Kumar"
WORKDIR /
ARG VER=0.0.1
ENV VER ${VER}
ADD ./spring-boot-jwt/target/spring-boot-jwt-$VER.jar /opt/lib/spring-boot-jwt.jar
EXPOSE 80
ENTRYPOINT ["java", "-Xmx512m","-Xss16m","-jar", "-Dconsole.level=INFO", "/opt/lib/spring-boot-jwt.jar"]
