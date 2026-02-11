#!/usr/bin/env bash

# =============================================================================
# start-backend.sh
# Starts PostgreSQL + Spring Boot backend and shows live server logs
#
# Usage:
#   ./start-backend.sh
#
# Requirements:
#   - Docker & Docker Compose installed and running
#   - Java 21 + Maven wrapper (mvnw) in backend/loan-application-service/
# =============================================================================

set -euo pipefail

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${GREEN}Starting Dealer Finance Backend...${NC}\n"

# 1. Start PostgreSQL (detached)
echo -e "${YELLOW}Starting PostgreSQL container...${NC}"
docker compose up -d db

# Wait a few seconds for Postgres to be ready
sleep 5

# Check if postgres is healthy
if ! docker compose ps db | grep "healthy" > /dev/null; then
  echo -e "${RED}PostgreSQL container is not healthy yet. Waiting 10 more seconds...${NC}"
  sleep 10
fi

# 2. Build & start Spring Boot (from backend/loan-application-service)
echo -e "\n${YELLOW}Building & starting Spring Boot application...${NC}"
cd backend/loan-application-service || { echo -e "${RED}Cannot find backend/loan-application-service folder${NC}"; exit 1; }

# Build (optional - remove if you want faster restarts)
./mvnw clean compile

# Run in foreground so we see logs
./mvnw spring-boot:run

# If you ever want to run it in background and tail logs separately, use:
# nohup ./mvnw spring-boot:run > backend.log 2>&1 &
# tail -f backend.log