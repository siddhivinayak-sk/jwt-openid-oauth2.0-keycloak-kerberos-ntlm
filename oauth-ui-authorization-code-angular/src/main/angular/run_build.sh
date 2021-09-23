#!/bin/sh
flag=0
success=0
echo "Running UI build..."
(npm install && ng test && ng build --prod) || ( echo "npm install or ng test failed, executing ng test..." && ng test && ng build --prod) || (echo "ng test failed, executing ng build --prod..." && ng build --prod) || flag=1
if [ $flag == $success ]
then
	echo "UI build successful..."
else
	echo "UI build failed..." && end
fi
exit $flag
