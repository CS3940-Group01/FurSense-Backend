@echo off
echo Stopping all running Java microservices...

:: This will kill ALL java.exe processes
taskkill /F /IM java.exe

echo All Java processes terminated.
pause
