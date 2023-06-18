# Sigorta Kampanya API

## Uygulama Tanımı
Sigorta Kampanya API kullanıcının yeni sigorta kampanyası eklemesini, 
var olan kampanyaları aktive ve deaktive etmesini, 
tüm kampanyaları listelemesi sağlayan bir backend uygulamasıdır.
Ayrıca kampanya kategorileri listeleme ve belirli kampanya üzerinde yapılan durum değişikliklerini listeleme yeteneklerine de sahiptir.

## Kullanılan Teknolojiler
Java 17, Maven, Spring Boot, Spring Data, Swagger, Junit, Mocikto, MockMvc,
H2, Lombok, Docker, Docker Compose, Micrometer, MapStruct, Logback

## Teknik Detaylar
* Verileri saklamak için H2 Embedded In Memory Database kullanılmıştır.
* DTO ve Entity dönüşümleri için MapStruct kullanıldı.
* 5 ms'den fazla süren işlemler interceptor ile araya girilerek micrometer ile loglanmaktadır.
* Jakarta Validation anotasyonları kullanılarak requestlerdeki doğrulamalar yapılmıştır.
* Hatalar ve istenmeyen durumlar GlobalExceptionHandler içinde handle edilmektedir.

### Swagger ve Postman Collection
* Swagger UI için http://localhost:8080/swagger-ui/index.html adresinden endpointlere erişim sağlanır. 
Swagger üzerinde örnek requestler ve açıklamaları mevcuttur.
* Postman collection'da farklı caseler requestler oluşturulmuştur. [Postman Collection](postman/ska.postman_collection.json)

### Unit Test ve Entegrasyon Testi
Unit testler için Junit5, Mockito ve WebMvcTest kullanılmıştır. 
Testler BDD yaklaşımıyla yazılmıştır. Unit testleri çalıştırmak için aşağıdaki komut çalıştırılır.
```shell
./mvnw test
```
Entegrasyon testleri için MockMvc kullanılmıştır. Entegrasyon testlerini koşmak için aşağıdaki komut çalıştırılır.
```shell
./mvnw verify -Pintegration-tests
```
Test ortamında interceptor disabled hale getirilmiştir.

### Docker
Uygulamayı dockerize etmek için aşağıdaki komut çalıştırılır. 
```shell
docker build -t ska:0.0.1 .
```
Oluşturulan docker image'ından container ayağa kaldırmak için aşağıdaki komut çalıştırılır.
```shell
docker-compose up -d
``` 

Docker containerı durdurulup kaldırmak istenirse bu komut çalıştırılır.
```shell
docker-compose down
```
