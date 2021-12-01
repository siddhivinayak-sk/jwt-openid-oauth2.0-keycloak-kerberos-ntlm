@echo off
kubectl -n bank-apps get deployment | findstr spring-boot-jwt > tmpfile
set result="N"
for /f "tokens=*" %%i in ('FINDSTR /C:"spring-boot-jwt" tmpfile') do (
  set result=%%i
)

