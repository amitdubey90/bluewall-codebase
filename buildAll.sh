mvn -f bluewallUtility/bluewallUtility/pom.xml clean compile package install
mvn -f userDeviceDataCollector/UserDeviceData/pom.xml clean compile package assembly:single
mvn -f userDeviceDataMapper/userDeviceDataMapper/pom.xml clean compile package assembly:single
mvn -f bluewallFrontEndServices/bluewallFrontEndServices/pom.xml clean compile package install