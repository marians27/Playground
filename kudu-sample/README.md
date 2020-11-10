# KUDU Sample app
Example shows:
* how to update kudu schema using liquibase and impala jdbc connection
* how to access kudu via Java API
* how to start application with required infrastructure using docker

## Installing dependencies
Not all dependencies are available in maven central repository. Therefore they need to be manually installed.

### Impala JDBC connector
1. Download connector from [Cloudera page](https://www.cloudera.com/downloads/connectors/impala/jdbc/2-6-4.html)
2. Unzip the ImpalaJDBC41.jar file and install in local repository:
    ```
    $ mvn install:install-file \
        -Dfile=ImpalaJDBC41.jar \
        -DgroupId=com.cloudera.impala.jdbc \
        -DartifactId=ImpalaJDBC41 \
        -Dpackaging=jar \
        -Dversion=2.6.4
    ```

### Hive JDBC connector
1. Download connector from [Cloudera page](https://www.cloudera.com/downloads/connectors/hive/jdbc/2-6-2.html)
2. Unzip the ImpalaJDBC41.jar file and install in local repository:
    ```
    $ mvn install:install-file \
        -Dfile=HiveJDBC41.jar \
        -DgroupId=com.cloudera.hive.jdbc \
        -DartifactId=HiveJDBC41 \
        -Dpackaging=jar \
        -Dversion=2.6.2
    ```

### Impala JDBC extension
To run liquibase for impala we need to use [impala extension for liquibase](https://github.com/eselyavka/liquibase-impala)
1. Get source code of liquibase impala extension 
    ```
    $ git clone https://github.com/eselyavka/liquibase-impala.git
    ```
2. Build and install plugin in mavn local repository
    ```
   $ cd liquibase-impala
   $ mvn clean install
   ```

## Creating infrastructure

### Kudu with Impala - simple
The simplest approach to create local infrastructure is to just have kudu and impala within single docker container.

#### Run Kudu and Impala in docker
```
docker run -d --name kudu-impala \
    -p 8050:8050 -p 8051:8051 -p 7050:7050 -p 7051:7051 \
    -p 25000:25000 -p 25010:25010 -p 25020:25020 \
    -p 50070:50070 -p 50075:50075 \
    -p 21050:21050 -p 28000:28000 \
    -e HOSTNAME=localhost \
    --hostname localhost \
    andreysabitov/impala-kudu:latest
```

##### Enter [impala shell](https://docs.cloudera.com/documentation/enterprise/5-9-x/topics/impala_shell_commands.html)
```
docker exec -it kudu-impala impala-shell
```

### Kudu with Impala - cluster
To be done

## Running the app

### Run Liquibase
```
$ cd db
$ mvn liquibase:update -Plocal
```

In case you need to recreate schema from scratch use following command:
 ```
$ cd db
$ mvn liquibase:dropAll -Plocal
 ```

### Start app

```
$ cd app
$ mvn spring-boot:run -Dspring-boot.run.profiles=local
```

### Test app

#### Create new record
```
curl -s -X PUT \
    -H "Content-Type: application/json" \
    -d '{"name":"Alex", "counter":4}' \
    localhost:8080/users/100 
```
#### Get All records from DB
```
curl -s -X GET localhost:8080/users |json_pp
```
#### Get record by id
```
curl -s -X GET localhost:8080/users/100 |json_pp
```


