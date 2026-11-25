@echo off
chcp 65001 > nul
cd /d "%~dp0src"
java -Dfile.encoding=UTF-8 BibliotecaConjuntos.java --pruebas
pause
