@echo off
SETLOCAL EnableDelayedExpansion

call mvn clean package

FOR /F "tokens=* USEBACKQ" %%F IN (`dir /s /b *.jar`) DO (SET EXEC_JAR=%%F)

FOR %%P IN (8080, 8090, 8091) DO (START java -jar -Dserver.port=%%P %EXEC_JAR%)
