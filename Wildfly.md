# Wildfly setup

## Configure PostgreSQL datasource 

http://www.mastertheboss.com/jboss-server/jboss-datasource/configuring-a-datasource-with-postgresql-and-jboss-wildfly

We need a non JTA datasource: --jta=false 

    data-source add --jndi-name=java:/datasources/jBM  --jta=false --name=jBMDS  --connection-url=jdbc:postgresql://dbhost/scuttle --driver-name=postgres --user-name=uuu --password=ppp

## Install Eclipselink as in

https://docs.jboss.org/author/display/WFLY10/JPA+Reference+Guide#JPAReferenceGuide-UsingEclipseLink

## use a jBM-custom.properties for Wildfly

* link it with System Property jBM.custom.config
    -DjBM.custom.config=${jboss.server.config.dir}/jBM-custom.properties
	
* or add it to a Wildfly Global Module: https://docs.jboss.org/author/display/WFLY10/Class+Loading+in+WildFly

We need just one entry for the data source:

    javax.persistence.nonJtaDataSource=java:/datasources/jBM

## Security

Add a Wildfly JEE user having roles jBM (and jBMAPI if you need the API for Android) to

    application-users.properties
    application-roles.properties
    
    