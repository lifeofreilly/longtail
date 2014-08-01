longtail
========

An automated page-load performance monitor built using Selenium, Firebug, NetExport, MongoDB, and HAR (HTTP Archive) files.

Concept:
-------------------------

This utility can be used to measure and record page-load performance for web pages. It carries out the following steps:
* Creates a Firefox Selenium Webdriver with required plugins
* Loads the target page in Firefox
* Exports the resulting HAR (HTTP Archive) file(s)
* Repeats the test based on the sample size specified
* Stores the resulting metadata in MongoDB
* Archives the complete HAR file for later examination using your HAR viewer of choice e.g. http://www.softwareishard.com/har/viewer/

When executed frequently (e.g., once an hour), the desired page-load performance statistics can be queried from mongo.

Definitions:
-------------------------

* onContentLoad: Content Load events that are triggered after the HTML file is delivered, but before external files like images are loaded.
* onLoad: On load is triggered after the entire document, images and all, are loaded.

Prerequisites:
-------------------------

* JDK 1.6 or higher
* Maven
* Firefox
* MongoDB
* For additional dependency information refer to pom.xml

Build Instructions:
-------------------------

Adjust the default paths and MongoDB settings in:
* ./src/main/java/com/gmail/lifeofreilly/longtail/Longtail.java
* ./src/test/java/com/gmail/lifeofreilly/longtailHARProcessorTest.java

Building an Executable Jar:
> mvn package

The standalone executable jar can be found at: ./target/longtail-1.0-SNAPSHOT-jar-with-dependencies.jar

Usage:
-------------------------

Start MongoDB:
```
> mongod --dbpath <path to data directory>
```

Running as an executable jar:
```
> java -jar ./target/longtail-1.0-SNAPSHOT-jar-with-dependencies.jar
```
Log information will be output to ./logs/error.log

Example Results:
-------------------------

```
> mongo
MongoDB shell version: 2.6.3
connecting to: test
> use mongo
switched to db mongo
> db.har.findOne() ;
{
	"_id" : ObjectId("53d9aee63004a842d56cc303"),
	"time" : "2014-07-30T19:50:02.614-07:00",
	"title" : "Bing",
	"onLoad" : 1652,
	"status" : 200,
	"file" : "www.bing.com+2014-07-30+19-50-07.har",
	"onContentLoad" : 1607,
	"url" : "http://www.bing.com/"
}
```

Test Execution:
-------------------------

Execute all unit tests:
```
> mvn test
```

License
-------------------------
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.