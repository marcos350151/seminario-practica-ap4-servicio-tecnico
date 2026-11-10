@echo off
cd /d "%~dp0"
if not exist out mkdir out
javac -encoding UTF-8 -d out src\*.java
if errorlevel 1 (echo ERROR DE COMPILACION & pause & exit /b 1)
java -cp out PruebasPOO
if errorlevel 1 (echo FALLO POO & pause & exit /b 1)
java -cp out PruebasAP4
if errorlevel 1 (echo FALLO AP4 & pause & exit /b 1)
pause
