
<div  dir="rtl">
<p><img src="https://raw.githubusercontent.com/alirezaashrafi/webi/master/pics/webi.jpg" width="100%"></p>

<p><a href="https://jitpack.io/#alirezaashrafi/webi"><img src="https://jitpack.io/v/alirezaashrafi/webi.svg" alt="" /></a></p>

<h4>webi کتابخانه سریع و پر از ویژگی ها برای اتصال به سرور است و از ویژگی های آن میتوان به راحتی و کش کردن پاسخ ها برای نرم افزار های اندرویدی اشاره کرد که میتوان گفت قابل مقایسه و حتی بهتر از کتابخانه های مشابه است</h4>


<ul>
<li>راحتی در استفاده</li>

<li>برنامه نویسی کمتر</li>

<li>چند نخی و پر سرعت</li>

<li>دربرداشتن روش های مختلف برای کش</li>

<li>قابلیت دریافت jsonArray,jsonObject,xml</li>

<li>راحتی در ارسال و پست مقادیر به سرور</li>
</ul>
<hr />

<h2 id=""><a href="https://github.com/alirezaashrafi/webi/blob/master/README.fa.md">english readme </a></h2>

<hr />

<h2 dir"rtl">چگونه webi را دانلود کنیم</h2>

<h4  dir="rtl" id="gradle">Gradle</h4>

<h6 dir="rtl" id="buildgradle">این کد را به build.gradle پروژه خود اضافه کنید</h6>
</div>

```java
    allprojects {
         repositories {
             ...
             maven { url 'https://jitpack.io' }
         }
    }
```

<h6 dir="rtl" id="">این کد را به build.gradle ماژول خود اضافه کنید</h6>

```java
    compile 'com.github.alirezaashrafi:webi:4.0.0'
```

<h3  dir="rtl" id="maven">Maven</h3>

<h6 dir="rtl" id="">این کد را که مربوط به مخزن JitPack است را به فایل build خود اضافه کنید</h6>

```xml
  <repositories>
    <repository>
        <id>jitpack.io</id>
      <url>https://jitpack.io</url>
    </repository>
  </repositories>
```

<h6  dir="rtl" id="">این کد را به dependency اضافه کنید</h6>

```xml
  <dependency>
    <groupId>com.github.alirezaashrafi</groupId>
    <artifactId>webi</artifactId>
    <version>4.0.0</version>
  </dependency>
```

<hr />

<h2 dir="rtl" id="">چگونه از webi استفاده کنید</h2>

```java
  Webi.with(context)
      .from("http://www.example.com")
      .onResponse(new OnResponse() {
          @Override
          public void Response(String response, String where) {
              //response متن خوانده شده از لینک مورد نظر است
              //where محل دریافت این پاسخ را نمایش نشان میدهد که ایا از سرور دریافت شده یا از حافظه کش
          }
      }).connect();
```


<hr />

<h5 dir="rtl" id="">تلاش مجدد </h5>

```java
  .setRetryTimes(int times) //پیش فرض یک دفعه است

  .setOnRetry(new OnRetry() {
      @Override
      public void retry(int remainingRetrys) {
          //زمانی که تلاش مجدد اجرا شود این تابع صدا زده میشود
      }
  });
```

<hr />

<h5  dir="rtl" id="">ارسال و پست داده ها توسط webi</h5>

<p  dir="rtl"><code  dir="ltr">addPost(key,value,description)</code> نکته: description اطلاعات اضافی برای برنامه نویس است و به سرور ارسال نمیشود</p>


```java
  Webi.with(context)
      .from("http://www.example.com")
      .addPost("username","webi")
      .addPost("password","123456")
      .connect();
```

<h5  dir="rtl" id="andreceivethispostsinphp">و دریافت مقادیر در php</h5>

```php
  <?php
      $username = $_POST['username'];  //value webi
      $password = $_POST['password'];  //value 123456
  ?>
```

<h5  dir="rtl" id="postyourcustomlist">لیستی از پست های خود بسازید </h5>

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
<hr />

<h5  dir="rtl" id="addgetmethodparams">اضافه کردن پارامتر های ارسالی GET</h5>

```java
  Webi.with(context)
      .from("http://www.example.com")
      .addGet("username","webi")
      .addGet("password","123456")
      .connect();

      //res http://www.example.com?username=webi&password=123456
```

<h6 dir="rtl" id="andgetthatvaluesinphp">و دریافت مقادیر در PHP</h6>

```php
  <?php
      $username = $_GET['username'];  //value webi
      $password = $_GET['password'];  //value 123456
  ?>
```

<hr />

<h5  dir="rtl" id="addheaders">افزودن Header ها</h5>

```java
  Webi.with(context)
      .from("http://www.example.com")
      .addHeader("Content-Type","application/json")
      .addHeader("Authorization","Bearer.....")
      .connect();
```

<h6  dir="rtl" id="ifyouwanttosendbearertokeninheaderusethismethod">اگر میخواهید bearer token را در header ارسال کنید از این تابع استفاده کنید</h6>

```java
  .setBearerToken("e.g. GJKRY78579tFYIlkdhiipEE908y0FD80")
```

<hr />

<h5  dir="rtl" id="jsonarrayjsonobjectandxmlrequestwithwebi">درخواست JsonArray , JsonObject و XML با webi</h5>

