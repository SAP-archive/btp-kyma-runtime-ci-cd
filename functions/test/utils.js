const httpMocks = require('node-mocks-http');

module.exports = {
    orderDetails: {
        "totalPrice": {
            "currencyIso": "EUR",
            "formattedValue": "100",
            "maxQuantity": 1,
            "minQuantity": 1,
            "priceType": "BUY",
            "value": 100
        }
    },
    createEvent: function (orderCode) {
        return {
            data: {
                orderCode: orderCode
            },
            extensions: {
                response: httpMocks.createResponse()
            }
        }
    },
    createGetOrdersReq: function (orderCode) {
        return {
            extensions: {
                request: {
                    url: `https://my.orders.url/${orderCode}`
                },
                response: httpMocks.createResponse()
            }
        }
    }
}