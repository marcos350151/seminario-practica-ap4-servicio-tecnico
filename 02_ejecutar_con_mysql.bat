@echo off
cd /d "%~dp0"
if not exist "lib\mysql-connector-j.jar" (
    echo Falta lib\mysql-connector-j.jar . Instala MySQL Connector/J primero.
    pause
    exit /b 1
)
if not exist out mkdir out
javac -encoding UTF-8 -d out src\*.java
if errorlevel 1 (echo ERROR DE COMPILACION & pause & exit /b 1)
java -cp "out;lib/mysql-connector-j.jar" Main
pause
