#!/usr/bin/env bash

# =============================================================================
# send-api-requests.sh
# Sends example curl requests to the running backend API (verbose + pretty JSON).
#
# Usage:
#   ./send-api-requests.sh <password>
#
# Examples:
#   ./send-api-requests.sh dev
# =============================================================================

#!/usr/bin/env bash

# =============================================================================
# send-api-requests.sh
# Sends example curl requests to the backend API (verbose + pretty JSON).
# =============================================================================

set -euo pipefail

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
NC='\033[0m'

if [ $# -ne 1 ]; then
    echo -e "${RED}Error:${NC} Missing password argument."
    echo -e "Usage: ${YELLOW}./send-api-requests.sh YOUR_PASSWORD${NC}"
    exit 1
fi

PASSWORD="$1"
API_URL="http://localhost:8080/api/applications"
AUTH_USER="dev"   # change if different

echo -e "${GREEN}Sending demo requests to ${API_URL}${NC}"
echo -e "Auth: ${CYAN}-u ${AUTH_USER}:$PASSWORD${NC}\n"

run_curl() {
    local cmd="$1"
    local full_cmd="$cmd -v --silent --show-error"

    echo -e "${YELLOW}Running:${NC} $cmd"
    echo -e "${CYAN}Response:${NC}"

    eval "$full_cmd" 2>&1 \
        | sed '/^\* /d; /^> /d; /^< /d' \
        | jq . 2>/dev/null || eval "$full_cmd" -v

    echo ""
}

echo "1. GET /api/applications"
run_curl "curl -u $AUTH_USER:$PASSWORD $API_URL"

echo "2. POST new application"
BODY='{
    "vehicleType": "Semi Truck",
    "buyerName": "Gilberto Test",
    "amount": 85000.00,
    "termMonths": 48,
    "status": "DRAFT"
}'
run_curl "curl -X POST -u $AUTH_USER:$PASSWORD $API_URL -H 'Content-Type: application/json' -d '$BODY'"

echo "3. GET /api/applications (after POST)"
run_curl "curl -u $AUTH_USER:$PASSWORD $API_URL"

echo -e "${GREEN}Done!${NC}"
echo "If POST fails with 400 → JSON body may be malformed (try Postman)."
echo "If 401 → verify SecurityConfig and restart backend."