@ECHO OFF
set flag=0
set success=0
ECHO Running UI build...
(npm install && ng test && ng build --prod) || ( echo npm install or ng test failed, executing ng test... && ng test && ng build --prod) || (echo ng test failed, executing ng build --prod... && ng build --prod) || set flag=1
if %flag% == %success%	(echo UI build successful...) else (echo UI build failed... && end)
exit %flag%
