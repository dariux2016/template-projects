FROM openjdk:8-jre
EXPOSE 8888
ADD target/spring-boot-banner.jar .
CMD java -Djava.awt.headless=true \
		 -server \
		 -XX:+PrintFlagsFinal \
		 -XX:+UseContainerSupport \
		 -XX:+AggressiveOpts \
		 -XX:+UseG1GC \
		 -XX:MaxRAMPercentage=40.0 \
		 -XX:+DisableExplicitGC \
		 -XX:+UnlockExperimentalVMOptions \
		 -XX:+UseCGroupMemoryLimitForHeap \
		 -jar spring-boot-banner.jar
