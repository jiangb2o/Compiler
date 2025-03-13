@echo off
javac -d bin src/*.java
echo Test Case:
echo 1500 under threshold
echo 2077  level1
echo 3500  level2
echo 4300  level3
echo 21599 level4
echo 99999 level5
echo.
java -cp bin Main 1500 2077 3500 4300 21599 99999
pause