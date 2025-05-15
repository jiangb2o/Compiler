@echo off
cd src
javac -d ..\bin -classpath ..\bin parser\*.java parser\expr\*.java parser\token\*.java
cd ..
pause
@echo on
