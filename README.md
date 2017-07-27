Порт 1234. http://localhost:1234/#/ 

##### Запуск
```aidl
mvn spring-boot:run
```


##### Развёртывание war

```aidl
mvn clean package
```
Эта команда создает развертываемый артефакт target/X-Users-0.0.1-SNAPSHOT.war.

Вы можете(не пробовал) загрузить Tomcat, Jetty или любой другой контейнер, который поддерживает сервлеты 3.0 версии. Распакуйте его и поместите WAR файл в правильную директорию. Затем запустите сервер.

Вы должны увидеть это:

![alt text](https://github.com/NikitaIT/ru.returnonintelligence.testtask/blob/master/pre.PNG)