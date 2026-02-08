Spring Boot Backed Skeleton
```
Invoke-WebRequest -Uri "https://start.spring.io/starter.zip" `
  -Method Post `
  -Body @{
    dependencies = "web,data-jpa,security,postgresql,lombok,devtools"
    type         = "maven-project"
    language     = "java"
    javaVersion  = "21"
    bootVersion  = "4.0.2"               # ← Updated to latest stable
    groupId      = "com.dealerfinance"
    artifactId   = "loan-application-service"
    name         = "loan-application-service"
    packageName  = "com.dealerfinance.loan"
  } `
  -OutFile "loan-app.zip"
  ```