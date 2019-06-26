
# Spring Boot Hello Wolrd: _Inversion of Control_ dan _Dependency Ijection_

Tutorial ini akan mendemonstrasikan bagaimana menggunakan _Dependency Injection_ pada Spring Boot.  Pada akhir tutorial, berikut adalah struktur java project tersebut.
```
├── pom.xml
└── src
    └── main
        ├── java
        │   └── bippotraining
        │       ├── Application.java
        │       ├── Cat.java
        │       └── CatImpl.java
        └── resources
            └── application.properties

```


## 1. Konfigurasi file maven pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>bippo-training</groupId>
    <artifactId>boot-hello-world</artifactId>
    <version>1.0-SNAPSHOT</version>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.4.RELEASE</version>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

## 2. File application.properties
Buat file  `src/main/resourcess/application.properties` dengan isi sebagai berikut. File tersebut akan membuat Spring Boot yang kita jalankan tidak menggunakan web-application.

```properties
#src/main/resourcess/application.properties
spring.main.web-application-type=NONE
```

## 3. Interface `Cat` dan Class `CatImpl`

Class `CatImpl` mengimplementasikan method yang ada di Interface `Cat`.
```java
package bippotraining;

public interface Cat {
    String say();
}
```

Perhatikan pada Class `CatImpl`, kita tambahkan annotasi `@Component` yang nantinya akan kita jelaskan kegunaannya.
```java
package bippotraining;

import org.springframework.stereotype.Component;

@Component
public class CatImpl implements Cat {
  @Override
  public String say() {
        return "Cat say meow..";
  }
}
```

## 4. Aplikasi Utama

```java
package bippotraining;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

  @Autowired
  private Cat cat;

 public static void main(String...args){
        SpringApplication.run(Application.class, args);
 }

  @Override
  public void run(String... args) throws Exception {
        System.out.println("Cat say: " + cat.say());
  }
}
```

Perhatikan pada deklarasi private variable: `private Cat cat;`, kita tidak melihat variable ini di-instantiasi melalui statement: `cat = new CatImpl()`, hal ini karena instantiasi variable ini dilakukan oleh  Spring Framework, inilah yang disebut dengan _**Dependency Injection**_.

Berikut adalah penjelasan sederhana bagaimana Spring Framework bekerja:
Aplikasi utama (Class `Application`) menjalankan Framework Spring dangan kode baris berikut:
```java
 public static void main(String...args){
        SpringApplication.run(Application.class, args);
 }
```
Spring Framework membaca anotasi `@SpringBootApplication` pada kelas `Application`, kemudian mencari kelas-kelas dengan anotasi `@Component`. Spring framework akan secara membuat instance dari kelas-kelas dengan anotasi tersebut. Spring kemudian akan mencari deklarasi private variable dari Class dengan anotasi `@Autowired`, pada Class `Application` di atas, terdapat deklarasi variable `private Cat cat;`, Spring akan memeriksa apakah ada instance yang sudah pernah dibuat yang mengimplementasikan interface `Cat`, jika ada, maka private variable tersebut akan di-set ke instance yang sudah pernah dibuat.

Pada kelas utama kita, Spring Framework yang akan menjalankan method `run`.

## 5. Inversion of Control
Pada contoh di atas, konsep _**Inversion of Control**_ dapat dijelaskan sebagai berikut:
> _Inversion of Control_ (IoC) adalah membalikkan kendali dari sebagian program ke library atau framework. Jika semula program kita yang memanggil library, maka dengan IoC, library yang akan memanggil program kita.

Pada aplikasi di atas IoC dapat kita lihat bagaiman Spring Framework memanggil method `run()` pada Class `Application`.


## 6. Dependency Injection
Sedangkan _**Dependency Injection**_ (DI) adalah:
> Bentuk khusus dari IoC, di mana yang dibalikkan adalah proses object creation.

Jika dilihat pada contoh di atas, jika tanpa DI, maka kita perlu membuat instance dari Cat dengan statement:
```java
	Cat = new CatImpl();
```
Namun, dengan DI, kita cukup menambahkan anotasi `@Autowired` saja.