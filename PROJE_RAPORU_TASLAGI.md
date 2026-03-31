# Proje Yönetim Sistemi

## 1. Giriş

Günümüzde kurum içi iş süreçlerinin dijital ortama taşınması, hem operasyonel verimliliğin artırılması hem de verilerin daha düzenli yönetilmesi açısından önem taşımaktadır. Özellikle proje bazlı çalışan yapılarda, projelerin takibi, çalışan bilgilerinin yönetimi ve çalışanların projelere atanması gibi süreçlerin sistematik biçimde yürütülmesi gerekmektedir.

Bu çalışma kapsamında geliştirilen **Proje Yönetim Sistemi**, bir organizasyon içindeki projelerin, çalışanların ve çalışan-proje ilişkilerinin yönetilmesini amaçlayan basit bir backend uygulamasıdır. Uygulama Spring Boot tabanlı olarak geliştirilmiş ve REST mimarisi üzerinden veri erişimi sağlayacak şekilde yapılandırılmıştır.

## 2. Projenin Amacı

Bu projenin temel amacı, proje yönetimi süreçlerini dijital ortamda düzenli ve sürdürülebilir bir şekilde yönetebilecek bir backend altyapısı oluşturmaktır. Sistem sayesinde:

- projeler kayıt altına alınabilmekte,
- çalışan bilgileri yönetilebilmekte,
- çalışanlar projelere atanabilmekte,
- proje ve çalışan ilişkileri sorgulanabilmektedir.

Bu yapı, daha sonra geliştirilebilecek bir full-stack sistemin backend temelini oluşturmaktadır.

## 3. Problem Tanımı

Kurumsal yapılarda birden fazla projenin eş zamanlı yürütülmesi ve çalışanların farklı projelerde görev alabilmesi, yönetimsel karmaşıklığa neden olabilir. Geleneksel yöntemlerle, örneğin tablo veya manuel kayıt sistemleriyle yürütülen takip süreçleri:

- veri tekrarına,
- güncelleme hatalarına,
- atama çakışmalarına,
- bilgiye erişim zorluklarına

yol açabilmektedir.

Bu nedenle proje, çalışan ve atama bilgilerinin merkezi bir sistem üzerinden yönetilmesi ihtiyacı ortaya çıkmaktadır. Bu proje, söz konusu ihtiyaca cevap verebilecek temel bir backend çözümü sunmaktadır.

## 4. Kullanılan Teknolojiler ve Seçim Nedenleri

### Java 21

Uygulama geliştirme dili olarak Java 21 tercih edilmiştir. Java, güçlü ekosistemi, geniş topluluk desteği ve kurumsal uygulamalardaki yaygın kullanımı nedeniyle backend projeleri için uygun bir seçenektir.

### Spring Boot

Spring Boot, hızlı uygulama geliştirme imkanı sağlaması, yapılandırma süreçlerini sadeleştirmesi ve Spring ekosistemi ile güçlü entegrasyonu nedeniyle tercih edilmiştir.

### Spring Data JPA

Veritabanı işlemlerinin daha düzenli ve nesne yönelimli bir yaklaşımla yönetilmesi için Spring Data JPA kullanılmıştır. Böylece repository katmanında temel CRUD işlemleri daha az kod ile gerçekleştirilebilmiştir.

### H2 Database

Geliştirme ve test aşamasında hızlı kurulum ve kolay kullanım sunduğu için H2 veritabanı tercih edilmiştir. In-memory yapıda çalışması sayesinde ek veritabanı kurulumu gerektirmeden uygulamanın test edilmesi mümkün olmuştur.

### REST API

Sistem, dış istemcilerle standart veri alışverişi yapabilmek için REST API yaklaşımıyla geliştirilmiştir. Bu sayede ileride web veya mobil istemcilerle kolay entegrasyon sağlanabilir.

### Katmanlı Mimari

Kodun okunabilirliğini, sürdürülebilirliğini ve modülerliğini artırmak amacıyla katmanlı mimari tercih edilmiştir. Bu yapı sayesinde controller, service, repository ve entity katmanları birbirinden ayrıştırılmıştır.

