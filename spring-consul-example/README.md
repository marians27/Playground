# Consul with spring cloud
Example shows:
* how to read application properties from consul using spring cloud
* how to register application as a service in consul
* how to deploy application propertieson consul using maven plugin

## Usage
### Starting consul docker container
```
docker run -d --name consul -p 8500:8500 consul
```

### Exporting properties to consul
```
cd config
mvn clean package consulkv:configure -Ptest
```

### Starting application
#### Run application reading properties from consul
```
mvn clean package
java -jar ./core/target/spring-consul-example-core-1.0-SNAPSHOT.jar --spring.profiles.active=test
```

#### Run application reading properties from properties files within jar without consul
```
mvn clean package
java -jar ./core/target/spring-consul-example-core-1.0-SNAPSHOT.jar --spring.profiles.active=local
```
