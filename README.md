# Project Management System Backend

## Proje Açıklaması

Bu proje, Spring Boot tabanlı bir Proje Yönetim Sistemi backend uygulamasıdır. Sistem; proje yönetimi, çalışan yönetimi ve çalışanların projelere atanması işlemlerini REST API üzerinden sunar.

Uygulama katmanlı mimari yaklaşımıyla yapılandırılmıştır ve geliştirme ortamında H2 in-memory veritabanı ile çalışır.

## Kullanılan Teknolojiler

- Java 21
- Spring Boot
- Spring Data JPA
- H2 Database
- Spring Validation
- Maven
- REST API

## Mimari Yapı

Proje klasik katmanlı mimari ile organize edilmiştir:

- `controller`: HTTP isteklerini karşılayan REST controller sınıfları
- `service`: İş kurallarını ve uygulama akışını yöneten servis katmanı
- `repository`: Spring Data JPA repository arayüzleri
- `entity`: Veritabanı tablolarını temsil eden JPA entity sınıfları
- `exception`: Uygulama içinde kullanılan özel exception sınıfları

## Modüller

### Project

Project modülü proje kayıtlarının yönetilmesini sağlar.

Alanlar:

- `id`
- `name`
- `description`
- `startDate`
- `endDate`
- `status`

### Employee

Employee modülü çalışan kayıtlarının yönetilmesini sağlar.

Alanlar:

- `id`
- `firstName`
- `lastName`
- `email`
- `department`

### ProjectAssignment

ProjectAssignment modülü, employee ve project arasındaki ilişkiyi ayrı bir entity üzerinden yönetir. Bu yapı doğrudan `@ManyToMany` yerine ilişki entity'si kullanılarak kurulmuştur.

Alanlar:

- `id`
- `project`
- `employee`
- `assignedAt`

## API Endpoint Özeti

### Project

- `GET /api/projects`
- `GET /api/projects/{id}`
- `POST /api/projects`
- `PUT /api/projects/{id}`
- `DELETE /api/projects/{id}`

### Employee

- `GET /api/employees`
- `GET /api/employees/{id}`
- `POST /api/employees`
- `PUT /api/employees/{id}`
- `DELETE /api/employees/{id}`

### Assignment

- `POST /api/assignments`
- `GET /api/assignments/project/{projectId}`
- `GET /api/assignments/employee/{employeeId}`
- `DELETE /api/assignments/{assignmentId}`

## Kurulum ve Çalıştırma

### Gereksinimler

- Java 21
- Maven Wrapper desteği

### Uygulamayı Başlatma

Windows ortamında proje dizininde aşağıdaki komut çalıştırılabilir:

```powershell
mvnw.cmd spring-boot:run
```

Uygulama varsayılan olarak `http://localhost:8080` adresinde çalışır.

## H2 Console Bilgileri

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: boş

## Örnek API Kullanımı

### 1. Project Oluşturma

`POST /api/projects`

```json
{
  "name": "Staj Yönetim Sistemi",
  "description": "Şirket içi proje takibi",
  "startDate": "2026-03-26",
  "endDate": "2026-06-26",
  "status": "NEW"
}
```

### 2. Employee Oluşturma

`POST /api/employees`

```json
{
  "firstName": "Zeynep",
  "lastName": "Dilay",
  "email": "zeynep@example.com",
  "department": "Yazılım"
}
```

### 3. Employee'yi Project'e Atama

`POST /api/assignments`

```json
{
  "projectId": 1,
  "employeeId": 1
}
```

### 4. Project Listesi

`GET /api/projects`

### 5. Employee Listesi

`GET /api/employees`

### 6. Project Assignment Listesi

`GET /api/assignments/project/1`

### 7. Employee Assignment Listesi

`GET /api/assignments/employee/1`

### 8. Project Güncelleme

`PUT /api/projects/1`

```json
{
  "name": "Staj Yönetim Sistemi v2",
  "description": "Güncellenmiş proje açıklaması",
  "startDate": "2026-03-26",
  "endDate": "2026-07-01",
  "status": "IN_PROGRESS"
}
```

### 9. Employee Güncelleme

`PUT /api/employees/1`

```json
{
  "firstName": "Zeynep",
  "lastName": "Dilay",
  "email": "zeynep.dilay@example.com",
  "department": "Proje Yönetimi"
}
```

### 10. Assignment Silme

`DELETE /api/assignments/1`

### 11. Employee Silme

`DELETE /api/employees/1`

### 12. Project Silme

`DELETE /api/projects/1`

## Gelecekte Eklenebilecek Geliştirmeler

- Global exception handling yapısının eklenmesi
- DTO ve response model ayrımının yapılması
- Gelişmiş validation kurallarının eklenmesi
- Filtreleme ve sayfalama desteği
- Kalıcı veritabanı entegrasyonu
- Authentication ve authorization katmanının eklenmesi
- Test kapsamının genişletilmesi
- Assignment yapısına rol, görev tipi veya atama durumu gibi ek alanların eklenmesi