## 5. Sistem Mimarisi

Sistem, klasik katmanlı mimari ile geliştirilmiştir. Temel katmanlar aşağıdaki gibidir:

### Controller Katmanı

İstemciden gelen HTTP isteklerini karşılayan katmandır. İlgili endpoint’ler bu katmanda tanımlanmıştır.

### Service Katmanı

İş kurallarının ve uygulama akışının yönetildiği katmandır. Verilerin doğrulanması, ilişkisel kontroller ve uygulama mantığı burada ele alınmaktadır.

### Repository Katmanı

Veritabanı ile iletişim kuran katmandır. Spring Data JPA aracılığıyla temel veri erişim işlemleri yürütülmektedir.

### Entity Katmanı

Veritabanı tablolarını temsil eden sınıfların bulunduğu katmandır. Project, Employee ve ProjectAssignment yapıları bu katmanda tanımlanmıştır.

### Exception Katmanı

Uygulama içinde anlamlı hata mesajları üretmek amacıyla özel exception sınıfları kullanılmıştır.

## 6. Veritabanı Tasarımı

Sistemde üç temel veri yapısı bulunmaktadır:

### Project

Project tablosu, projelere ait temel bilgileri tutmaktadır.

Alanlar:

- `id`
- `name`
- `description`
- `startDate`
- `endDate`
- `status`

Bu yapı sayesinde proje kayıtları oluşturulabilir, güncellenebilir, listelenebilir ve silinebilir.

### Employee

Employee tablosu, çalışanlara ait bilgileri saklamaktadır.

Alanlar:

- `id`
- `firstName`
- `lastName`
- `email`
- `department`

`email` alanı benzersiz olarak tanımlanmıştır. Böylece aynı e-posta adresiyle birden fazla çalışan kaydı oluşturulması engellenmiştir.

### ProjectAssignment

ProjectAssignment tablosu, çalışan ve proje arasındaki ilişkiyi yönetmek için oluşturulmuştur.

Alanlar:

- `id`
- `project`
- `employee`
- `assignedAt`

Bu yapı ile bir çalışanın hangi projeye ne zaman atandığı tutulabilmektedir.

### Neden Doğrudan ManyToMany Kullanılmadı?

Project ile Employee arasında doğrudan `ManyToMany` ilişkisi kurulmak yerine, `ProjectAssignment` adında ara bir entity kullanılmıştır. Bunun temel nedenleri şunlardır:

- ilişkiye ait ek bilgilerin tutulabilmesini sağlamak,
- atama zamanını kaydedebilmek,
- ileride ilişkiye yeni alanlar ekleyebilmek,
- veri modelini daha esnek ve genişletilebilir hale getirmek.

Bu yaklaşım, ilerleyen aşamalarda örneğin görev rolü, atama durumu veya çalışma süresi gibi alanların eklenmesini kolaylaştıracaktır.

## 7. Modüllerin Açıklaması

### Project Modülü

Bu modül proje verilerinin yönetilmesini sağlar. Projeler için CRUD işlemleri desteklenmektedir. Ayrıca proje durumu `NEW`, `IN_PROGRESS` ve `COMPLETED` olarak tanımlanmıştır.

### Employee Modülü

Bu modül çalışan kayıtlarının yönetilmesini sağlar. Çalışanlar sisteme eklenebilir, güncellenebilir, listelenebilir ve silinebilir.

### ProjectAssignment Modülü

Bu modül, çalışanların projelere atanmasını sağlar. Aynı çalışanın aynı projeye ikinci kez atanması veritabanı ve servis katmanı düzeyinde engellenmiştir. Ayrıca bir projeye ait çalışan atamaları ve bir çalışanın yer aldığı projeler sorgulanabilmektedir.

## 8. Geliştirilen API Yapısı

Sistemde REST tabanlı endpoint’ler geliştirilmiştir.

### Project Endpoint’leri

- `GET /api/projects`
- `GET /api/projects/{id}`
- `POST /api/projects`
- `PUT /api/projects/{id}`
- `DELETE /api/projects/{id}`

