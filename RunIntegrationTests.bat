REM @echo off

call SetClasspath

REM @echo on

cd database
call BackupDB.bat
call RestoreDB.bat
cd..

REM java junit.textui.TestRunner tests.RunIntegrationTests
java junit.swingui.TestRunner tests.RunIntegrationTests

cd database
call RestoreBackup.bat
cd..