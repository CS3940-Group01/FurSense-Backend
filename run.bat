@echo off
setlocal enabledelayedexpansion

:: Base directory of your microservices
set "BASE_DIR=C:\Users\janin\Desktop\FurSense-Backend"

:: Get service name from user argument
if "%~1"=="" (
    echo Usage: %~nx0 ^<microservice-name^>
    exit /b 1
)
set "SERVICE=%~1"

echo Restarting service: %SERVICE%

:: Function to kill the java process running this microservice
:: We'll match the window title of the process (which we set when starting service)

echo Looking for running process of %SERVICE%...

:: List java processes with window titles, find one matching %SERVICE%
for /f "tokens=2 delims=," %%P in ('tasklist /FI "IMAGENAME eq java.exe" /v /fo csv ^| findstr /I "%SERVICE%"') do (
    set "PID=%%~P"
)

if defined PID (
    echo Found process with PID: !PID! - killing it...
    taskkill /F /PID !PID!
    timeout /t 3 /nobreak >nul
) else (
    echo No running process found for %SERVICE%.
)

:: Build the microservice
echo Building %SERVICE%...
cd /d "%BASE_DIR%\%SERVICE%"
call mvn clean install -DskipTests
if errorlevel 1 (
    echo ❌ Build failed for %SERVICE%.
    exit /b 1
)

:: Find the JAR file
set "JAR_FILE="
for /f "delims=" %%F in ('dir /b /s target\*.jar 2^>nul') do (
    set "JAR_FILE=%%F"
    goto :foundJar
)

echo ⚠️ No JAR file found for %SERVICE%
exit /b 1

:foundJar
echo Starting %SERVICE% from !JAR_FILE!

:: Start the service with window title = service name (to identify later)
start "%SERVICE%" cmd /c "java -jar "!JAR_FILE!" > %SERVICE%.log 2>&1"

echo ✅ %SERVICE% restarted successfully.
pause