```java
  Webi.with(this).from("http://www.example.com")
      .setOnJsonArrayReceive(new OnJsonArrayReceive() {
          @Override
          public void jsonArray(JSONArray jsonArray, String where) {

              //توجه کنید jsonArray با کاراکتر های روبه رو شروع و خاتمه میپذیرد '[',']'

          }
      })
      .setOnJsonObjectReceive(new OnJsonObjectReceive() {
          @Override
          public void jsonObject(JSONObject jsonObject, String where) {

              //توجه کنید اولین کاراکتر JsonObject '{' است و اخرین نیز '}'

          }
      })
      .setOnXmlRecevie(new OnXmlReceive() {
          @Override
          public void xml(Document xml, String where) {


          }
      }).connect();
```

<hr />

<div dir="rtl">

<h5 id="cachinginwebi">کش کردن در webi</h5>

<h6 id="therearethreewaystocache">سه روش برای کس در webi وجود دارد</h6>

<ul>
 <li>ram cache - بسیار سریع ولی نا ماندگار</li>

<li>xml cache - سریع ولی در این کتابخانه برای آن محدودیت 50 داده قرار داده شده است و در صورتی که بیش از آن مقدار باشد اطاعات پاک شده و از نوع ذخیره میشود</li>

<li>sql cache -نامحدود در ذخیره سازی بیش از 5000 سند اما سرعت کمتر نسبت به بقیه</li>
</ul>

</div>

```java
  Webi.with(context)
      .from("http://www.example.com")
      .setSqlCache(true) //It is better to have only one active Caching method
      .setXmlCache(true)
      .setRamCache(true)
      .connect();
```

<h6 id="setsqlcachetruekey"><code>.setSqlCache(true,key)</code></h6>

<p><code>.setXmlCache(true,key)</code></p>

<h6 dir="rtl" id="yourcustomkeyforstorageifyoudonotsetanautomatichashmd5keywillbegenerated">کلید اخصاصی شما برای ذخیره آن سند و اگر وارد نکنید به صورت خودکار به صورت هش شده تولید میشود</h6>

<h5 dir="rtl" id="workoffline">فعالیت افلاین</h5>

<p  dir="rtl">زمانی که فعالیت افلاین فعال باشد اطلاعات فقط از حافظه خوانده میشود و حافظه ها اپدیت نمیشود</p>

```java
  .setWordOffline(true)
```

<hr />

<h5 dir="rtl" id="encryptcachecontent">رمز نگاری محتوا حافظه ها</h5>

<h6  dir="rtl" id="whenencryptcacheisenabledthedataisencryptbeforeinsertion">زمانی که فعال باشد داده ها قبل از ذخیره در حافظه کش رمزنگاری میشود</h6>

```java
  .setEncryptCache(true)
```
<hr />

<h5  dir="rtl" id="setcustomconnecttimeoutreadtimeout">تنظیم تایم اوت های اختصاصی خود</h5>

```java
  .setConnectTimeOut(10000)
  .setReadTimeOut(10000)
  //پیش فرض برای هر دو پنج هزار میلی ثانیه است
```

<hr />

<h5 dir="rtl"
 id="getresponsetime">دریافت زمان پاسخ گویی</h5>

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

<hr />

<h5 dir="rtl"
 id="callbackwhenrequestfailed">تابعی برای زمانی که درخواست ناموفق باشد</h5>

 ```java
   .setOnFailed(new OnFailed() {
       @Override
       public void failed(int code) {
           //code is http response code e.g. 404
       }
   }).
 ```
<hr />

<h5 dir="rtl" id="useproxyinwebi">استفاده از proxy در Webi</h5>

```java
.setProxy(host,port).
   or
.setProxy(host,port,username,password).

```
<hr />

<h3   dir="rtl" id="webiconfiginit">()WebiConfig.init</h3>

<h4  dir="rtl" id="usethisclasstochangewebdefaults">از این کلاس برای تغییر پیش فرض های webi استفاده کنید</h4>


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

<h5 dir="rtl" id="itwillbetterperformanceifcallandsetfirstofallinapplicationclass">بهتر است که تنظیمات پیشفرض خود را قبل از همه و در کلاس  application  تنظیم کنید</h5>

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
<h5 dir="rtl" id="manifest">manifest</h5>

```XML
<manifest>
    <application
        android:name=".myApplication"
        ...
        >

    </application>
</manifest>
```

<h5 dir="rtl" id="someofwebiconfigclassmethods">برخی از تابع های کلاس WebiConfig</h5>

<p><img src="https://raw.githubusercontent.com/alirezaashrafi/webi/master/pics/config.png" ></p>

<hr />

<h2  dir="rtl"
 id="licence">لایسنس</h2>

<p>Copyright 2017 Alireza Ashrafi</p>

<p>Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at</p>

<pre><code>http://www.apache.org/licenses/LICENSE-2.0
</code></pre>

<p>Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.</p>

<hr />

<div dir="rtl">

<h2 id="author">برنامه نویس</h2>

<ul>
<li><a href="https://github.com/alirezaashrafi">علیرضا اشرفی</a></li>

<li><a href="http://alirezaashrafi.ir">وبسایت : alirezaashrafi.ir</a></li>

<li><a href="alirezaashrafi.email@gmail.com">ایمیل : alirezaashrafi.email@gmail.com</a></li>

<li>موبایل &amp; واتس اپ +98 901 396 2648</li>

<li><a href="http://t.me/dr_khaki">تلگرام : @dr_khaki</a></li>
</ul>

</div>
<hr />

<h2 dir="rtl" id="eventuallyseeourotherlibrariesandprojectshttpsgithubcomalirezaashrafi"><a href="https://github.com/alirezaashrafi/">در نهایت از دیگر کتابخانه ها و پروژه های ما بازدید کنید</a></h2>
