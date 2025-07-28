build:
   mvn -DskipTests package

# native build
native-build:
   mvn -Pnative -DskipTests clean package native:compile

graalvm-agent: build
   java -agentlib:native-image-agent=config-merge-dir=src/main/resources/META-INF/native-image,config-write-period-secs=60,config-write-initial-delay-secs=0 -cp target/registerbouncycastle-0.0.1-SNAPSHOT.jar org.springframework.boot.loader.launch.JarLauncher