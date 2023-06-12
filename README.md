# Sigorta Kampanya API

## Kullanılan Teknolojiler
* Java 17, Spring Boot 3.1, Spring Data, Maven 3, Swagger, H2 Embedded Database, Lombok, Docker, Docker Compose, Micrometer, MapStruct

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
* Unit testler için Junit5, Mockito ve MockMVC kullanılmıştır.
* Entegrasyon testleri için MockMVC kullanıldı.
* Uygulamayı dockerize etmek için `docker build -t ska:0.0.1 .` komutu çalıştırılır. 
Daha sonra `docker-compose up -d` komutu çalıştırılarak docker containerı ayağa kaldırılır. 
Kapatılmak istenirse docker-compose down komutu çalıştırılır.

* Swagger UI için http://localhost:8080/swagger-ui/index.html adresinden endpointlere erişim sağlanmaktadır.
