

docker run -d -it -p 80:8814 -v ~/Workspace/whattolun/build/libs/:/lunch -v /Workspace/DB/:/root/DB ubuntu

keytool -genkey -alias spring -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore keystore.p12 -validity 4000
