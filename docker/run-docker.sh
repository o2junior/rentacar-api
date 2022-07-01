docker-compose -f docker-compose.yml down
docker rmi rentcar_pg
docker-compose -f docker-compose.yml up -d