### Employee Endpoint’leri

- `GET /api/employees`
- `GET /api/employees/{id}`
- `POST /api/employees`
- `PUT /api/employees/{id}`
- `DELETE /api/employees/{id}`

### Assignment Endpoint’leri

- `POST /api/assignments`
- `GET /api/assignments/project/{projectId}`
- `GET /api/assignments/employee/{employeeId}`
- `DELETE /api/assignments/{assignmentId}`

Bu API yapısı ile sistemin temel işlevleri istemci uygulamalar tarafından kullanılabilecek hale getirilmiştir.

## 9. Karşılaşılan Zorluklar

Proje geliştirme sürecinde bazı teknik ve öğrenme odaklı zorluklarla karşılaşılmıştır.

### Veritabanı Kurulum Süreçleri

Harici veritabanı kurulumları ve bağlantı ayarları geliştirme sürecinde zaman kaybına neden olabileceği için, başlangıç aşamasında H2 veritabanı tercih edilmiştir. Bu sayede uygulamanın hızlı biçimde ayağa kaldırılması ve temel işlevlerin test edilmesi mümkün olmuştur.

### Katmanlı Mimariyi Kurma Süreci

Controller, service, repository ve entity katmanlarının doğru biçimde ayrıştırılması, özellikle ilk kurulum aşamasında dikkat gerektiren bir süreç olmuştur. Katmanlar arası sorumlulukların doğru dağıtılması, uygulamanın daha düzenli hale gelmesini sağlamıştır.

### İlişki Yönetimi

Project ile Employee arasındaki ilişki yapısının doğrudan ManyToMany yerine ara entity ile modellenmesi, veri yapısının daha doğru ve sürdürülebilir tasarlanmasını gerektirmiştir. Bu seçim kısa vadede daha fazla sınıf oluştursa da uzun vadede daha sağlıklı bir yapı sunmaktadır.

## 10. Elde Edilen Sonuçlar

Bu çalışma sonucunda aşağıdaki kazanımlar elde edilmiştir:

- Proje, çalışan ve atama kayıtlarını yöneten çalışabilir bir backend sistemi oluşturulmuştur.
- REST API üzerinden temel CRUD işlemleri başarıyla uygulanmıştır.
- Çalışanların projelere atanması ve bu atamaların listelenmesi mümkün hale getirilmiştir.
- H2 veritabanı ile hızlı test ve geliştirme ortamı sağlanmıştır.
- Katmanlı mimariye uygun, geliştirilebilir bir temel oluşturulmuştur.

Bu yapı, daha büyük ölçekli bir proje yönetim sistemine dönüşebilecek teknik bir başlangıç niteliğindedir.

## 11. Gelecekte Yapılabilecek Geliştirmeler

Proje ilerleyen aşamalarda aşağıdaki geliştirmelerle genişletilebilir:

- Spring Security + JWT entegrasyonu
- Rol bazlı yetkilendirme
- React frontend entegrasyonu
- DTO ve mapper yapısının eklenmesi
- Global exception handler yapısının kurulması
- H2 yerine PostgreSQL veya MySQL kullanımı
- Swagger / OpenAPI dokümantasyonu
- Gelişmiş filtreleme, arama ve sayfalama desteği
- Test kapsamının artırılması

## 12. Sonuç

Bu proje, proje yönetimi alanında kullanılabilecek temel bir backend sisteminin nasıl tasarlanıp geliştirilebileceğini göstermektedir. Geliştirilen yapı; proje, çalışan ve atama yönetimini merkezi bir sistem altında toplamaktadır.

Katmanlı mimari kullanılması, sistemin daha okunabilir, sürdürülebilir ve geliştirilebilir olmasını sağlamıştır. Ayrıca ilişkisel veri modelinin ara entity ile kurulması, sistemin gelecekte daha kapsamlı özelliklerle genişletilebilmesine uygun bir altyapı sunmaktadır.

Sonuç olarak bu çalışma, hem teknik öğrenme süreci hem de kurumsal uygulama geliştirme mantığı açısından faydalı ve uygulanabilir bir örnek oluşturmaktadır.
