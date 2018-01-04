<body  dir="rtl">
</body>
<p><img src="https://raw.githubusercontent.com/alirezaashrafi/webi/master/pics/webi.jpg" width="100%"></p>

<p  dir="rtl"><a href="https://jitpack.io/#alirezaashrafi/webi"><img src="https://jitpack.io/v/alirezaashrafi/webi.svg" alt="" /></a></p>

<h4 dir="rtl">وبی کتابخانه سریع و پر از ویژگی ها برای اتصال به سرور است و از ویژگی های آن میتوان به راحتی و کش کردن پاسخ ها برای نرم افزار های اندرویدی اشاره کرد که میتوان گفت قابل مقایسه و حتی بهتر از کتابخانه های مشابه است</h4>


<ul dir="rtl">
<li>راحتی در استفاده</li>

<li>برنامه نویسی کمتر</li>

<li>چند نخی و پر سرعت</li>

<li>دربرداشتن روش های مختلف برای کش</li>

<li>قابلیت دریافت jsonArray,jsonObject,xml</li>

<li>راحتی در ارسال و پست مقادیر به سرور</li>
</ul>

<hr />

<h2 id="readmemdenglishreadmehttpsgithubcomalirezaashrafiwebiblobmasterreadmefamd"><a href="https://github.com/alirezaashrafi/webi/blob/master/README.fa.md">readme.md english readme </a></h2>

<hr />

<h2 dir"rtl">چگونه وبی را دانلود کنیم</h2>

<h4 id="gradle">Gradle</h4>

<h6 id="buildgradle">این کد را به build.gradle پروژه خود اضافه کنید</h6>

<pre><code class="java language-java">    allprojects {
         repositories {
             ...
             maven { url 'https://jitpack.io' }
         }
    }
</code></pre>

<h6 id="addthislinetoyourmodulebuildgradledependeciesblock">add this line to your module build.gradle dependecies block:</h6>

<pre><code class="java language-java">    compile 'com.github.alirezaashrafi:webi:4.0.0'
</code></pre>

<h3 id="maven">Maven</h3>

<h6 id="addthejitpackrepositorytoyourbuildfile">Add the JitPack repository to your build file</h6>

<pre><code class="xml language-xml">  &lt;repositories&gt;
    &lt;repository&gt;
        &lt;id&gt;jitpack.io&lt;/id&gt;
      &lt;url&gt;https://jitpack.io&lt;/url&gt;
    &lt;/repository&gt;
  &lt;/repositories&gt;
</code></pre>

<h6 id="addthedependency">Add the dependency</h6>

<pre><code class="xml language-xml">  &lt;dependency&gt;
    &lt;groupId&gt;com.github.alirezaashrafi&lt;/groupId&gt;
    &lt;artifactId&gt;webi&lt;/artifactId&gt;
    &lt;version&gt;4.0.0&lt;/version&gt;
  &lt;/dependency&gt;
</code></pre>

<hr />

<h2 id="howtousewebi">How to use webi</h2>

<pre><code class="java language-java">  Webi.with(context)
      .from("http://www.example.com")
      .onResponse(new OnResponse() {
          @Override
          public void Response(String response, String where) {
              //response is content of url
              //where is type of load response from e.g. online ram xml or sql
          }
      }).connect();
</code></pre>

<hr />

<h5 id="retryonfailed">Retry on failed</h5>

<pre><code class="java language-java">  .setRetryTimes(int times) //Default is one time

  .setOnRetry(new OnRetry() {
      @Override
      public void retry(int remainingRetrys) {
          //call back when retry
      }
  });
</code></pre>

<hr />

<h5 id="postwithwebi">Post with webi</h5>

<p><code>.addPost(key,value,description);</code> note: description is meta data and these are not sent with your HTTP request</p>

<pre><code class="java language-java">  Webi.with(context)
      .from("http://www.example.com")
      .addPost("username","webi")
      .addPost("password","123456")
      .connect();
</code></pre>

<h5 id="andreceivethispostsinphp">and receive this posts in php</h5>

<pre><code class="php language-php">  &lt;?php
      $username = $_POST['username'];  //value webi
      $password = $_POST['password'];  //value 123456
  ?&gt;
</code></pre>

<h5 id="postyourcustomlist">Post your custom list</h5>

<pre><code class="java language-java">  List&lt;Posts&gt;postsList = new ArrayList&lt;&gt;();
  Posts posts = new Posts();
  posts.setKey("...");
  posts.setValue("...");
  postsList.add(posts);
  ....

  Webi.with(context)
    .from("http://www.example.com")
    .addPostList(postsList)
    .connect();
</code></pre>

<hr />

<h5 id="addgetmethodparams">Add GET method params</h5>

<pre><code class="java language-java">  Webi.with(context)
      .from("http://www.example.com")
      .addGet("username","webi")
      .addGet("password","123456")
      .connect();

      //res http://www.example.com?username=webi&amp;password=123456
</code></pre>

<h6 id="andgetthatvaluesinphp">and get that values in php</h6>

<pre><code class="php language-php">  &lt;?php
      $username = $_GET['username'];  //value webi
      $password = $_GET['password'];  //value 123456
  ?&gt;
</code></pre>

<hr />

<h5 id="addheaders">Add Headers</h5>

<pre><code class="java language-java">  Webi.with(context)
      .from("http://www.example.com")
      .addHeader("Content-Type","application/json")
      .addHeader("Authorization","Bearer.....")
      .connect();
