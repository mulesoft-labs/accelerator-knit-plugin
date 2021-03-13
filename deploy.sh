#!/bin/sh
export VERSION=2.0.2
export GROUPID=997d5e99-287f-4f68-bc95-ed435d7c5797
echo Cleaning and building version ${VERSION} of the library...
mvn clean package -DskipTests
echo Deploying version ${VERSION} of the library to the organization Exchange repository...
mvn deploy:deploy-file -Dfile=target/knit-maven-plugin-%VERSION%.jar -DpomFile=pom.xml -DgroupId=%GROUPID% -DartifactId=knit-maven-plugin -Dversion=%VERSION% -Dpackaging=jar -Durl=https://maven.anypoint.mulesoft.com/api/v2/organizations/%GROUPID%/maven -DrepositoryId=accelerator-exchange
