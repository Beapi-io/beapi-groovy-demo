![alt text](https://github.com/orubel/logos/blob/master/beapi_logo_large.png)
# spring-boot-starter-beapi Groovy Demo

**Springboot Version** - 2.6.2 (or greater)

**JVM** - 17 

**Configuration** -  Follow instructions in documentation for [installation/configuration](https://beapi-io.github.io/spring-boot-starter-beapi/index.html#section-2)


**Build**
```
gradle clean;gradle build
 ```
 ---
**Getting a Token** - 

This will get you your BEARER token to use in your calls/app:
```
curl -v -H "Content-Type: application/json" -X POST -d '{"username":"admin","password":"@6m!nP@s5"}' http://localhost:8080/authenticate
```

  
**Calling your API**

Then call your api normally:
```
curl -v -H "Content-Type: application/json" -H "Authorization: Bearer {your_token_here}" --request GET "http://localhost:8080/v{appVersion}/user/show/5"
```
NOTE:  appVersion can be found in your gradle.properties file as 'version'  

---
**NOTE**
Project is currently not properly flushing DB so chaining tests have been disabled until I add fix; you can test but you will have to hand remove values from DB.


