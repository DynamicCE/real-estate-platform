#!/bin/bash

# Start Docker Desktop if not running
if ! docker info > /dev/null 2>&1; then
    echo "Docker Desktop başlatılıyor..."
    open -a Docker
    echo "Docker'ın başlamasını bekliyoruz (30 saniye)..."
    sleep 30
fi

# Build all services
echo "Building Discovery Service..."
cd discovery-service
mvn clean package -DskipTests
cd ..

echo "Building User Service..."
cd user-service
mvn clean package -DskipTests
cd ..

# Stop and remove existing containers
echo "Eski containerları temizliyorum..."
docker-compose down

# Start all services using Docker Compose
echo "Servisleri başlatıyorum..."
docker-compose up --build -d

# Function to check service health
check_service() {
    local service=$1
    local max_attempts=30
    local attempt=1

    echo "Checking $service health..."
    while [ $attempt -le $max_attempts ]; do
        if docker-compose ps $service | grep -q "healthy"; then
            echo "$service is healthy!"
            return 0
        fi
        echo "Waiting for $service to be healthy... (Attempt $attempt/$max_attempts)"
        sleep 5
        attempt=$((attempt + 1))
    done
    echo "$service failed to become healthy"
    return 1
}

# Check each service
check_service postgres
check_service discovery-service
check_service user-service

# Show logs if all services are healthy
if [ $? -eq 0 ]; then
    echo "Tüm servisler sağlıklı! Logları gösteriyorum..."
    docker-compose logs -f
else
    echo "Bazı servisler başlatılamadı. Lütfen logları kontrol edin."
    docker-compose logs
fi 