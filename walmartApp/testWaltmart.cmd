set home = %~dp0
cd %home%
cls
rem cmd /c
set MAVEN_OPTS=-Xms1G -Xmx1G
mvn integration-test -Dit.test=Waltmart_IT#testWaltmartJson > WaltMart.log.xml
pause
