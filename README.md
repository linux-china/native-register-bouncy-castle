# HOWTO: Build and run Spring Boot with GraalVM native and Bouncy Castle

## Prerequisites:

- GraalVM 21.0.x
- Bouncy Castle 1.78 or higher

Other versions might work in different ways

## Problem:

Using Bouncy Castle with GraalVM native will result in missing classes and/or exception
`ava.lang.RuntimeException: com.oracle.svm.core.jdk.UnsupportedFeatureError: Trying to verify a provider that was not registered at build time: BC version 1.xx. All providers must be registered and verified in the Native Image builder`

## Steps to solve:

1. Create a fine running Spring Boot app in java mode
2. Add a static initializer for Bouncy Castle like
   this: [BouncyCastleInitializer.java](./src/main/java/com/security/registerbouncycastle/BouncyCastleInitializer.java)
3. Add initializations using native-maven-plugin, like here: [pom.xml](./pom.xml)

```
<plugin>
	<groupId>org.graalvm.buildtools</groupId>
	<artifactId>native-maven-plugin</artifactId>
	<version>0.9.28</version>
	<configuration>
		<buildArgs>
			<arg>--initialize-at-build-time=org.bouncycastle,com.security.registerbouncycastle.BouncyCastleInitializer</arg>
			<arg>--initialize-at-run-time=org.bouncycastle.jcajce.provider.drbg.DRBG$Default,org.bouncycastle.jcajce.provider.drbg.DRBG$NonceAndIV</arg>
		</buildArgs>
	</configuration>
</plugin>
```

4. Use native-image-agent to collect json files for native build:
   `-agentlib:native-image-agent=config-merge-dir=src/main/resources/META-INF/native-image,config-write-period-secs=60,config-write-initial-delay-secs=0`.
   See [collectNativeJson.sh](https://github.com/HarrDevY/native-register-bouncy-castle/blob/main/collectNativeJson.sh)
   and official
   GraalVM [documentation](https://www.graalvm.org/jdk21/reference-manual/native-image/metadata/AutomaticMetadataCollection/).
   This step is optional but it solves build problems in general.
5. Build native app with `mvn -Pnative native:compile`

## Done

Following these steps you should be able to successfully build and run your app.
