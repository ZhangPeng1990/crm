echo " ======================== start webapp install package "

cd "D:\zp-workspace\gdsap-framework"
call mvn clean package install deploy -Dmaven.test.skip=true

cd "D:\zp-workspace\gdsap-adminconsole-webroot"
call mvn clean package -Dmaven.test.skip=true

echo " ======================== maven command end."