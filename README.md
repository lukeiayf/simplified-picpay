<h1 align="center">
  Simplified PicPay
</h1>

This is a simplified version of the PicPay application based on the challenge proposed [here](https://github.com/PicPay/picpay-desafio-backend?tab=readme-ov-file)
.In order to develop this application I got some ideas from these cool people: 
- [Giuliana Bezerra](https://youtu.be/YcuscoiIN14)
- [Rafael Kiss](https://www.youtube.com/watch?v=9PDI5Kq2tEM&t=2s)
- [Fernanda Kipper](https://www.youtube.com/@kipperdev)

## Technologies used

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring MVC](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
- [Spring Data JDBC](https://spring.io/projects/spring-data-jdbc)
- [Spring for Apache Kafka](https://spring.io/projects/spring-kafka)
- [Docker Compose](https://docs.docker.com/compose/)
- [H2](https://www.h2database.com/html/main.html)

## How to run

- Clone git repository:
```
git clone https://github.com/lukeiayf/simplified-picpay
```
- Run Kafka:
```
docker-compose up
```
- Run Spring Boot application on `http://localhost:8080`.

## API

You can use [HttpPie](https://httpie.io/) or Postman to mock the API calls

- http :8080/transaction value=100.0 payer=1 payee=200
```
HTTP/1.1 200
Connection: keep-alive
Content-Type: application/json
Date: Tue, 05 Mar 2024 19:07:52 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

{
    "createdAt": "2024-03-05T16:07:50.749774",
    "id": 20,
    "payee": 2,
    "payer": 1,
    "value": 100.0
}
```

- http :8080/transaction
```
HTTP/1.1 200
Connection: keep-alive
Content-Type: application/json
Date: Tue, 05 Mar 2024 19:08:13 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

[
    {
        "createdAt": "2024-03-05T16:07:50.749774",
        "id": 20,
        "payee": 2,
        "payer": 1,
        "value": 100.0
    }
]
```