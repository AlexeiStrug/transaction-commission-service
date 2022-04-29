# Transaction Commission Calculation API

## How to run it

1. Using Maven
    ```
   mvn clean package (from console or from maven toolbar)
   java -jar target/transaction-commission-0.0.1-SNAPSHOT.jar
   ```
2. Using Docker
   ```
   docker build -t transaction-commission:latest .  
   docker run -p 8080:8080 transaction-commission:latest
   ```

## How to use it

* After starting application is running on the port **8080**.
* Endpoint address is **http://localhost:8080/api/v1/transactions/commissions** **POST** request
* Request body example:
    ```
  {
  "date": [2022,4,29],
  "amount": "100.00",
  "currency": "EUR",
  "client_id": 42
  }
  ```
* Endpoint accepted **POST** request

## How long did it take

Total: 3h 35min

- POC logic: 1.5h
- Tests: 1h
- Fix and refactor: 0.5h
- Configure docker image: 15 min
- Documentation: 20 min
