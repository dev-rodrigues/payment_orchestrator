# Orchestrator

See [orchestrator](/architecture/payment--service.drawio.png) for a high-level overview of the payment service.

## Overview
This application is a payment service that allows users to make payments to payments type.

The types of payments are:
- Credit Card
- Slip bank

The request to make a payment is made to the topic `payment-request` and the response is made to the topic `payment-response`.

Inside the payment is made successfully or not-success, the payment is saved in the database for future consultation.