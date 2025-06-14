@echo off
setlocal enabledelayedexpansion

:: Define base directory
set "BASE_DIR=C:\Users\DOWNLOAD\Desktop\fursense\FurSense-Backend"
set SERVICES=gateway authService notifications userService petService

echo 📦 Building microservices...

for %%S in (%SERVICES%) do (
    echo ➡️ Building %%S...
    cd /d "%BASE_DIR%\%%S"
    call mvn clean install -DskipTests
    if errorlevel 1 (
        echo ❌ Failed to build %%S
        exit /b 1
    )
)

echo 🚀 Starting microservices...

for %%S in (%SERVICES%) do (
    call :runService %%S
)

echo ✅ All microservices launched.
pause
exit /b

:runService
set "SERVICE=%1"
cd /d "%BASE_DIR%\%SERVICE%"
set "JAR_FILE="

:: Find the first jar file in the target folder
for /f "delims=" %%F in ('dir /b /s "target\*.jar" 2^>nul') do (
    set "JAR_FILE=%%F"
    goto :foundJar
)

echo ⚠️ No JAR file found for %SERVICE%
goto :eof

:foundJar
echo ▶️ Starting %SERVICE% from !JAR_FILE!
start "%SERVICE%" cmd /c "java -jar "!JAR_FILE!" > %SERVICE%.log 2>&1"
goto :eof
