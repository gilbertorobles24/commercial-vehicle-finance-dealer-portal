# start-backend.ps1
# Starts PostgreSQL + Spring Boot backend and shows live server logs
#
# Usage:
#   .\start-backend.ps1
#
# Requirements:
#   - Docker Desktop installed and running (with Compose)
#   - Java 21 + Maven wrapper (mvnw) in backend/loan-application-service/

Write-Host "Starting Dealer Finance Backend..." -ForegroundColor Green
Write-Host ""

# 1. Start PostgreSQL (detached)
Write-Host "Starting PostgreSQL container..." -ForegroundColor Yellow
docker compose up -d db

# Wait a few seconds for Postgres to be ready
Write-Host "Waiting 5 seconds for database to initialize..." -ForegroundColor Yellow
Start-Sleep -Seconds 5

# Check container status (simple check - looks for 'healthy' or 'running')
$status = docker compose ps db --format "{{.Status}}"
if ($status -notmatch "healthy|running") {
    Write-Host "PostgreSQL container is not ready yet. Waiting 10 more seconds..." -ForegroundColor Red
    Start-Sleep -Seconds 10
}

# 2. Build & start Spring Boot
Write-Host ""
Write-Host "Building & starting Spring Boot application..." -ForegroundColor Yellow

# Change to backend directory
Set-Location -Path "backend/loan-application-service" -ErrorAction Stop
if (-not (Test-Path "mvnw.cmd")) {
    Write-Host "Cannot find mvnw.cmd in backend/loan-application-service folder" -ForegroundColor Red
    exit 1
}

# Optional: Clean & compile (uncomment if you want fresh build every time)
# & .\mvnw clean compile

# Run in current window so you see live logs
& .\mvnw spring-boot:run

# If you want background run + log tail (alternative):
# Start-Process -NoNewWindow -FilePath ".\mvnw" -ArgumentList "spring-boot:run" -RedirectStandardOutput "backend.log" -RedirectStandardError "backend.log"
# Get-Content backend.log -Wait