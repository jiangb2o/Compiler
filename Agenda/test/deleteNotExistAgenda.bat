@echo off
(
echo batch deleteNotExistAgenda.txt
) | java -cp ..\bin AgendaService
pause