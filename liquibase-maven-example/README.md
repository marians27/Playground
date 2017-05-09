## Run liquibase directly (see liquibase.properties)
```
cd src/main/resources/db
liquibase --classpath=$HOME/.m2/repository/oracle/ojdbc6/11.2.0.3/ojdbc6-11.2.0.3.jar --changeLogFile=ddl.xml updateSQL
```
## Run liquibase from maven - perform migration
```    
mvn process-resources liquibase:update -Pload-ddl,dev
```
## Run liquibase from maven - generate sql
```    
mvn process-resources liquibase:updateSQL -Pload-ddl,dev
```
## Run liquibase from maven - drop schema
```
mvn process-resources liquibase:dropAll -Pdev
```
