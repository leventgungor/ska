# Sigorta Kampanya API

## Kullanılan Teknolojiler
* Java 17, Spring Boot 3.1, Spring Data, Maven 3, Swagger, H2 Embedded Database, Lombok, Docker, Docker Compose, Micrometer

## Uygulama Tanımı
Kullanıcının yeni sigorta kampanyası eklemesini, 
var olan kampanyaları aktive ve deaktive etmesini, 
tüm kampanyaları listelemesi yeteneklerini içerir.

## Teknik Detaylar
* 5 ms'den fazla süren işlemler interceptor ile araya girilerek micrometer ile loglanmaktadır.
* Jakarta Validation Annotation'ları kullanılarak requestlerdeki doğrulamalar yapılmıştır.
* Hatalar GlobalExceptionHandler içinde yönetilmektedir.
* Unit testler için junit ve mockito kullanılmıştır.
* projeyi dockerize etmek için `docker build -t ska:0.0.1 .` komutu çalıştırılır.
* daha sonra `docker-compose up -d` komutu çalıştırılarak docker containerı ayağa kaldırılır.
* Kapatılmak istenirse docker-compose down komutu çalıştırılır.

* Swagger UI için http://localhost:8080/swagger-ui/index.html adresinden endpointlere erişim sağlanabilir

git 