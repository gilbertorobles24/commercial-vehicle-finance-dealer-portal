"""
send_api_requests.py
Sends demo requests to the backend API using Bearer token authentication.
"""

import sys
import json
import requests

# Configuration
API_URL = "http://localhost:8080/api/applications"

# Paste a valid JWT here (generate from jwt.io or your /login endpoint later)
# This is a mock token signed with secret "secret" containing dealer_id: 1
MOCK_BEARER_TOKEN = (
    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9."
    "eyJzdWIiOiJnaWxiZXJ0bzEyMyIsImRlYWxlcl9pZCI6MSwicm9sZXMiOlsiREVBTEVSIl0sImlhdCI6MTczOTMxMDAwMCwiZXhwIjo0NzM5MzE3MjAwfQ."
    "dmy_example_signature_replace_with_real_one_from_jwt_io"
)

def print_header(text, color="\033[1;33m"):  # Yellow
    print(f"{color}{text}\033[0m")

def print_subheader(text, color="\033[0;36m"):  # Cyan
    print(f"{color}{text}\033[0m")

def send_request(method, url, token=None, headers=None, json_data=None):
    print_header(f"Running: {method} {url}")
    print_subheader("Response:")

    final_headers = headers or {}
    if token:
        final_headers["Authorization"] = f"Bearer {token}"

    try:
        response = requests.request(
            method,
            url,
            headers=final_headers,
            json=json_data,
            timeout=10
        )

        print(f"HTTP/1.1 {response.status_code} {response.reason}")
        for k, v in response.headers.items():
            print(f"{k}: {v}")

        print()  # blank line

        try:
            data = response.json()
            print(json.dumps(data, indent=2))
        except json.JSONDecodeError:
            print(response.text or "(empty body)")

    except requests.exceptions.RequestException as e:
        print(f"\033[0;31mError: {e}\033[0m")

    print("-" * 60)


def main():
    if len(sys.argv) != 1:
        print("\033[0;31mUsage: python send_api_requests.py\033[0m")
        sys.exit(1)

    print("\033[0;32mSending demo requests to", API_URL)
    print("Using Bearer token authentication\033[0m\n")

    token = MOCK_BEARER_TOKEN  # change this when you have a real one

    # 1. GET all applications
    print_header("1. GET /api/applications")
    send_request("GET", API_URL, token=token)

    # 2. POST new application
    print_header("2. POST new application")
    payload = {
        "vehicleType": "Semi Truck",
        "buyerName": "Gilberto Test",
        "amount": 85000.00,
        "termMonths": 48
        # Note: status is set server-side to "DRAFT" — don't send it
    }
    headers = {"Content-Type": "application/json"}
    send_request("POST", API_URL, token=token, headers=headers, json_data=payload)

    # 3. GET again (should show new item)
    print_header("3. GET /api/applications (after POST)")
    send_request("GET", API_URL, token=token)

    print("\033[0;32mDone!\033[0m")
    print("Success = 200/201 + real JSON body.")
    print("If 401 → check token validity or SecurityConfig.")
    print("Tip: Use Postman for easier token testing.")


if __name__ == "__main__":
    main()