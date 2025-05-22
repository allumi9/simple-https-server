## My implementation of an Http Server
Not exactly low level, it still uses sockets, but request parsing is done by hand.
Also the repo is called "https-server", but i don't even know how to do that yet, so it is definitely not secure. Maybe in future?
### Usage:
- First you'll need Maven 3.9.9 (check with mvn -v).
- And also JDK 21 (java -version). You could also use higher versions, i tried 22 and it worked but that's to your own risks.

 I started it through Intellij Idea, but you can use maven and run the jar:
```git clone https://github.com/allumi9/simple-https-server
cd simple-https-server
mvn clean package
java -jar target/simple-http-server-1.0-SNAPSHOT.jar```
 