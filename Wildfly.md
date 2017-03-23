# Wildfly setup

## Configure PostgreSQL datasource 

http://www.mastertheboss.com/jboss-server/jboss-datasource/configuring-a-datasource-with-postgresql-and-jboss-wildfly

## Install Eclipselink as in

https://docs.jboss.org/author/display/WFLY10/JPA+Reference+Guide#JPAReferenceGuide-UsingEclipseLink

## use a jBM-custom.properties for Wildfly

* link it with System Property jBM.custom.config
	-DjBM.custom.config=${}/jBM.custom.config
	
* or add it to a Wildfly Global Module: https://docs.jboss.org/author/display/WFLY10/Class+Loading+in+WildFly