</code></pre>

<h6 id="ifyouwanttosendbearertokeninheaderusethismethod">If you want to send bearer token in header use this method</h6>

<pre><code class="java language-java">  .setBearerToken("e.g. GJKRY78579tFYIlkdhiipEE908y0FD80")
</code></pre>

<hr />

<h5 id="jsonarrayjsonobjectandxmlrequestwithwebi">JsonArray , JsonObject and XML request with webi</h5>

<pre><code class="java language-java">  Webi.with(this).from("http://www.example.com")
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
</code></pre>

<hr />

<h5 id="cachinginwebi">Caching in webi</h5>

<h6 id="therearethreewaystocache">There are three ways to cache</h6>

<ul>
<li>ram cache - fastest but temporary</li>

<li>xml cache - fast but cache only 50 response</li>

<li>sql cache - permanent and Unlimited cache about 5,000 response but Slower than the rest</li>
</ul>

<pre><code class="java language-java">  Webi.with(context)
      .from("http://www.example.com")
      .setSqlCache(true) //It is better to have only one active Caching method
      .setXmlCache(true)
      .setRamCache(true)
      .connect();
</code></pre>

<h6 id="setsqlcachetruekey"><code>.setSqlCache(true,key)</code></h6>

<p><code>.setXmlCache(true,key)</code></p>

<h2 id="yourcustomkeyforstorageifyoudonotsetanautomatichashmd5keywillbegenerated">###### Your custom key for storage If you do not set, an automatic hash (MD5) key will be generated</h2>

<h5 id="workoffline">Work offline</h5>

<p>When work offline is enable, content is only read from cache and cache content is not update</p>

<pre><code class="java language-java">  .setWordOffline(true)
</code></pre>

<hr />

<h5 id="encryptcachecontent">Encrypt cache content</h5>

<h6 id="whenencryptcacheisenabledthedataisencryptbeforeinsertion">When encryptCache is enabled, the data is encrypt before insertion</h6>

<pre><code class="java language-java">  .setEncryptCache(true)
</code></pre>

<hr />

<h5 id="setcustomconnecttimeoutreadtimeout">Set custom connectTimeOut &amp; readTimeOut</h5>

<pre><code class="java language-java">  .setConnectTimeOut(10000)
  .setReadTimeOut(10000)
  //The default of both is 5000 ms
</code></pre>

<hr />

<h5 id="getresponsetime">Get Response Time</h5>

<pre><code class="java language-java">  Webi.with(context)
    .from("http://www.example.com")
    .getResponseTime(new GetResponseTime() {
        @Override
        public void time(long time) {
            //e.g. 430 ms
        }
    }).
</code></pre>

<hr />

<h5 id="callbackwhenrequestfailed">call back when request failed</h5>

<pre><code class="java language-java">  .setOnFailed(new OnFailed() {
      @Override
      public void failed(int code) {
          //code is http response code e.g. 404
      }
  }).
</code></pre>

<hr />

<h5 id="useproxyinwebi">use proxy in Webi</h5>

<pre><code class="java language-java">.setProxy(host,port).
   or
.setProxy(host,port,username,password).
</code></pre>

<hr />

<h3 id="webiconfiginit">WebiConfig.init()</h3>

<h4 id="usethisclasstochangewebdefaults">Use this class to change web defaults</h4>

<pre><code class="java language-java">    WebiConfig.init()
        .setDefaultUrl("http://alirezaashrafi.ir")
        .setDefaultConnectTimeOut(15000)
        .setDefaultReadTimeOut(15000);

    Webi.with(this).onResponse(new OnResponse() {
        @Override
        public void Response(String res, String where) {

        }
    });
</code></pre>

<h5 id="itwillbetterperformanceifcallandsetfirstofallinapplicationclass">It will better performance if call and set first of all in application class</h5>

<pre><code class="java language-java">  public class myApplication extends Application {
      @Override
      public void onCreate() {
          WebiConfig.init()
                  .setDefaultUrl("http://alirezaashrafi.ir")
                  .setDefaultConnectTimeOut(15000)
                  .setDefaultReadTimeOut(15000);


          super.onCreate();
      }
  }
</code></pre>

<h5 id="manifest">manifest</h5>

<pre><code class="XML language-XML">&lt;manifest&gt;
    &lt;application
        android:name=".myApplication"
        ...
        &gt;

    &lt;/application&gt;
&lt;/manifest&gt;
</code></pre>

<h5 id="someofwebiconfigclassmethods">some of WebiConfig class methods</h5>

<p><img src="https://raw.githubusercontent.com/alirezaashrafi/webi/master/pics/config.png" ></p>

<hr />

<h2 id="licence">Licence</h2>

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

<h2 id="author">Author</h2>

<ul>
<li><a href="https://github.com/alirezaashrafi">Alireza Ashrafi</a></li>

<li><a href="http://alirezaashrafi.ir">web site : alirezaashrafi.ir</a></li>

<li><a href="alirezaashrafi.email@gmail.com">email : alirezaashrafi.email@gmail.com</a></li>

<li>Phone &amp; Whatsapp +98 901 396 2648</li>

<li><a href="http://t.me/dr_khaki">telegram : @dr_khaki</a></li>
</ul>

<hr />

<h2 id="eventuallyseeourotherlibrariesandprojectshttpsgithubcomalirezaashrafi"><a href="https://github.com/alirezaashrafi/">Eventually see our other libraries and projects</a></h2>
