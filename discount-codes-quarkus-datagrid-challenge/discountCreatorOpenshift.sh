#!/bin/sh
set -e

curl -H "Content-Type: application/json" http://quarkus-datagrid-challenge-user2-challenge6.apps.cluster-9vs5p.9vs5p.sandbox1530.opentlc.com/discounts -d '{ "name": "PROMO12", "amount": 20, "enterprise": "ALBACETEBANK", "type": "VALUE"}'
curl -H "Content-Type: application/json" http://quarkus-datagrid-challenge-user2-challenge6.apps.cluster-9vs5p.9vs5p.sandbox1530.opentlc.com/discounts -d '{ "name": "PROMO35", "amount": 10, "enterprise": "MERCADONO", "type": "PERCENT" }'
curl http://quarkus-datagrid-challenge-user2-challenge6.apps.cluster-9vs5p.9vs5p.sandbox1530.opentlc.com/discounts/consume/PROMO12
echo ""
curl http://quarkus-datagrid-challenge-user2-challenge6.apps.cluster-9vs5p.9vs5p.sandbox1530.opentlc.com/discounts/consume/PROMO35
echo ""
curl http://quarkus-datagrid-challenge-user2-challenge6.apps.cluster-9vs5p.9vs5p.sandbox1530.opentlc.com/discounts/VALUE
echo ""
curl http://quarkus-datagrid-challenge-user2-challenge6.apps.cluster-9vs5p.9vs5p.sandbox1530.opentlc.com/discounts/PERCENT
echo ""