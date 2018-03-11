

<img src="https://raw.githubusercontent.com/alirezaashrafi/webi/master/pics/webi.jpg" width="100%">

#### Do not forget the star:)⭐️


[![](https://jitpack.io/v/alirezaashrafi/webi.svg)](https://jitpack.io/#alirezaashrafi/webi)



<h4>Webi is a Fast and full of features HTTP library that makes easy networking and caching response for Android apps And comparable to other similar libraries</h4>

- Easy to use and set up
- Less coding
- Very fast and multi-threaded
- Includes quick and different ways to cache
- JsonArray , jsonObject and xml request
- Easily post values to the server


---
## [راهنما فارسی اسفاده از کتابخانه](https://github.com/alirezaashrafi/webi/blob/master/README.fa.md)

---
## How to download webi
#### Gradle
###### Add it in your root build.gradle at the end of repositories:
```java
    allprojects {
         repositories {
             ...
             maven { url 'https://jitpack.io' }
         }
    }
```
###### add this line to your module build.gradle dependecies block:
```java
    compile 'com.github.alirezaashrafi:webi:4.1.0'
```

### Maven
###### Add the JitPack repository to your build file
```xml
  <repositories>
    <repository>
        <id>jitpack.io</id>
      <url>https://jitpack.io</url>
    </repository>
  </repositories>
```

###### Add the dependency

```xml
  <dependency>
    <groupId>com.github.alirezaashrafi</groupId>
    <artifactId>webi</artifactId>
    <version>4.1.0</version>
  </dependency>
```
---
## How to use webi
```java
  Webi.with(context)
      .from("http://www.example.com")
      .onResponse(new OnResponse() {
          @Override
          public void Response(String response, String where) {
              //response is content of url
              //where is type of load response from e.g. online ram xml or sql
          }
      }).connect();
```
---
##### Retry on failed
```java
  .setRetryTimes(int times) //Default is one time

  .setOnRetry(new OnRetry() {
      @Override
      public void retry(int remainingRetrys) {
          //call back when retry
      }
  });
```

---
##### Post with webi
`.addPost(key,value,description);` note: description is meta data and these are not sent with your HTTP request
```java
  Webi.with(context)
      .from("http://www.example.com")
      .addPost("username","webi")
      .addPost("password","123456")
      .connect();
```
##### and receive this posts in php
```php
  <?php
      $username = $_POST['username'];  //value webi
      $password = $_POST['password'];  //value 123456
  ?>
```

##### Post your custom list
```java
  List<Posts>postsList = new ArrayList<>();
  Posts posts = new Posts();
  posts.setKey("...");
  posts.setValue("...");
  postsList.add(posts);
  ....

  Webi.with(context)
    .from("http://www.example.com")
    .addPostList(postsList)
    .connect();
```
---
##### Add GET method params

```java
  Webi.with(context)
      .from("http://www.example.com")
      .addGet("username","webi")
      .addGet("password","123456")
      .connect();

      //res http://www.example.com?username=webi&password=123456
```

###### and get that values in php
```php
  <?php
      $username = $_GET['username'];  //value webi
      $password = $_GET['password'];  //value 123456
  ?>
```
---
##### Add Headers

```java
  Webi.with(context)
      .from("http://www.example.com")
      .addHeader("Content-Type","application/json")
      .addHeader("Authorization","Bearer.....")
      .connect();
```
###### If you want to send bearer token in header use this method
```java
  .setBearerToken("e.g. GJKRY78579tFYIlkdhiipEE908y0FD80")
```
---

##### JsonArray , JsonObject and XML request with webi
```java
  Webi.with(this).from("http://www.example.com")
      .setOnJsonArrayReceive(new OnJsonArrayReceive() {
          @Override
          public void jsonArray(JSONArray jsonArray, String where) {
              //return json array if content is a jsonArray
              //note jsonArray First character is '[' and the last is ']'

          }
      })
      .setOnJsonObjectReceive(new OnJsonObjectReceive() {
          @Override
          public void jsonObject(JSONObject jsonObject, String where) {
              //return json object if content is a json object
              //note JsonObject First character is '{' and the last is '}'

          }
      })
      .setOnXmlRecevie(new OnXmlReceive() {
          @Override
          public void xml(Document xml, String where) {
              //return xml if content is xml

          }
      }).connect();
```
---
##### Caching in webi
###### There are three ways to cache
- ram cache - fastest but temporary
- xml cache - fast but cache only 50 response
- sql cache - permanent and Unlimited cache about 5,000 response but Slower than the rest

```java
  Webi.with(context)
      .from("http://www.example.com")
      .setSqlCache(true) //It is better to have only one active Caching method
      .setXmlCache(true)
      .setRamCache(true)
      .connect();
```
###### `.setSqlCache(true,key)`
 `.setXmlCache(true,key)`
###### Your custom key for storage If you do not set, an automatic hash (MD5) key will be generated
---
##### Work offline
When work offline is enable, content is only read from cache and cache content is not update
```java
  .setWordOffline(true)
```
---
##### Encrypt cache content
###### When encryptCache is enabled, the data is encrypt before insertion
```java
  .setEncryptCache(true)
```
---
##### Set custom connectTimeOut & readTimeOut
```java
  .setConnectTimeOut(10000)
  .setReadTimeOut(10000)
  //The default of both is 5000 ms
```
---

##### Get Response Time

```java
  Webi.with(context)
    .from("http://www.example.com")
    .getResponseTime(new GetResponseTime() {
        @Override
        public void time(long time) {
            //e.g. 430 ms
        }
    }).
```
---
##### call back when request failed
```java
  .setOnFailed(new OnFailed() {
      @Override
      public void failed(int code) {
          //code is http response code e.g. 404
      }
  }).
```
---
##### use proxy in Webi
```java
.setProxy(host,port).
   or
.setProxy(host,port,username,password).

```
---

### WebiConfig.init()
#### Use this class to change web defaults

```java
    WebiConfig.init()
        .setDefaultUrl("http://alirezaashrafi.ir")
        .setDefaultConnectTimeOut(15000)
        .setDefaultReadTimeOut(15000);

    Webi.with(this).onResponse(new OnResponse() {
        @Override
        public void Response(String res, String where) {

        }
    });

```
##### It will better performance if call and set first of all in application class
```java
  public class myApplication extends Application {
      @Override
      public void onCreate() {
          WebiConfig.init()
                  .setDefaultUrl("http://alirezaashrafi.ir")
                  .setDefaultConnectTimeOut(15000)
                  .setDefaultReadTimeOut(15000);


          super.onCreate();
      }
  }
```
##### manifest
```XML
<manifest>
    <application
        android:name=".myApplication"
        ...
        >

    </application>
</manifest>
```
##### some of WebiConfig class methods

<img src="https://raw.githubusercontent.com/alirezaashrafi/webi/master/pics/config.png" >

---
## Licence
Copyright 2017 Alireza Ashrafi

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


---
## Author
 - [Alireza Ashrafi](https://github.com/alirezaashrafi)
 - [web site : alirezaashrafi.ir](http://alirezaashrafi.ir)
 - [email : alirezaashrafi.pv@gmail.com](alirezaashrafi.pv@gmail.com)
 - Phone & Whatsapp +98 901 396 2648
 - [telegram : @dr_khaki](http://t.me/dr_khaki)

 ---
#### If you liked this library, do not forget to star and follow me ⭐️❤️️💙
#### [Eventually see my other libraries and projects](https://github.com/alirezaashrafi/)
