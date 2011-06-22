
@echo OFF

set program=%1
set args=%*
set package=net.praqma.cli

IF NOT "%program%"=="" GOTO programok

echo.
echo The program was not given
echo.

EXIT /B 1

:programok

IF NOT "%VANS_HOME%"=="" GOTO vanshomeok

echo.
echo VANS_HOME is not set
echo.

EXIT /B 1

:vanshomeok

set VANS_JAR=%VANS_HOME%\build\VANS-0.1.3-jar-with-dependencies.jar
echo %VANS_JAR%
if exist "%VANS_JAR%" goto start1

echo.
echo The jar library cannot be found
echo.

EXIT /B 1

:start1

set vans=java -classpath "%VANS_JAR%" %package%.%program% %args%

call %vans%

@GOTO :EOF

:WRONG_PARAMS

ECHO %1 is missing

EXIT /B 
GOTO :EOF