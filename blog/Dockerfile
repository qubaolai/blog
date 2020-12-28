FROM java:8

MAINTAINER 红杏

RUN mkdir -p /opt/project \
    && ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo 'Asia/Shanghai' >/etc/timezone

WORKDIR /opt/project

EXPOSE 8557

ADD ./target/blog-server-0.0.1-SNAPSHOT.jar.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

CMD ["--spring.profiles.active=dev"]
