@REM docker stop $(docker ps -a -q --filter ancestor=coindesk-cli)
@REM docker rm $(sudo docker ps -a -q --filter ancestor=coindesk-cli)
@REM docker image rm -f coldfusion/coindesk-cli
@REM docker build -t coldfusion/coindesk-cli .
@REM docker tag coldfusion/coindesk-cli coldfusion/coindesk-cli:0.2
@REM docker push coldfusion/coindesk-cli:0.2
@REM
@REM docker pull coldfusion/coindesk-cli:0.2
@REM docker run -it coldfusion/coindesk-cli:0.2
docker run -e "SPRING_PROFILES_ACTIVE=dev" -it coldfusion/coindesk-cli:0.2
