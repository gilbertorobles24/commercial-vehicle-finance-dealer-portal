import sys
import json
import requests
from requests.auth import HTTPBasicAuth

# Configuration
API_URL = "http://localhost:8080/api/applications"
USERNAME = "dev"  # change if different in SecurityConfig

def print_header(text, color="\033[1;33m"):  # Yellow
    print(f"{color}{text}\033[0m")

def print_subheader(text, color="\033[0;36m"):  # Cyan
    print(f"{color}{text}\033[0m")

def send_request(method, url, auth, headers=None, json_data=None):
    print_header(f"Running: {method} {url}")
    print_subheader("Response:")

    try:
        response = requests.request(
            method,
            url,
            auth=auth,
            headers=headers,
            json=json_data,
            timeout=10
        )

        # Print status and headers
        print(f"HTTP/1.1 {response.status_code} {response.reason}")
        for k, v in response.headers.items():
            print(f"{k}: {v}")

        print()  # blank line

        # Try to pretty-print JSON
        try:
            data = response.json()
            print(json.dumps(data, indent=2))
        except json.JSONDecodeError:
            print(response.text or "(empty body)")

    except requests.exceptions.RequestException as e:
        print(f"\033[0;31mError: {e}\033[0m")

    print("-" * 60)


def main():
    if len(sys.argv) != 2:
        print("\033[0;31mError: Missing password argument.\033[0m")
        print("Usage: python send_api_requests.py YOUR_PASSWORD")
        print("Example: python send_api_requests.py dev")
        sys.exit(1)

    password = sys.argv[1]
    auth = HTTPBasicAuth(USERNAME, password)

    print("\033[0;32mSending demo requests to", API_URL)
    print("Auth: -u", USERNAME + ":" + password + "\033[0m\n")

    # 1. GET all applications
    print_header("1. GET /api/applications")
    send_request("GET", API_URL, auth)

    # 2. POST new application
    print_header("2. POST new application")
    payload = {
        "vehicleType": "Semi Truck",
        "buyerName": "Gilberto Test",
        "amount": 85000.00,
        "termMonths": 48,
        "status": "DRAFT"
    }
    headers = {"Content-Type": "application/json"}
    send_request("POST", API_URL, auth, headers=headers, json_data=payload)

    # 3. GET again (should show new item)
    print_header("3. GET /api/applications (after POST)")
    send_request("GET", API_URL, auth)

    print("\033[0;32mDone!\033[0m")
    print("Success = 200/201 + real JSON body.")
    print("If 401 → verify SecurityConfig and restart backend.")
    print("Tip: Use Postman for interactive testing.")


if __name__ == "__main__":
    main()