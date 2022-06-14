@REM docker stop $(docker ps -a -q --filter ancestor=coindesk-demo)
@REM docker rm $(sudo docker ps -a -q --filter ancestor=coindesk-demo)
@REM docker image rm -f coindesk-demo
@REM docker build -t coindesk-demo .

docker run -it coindesk-demo
@REM docker run -e "SPRING_PROFILES_ACTIVE=dev" -it coindesk-demo