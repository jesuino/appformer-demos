Standalone Editor
--
Microservice Standalone editor that can edit files from a local git repository.

To build and run:

~~~
mvn clean install
java -jar standalone-editor-thorntail.jar 
~~~

Then access: `http://localhost:8080/index.html?asset=readme.md` and you should see a readme file.

From the dir where you run the java command you should have a .niogit and inside system. This is the default space used by Standalone Editor (System). Inside system you should see editor.git. Use `git clone editor.git` and go inside the cloned editor directory, create a file, save, commit, push and it should be accessible in the editor using the query param asset.

Docker
--

After you run `mvn clean install` you can run the image in Docker. First build the image:

~~~
docker build . -t standalone-editor
~~~

Then you can start it:

~~~
docker run -p 8080:8080 standalone-editor
~~~

Once it is started access the editor using: `http://localhost:8080/index.html?asset=readme.md`

If you want to try this application in your docker pull the image `jesuino/standalone-editor:latest`
