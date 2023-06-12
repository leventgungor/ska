# Sigorta Kampanya API

## Kullanılan Teknolojiler
Java 17, Spring Boot 3.1, Spring Data, Maven 3, Swagger, 
H2 Embedded Database, Lombok, Docker, Docker Compose, Micrometer, MapStruct

## Uygulama Tanımı
Sigorta Kampanya API kullanıcının yeni sigorta kampanyası eklemesini, 
var olan kampanyaları aktive ve deaktive etmesini, 
tüm kampanyaları listelemesi sağlayan bir backend uygulamasıdır.
Ayrıca kampanya kategorileri listeleme ve belirli kampanya üzerinde yapılan durum değişikliklerini listeleme yetenekleri de mevcuttur.

## Teknik Detaylar
* Veritabanı olarak H2 Embedded In Memory Database kullanılmıştır.
* DTO ve Entity dönüşümleri için MapStruct kullanıldı.
* 5 ms'den fazla süren işlemler interceptor ile araya girilerek micrometer ile loglanmaktadır.
* Jakarta Validation Annotation'ları kullanılarak requestlerdeki doğrulamalar yapılmıştır.
* Hatalar ve istenmeyen durumlar GlobalExceptionHandler içinde handle edilmektedir.

### Swagger
Swagger UI için http://localhost:8080/swagger-ui/index.html adresinden endpointlere erişim sağlanır.
Swagger üzerinde örnek requestler ve açıklamaları mevcuttur.

### Test
Unit testler için Junit5, Mockito ve WebMvcTest kullanılmıştır. 
Testler BDD yaklaşımıyla yazılmıştır.
```shell
./mvnw test
```
Entegrasyon testleri için MockMVC kullanılmıştır.
```shell
./mvnw verify -Pintegration-tests
```

### Docker
Uygulamayı dockerize etmek için aşağıdaki komut çalıştırılır. 
```shell
docker build -t ska:0.0.1 .
```
Oluşturulan docker image'ından container ayağa kaldırmak için aşağıdaki komut çalıştırılır.
```shell
docker-compose up -d
``` 

Kapatılmak istenirse bu komut çalıştırılır.
```shell
docker-compose down
```