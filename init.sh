export SERVICEUSER_USERNAME=$(cat /secrets/serviceuser/dialogfordeler/username)
export SERVICEUSER_PASSWORD=$(cat /secrets/serviceuser/dialogfordeler/password)

export SPRING_DATASOURCE_URL=$(cat /secrets/dialogfordelerdb/config/jdbc_url)
export SPRING_DATASOURCE_USERNAME=$(cat /secrets/dialogfordelerdb/credentials/username)
export SPRING_DATASOURCE_PASSWORD=$(cat /secrets/dialogfordelerdb/credentials/password)
