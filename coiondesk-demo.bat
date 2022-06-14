@REM docker stop $(docker ps -a -q --filter ancestor=coindesk-demo)
@REM docker rm $(sudo docker ps -a -q --filter ancestor=coindesk-demo)
@REM docker image rm -f coindesk-demo
@REM docker build -t coindesk-demo .

docker pull coldfusion/coindesk-demo:0.1
docker run -it coldfusion/coindesk-demo:0.1
@REM docker run -e "SPRING_PROFILES_ACTIVE=dev" -it coindesk-demo
