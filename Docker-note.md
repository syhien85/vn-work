## Push to docker on Ubuntu:

- chú ý: ko để thông số #server.port=8888 trong application.properties
- mvn clean package
- copy file `target/Project3-0.0.1-SNAPSHOT.jar` to host: `/home/project_name/target/`
- copy file `Dockerfile`, `docker-compose.yml` (if changes) to host: `/home/project_name/`

```sh
clear;
```
```bash
mvn clean package
```
```bash 
docker compose up -d vn-work_database
```
```bash 
docker compose up -d vn-work_backend
```
```bash 
docker compose down vn-work_redis; docker compose up -d vn-work_redis;
```
```bash
docker compose build
```

docker compose build vn-work_backend; docker compose up -d vn-work_backend;

## Update image and rebuild container

```
mvn clean package
```

```bash
docker compose down vn-work_backend
```
```bash
docker image rm job1-vn-work_backend:latest
```
```bash
docker volume rm job1_vn-work_backend
```
```bash
docker compose up -d vn-work_backend
```

```bash
#docker compose down vn-work_database
#docker compose down job1-backend
#docker image rm job1-backend:latest
#docker volume rm job1_backend
#docker compose up -d vn-work_backend
```

### Info
```bash
docker logs backend; #logs container backend
docker ps; # list container running
docker image ls; # list all image
docker volume ls; # list all volume
```
```bash
docker exec -it mysqldb mysql -uroot -p; # login to mysql container
```

```bash
docker rm -vf $(docker ps -aq); docker rmi -f $(docker images -aq)
```