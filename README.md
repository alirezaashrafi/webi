<center><img src="https://github.com/alirezaashrafi/webi/blob/master/webi.jpg?raw=true" width="500px">

</center>

# <center>Webi Android Http Libray</center>
<br>

<h4>Webi is an HTTP library that makes networking for Android apps</h4>

- easier and, most importantly, faster
- There are three fast Caching method ram,xml,sql
- jsonArray and jsonObject request
- post to server fast and easier





## How to download webi
### Gradle
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
    compile 'com.github.alirezaashrafi:webi:1.3.7'
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
    <version>1.3.7</version>
  </dependency>
```

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
      }).connect();;
```

##### Post with webi
```java
  Webi.with(context)
      .from("http://www.example.com")
      .addPost("username","webi")
      .addPost("password","123456")
      .connect();;
```
###### and receive this posts in php
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
##### JsonArray and JsonObject request with webi
```java
  Webi.with(context)
    .from("http://www.example.com")
    .jsonArrayRequest(new OnJsonArrayReceive() {
        @Override
        public void jsonArray(JSONArray jsonArray, String where) {
            //return json array if content is a jsonArray
            //note jsonArray First character is '[' and the last is ']'
        }
    })
    .jsonObjectRequest(new OnJsonObjectReceive() {
        @Override
        public void jsonObject(JSONObject jsonObject, String where) {
            //return json object if content is a json object
            //note JsonObject First character is '{' and the last is '}'
        }
    }).connect();;
```
##### Caching in webi
###### There are three ways to cache
- ram cache - fastest but temporary
- xml cache - fast but cache only 50 response
- sql cache - permanent and Unlimited cache about 5,000 response but Slower than the rest

```java
  Webi.with(context)
      .from("http://www.example.com")
      .ramCache(true) //It is better to have only one active Caching method
      .xmlCache(true)
      .sqlCache(true)
      .connect();
```
##### Work offline
When work offline is enabled, content is only read from cache and cache content is not update
```java
  .wordOffline(true)
```
##### Encrypt cache content
###### When encryptCache is enabled, the data is encrypt before insertion
```java
  .encryptCache(true)
```

##### Set custom connectTimeOut & readTimeOut
```java
  .connectTimeOut(10000)
  .readTimeOut(10000)
  //The default of both is 5000 ms
```


##### Get Response Time

```java
    Webi.with(context)
      .from("http://www.example.com")
      .getResponseTime(new GetResponseTime() {
          @Override
          public void time(long time) {

          }
      }).
```






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

## Author
 - [Alireza Ashrafi](https://github.com/alirezaashrafi)
 - [web site : alirezaashrafi.ir](http://alirezaashrafi.ir)
 - [email : alirezaashrafi.email@gmail.com](alirezaashrafi.email@gmail.com)
 - phone +98 901 396 2648
 - [telegram : @dr_khaki](http://t.me/dr_khaki)
