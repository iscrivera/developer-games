#!/bin/sh
set -e

curl -H "Content-Type: application/json" http://localhost:9090/discounts -d '{ "name": "PROMO12", "amount": 20, "enterprise": "ALBACETEBANK", "type": "VALUE"}'
curl -H "Content-Type: application/json" http://localhost:9090/discounts -d '{ "name": "PROMO35", "amount": 10, "enterprise": "MERCADONO", "type": "PERCENT" }'
curl http://localhost:9090/discounts/consume/PROMO12
echo ""
curl http://localhost:9090/discounts/consume/PROMO35
echo ""
curl http://localhost:9090/discounts/VALUE
echo ""
curl http://localhost:9090/discounts/PERCENT
echo ""