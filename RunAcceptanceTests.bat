REM @echo off

call SetClasspath

REM @echo on

cd database
call BackupDB.bat
call RestoreDB.bat
cd..

IF "%1" NEQ "" (SET SLEEP=%1) ELSE (SET SLEEP=1)

java -cp %CLASSPATH% acceptanceTests.TestRunner %SLEEP%

cd database
call RestoreBackup.bat
cd..