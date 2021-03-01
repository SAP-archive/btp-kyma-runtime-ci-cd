const { afterEach } = require('mocha');
const redisMock = require("redis-mock");
//TODO: Use the mock redis instead of Map
const mockRedisClient = redisMock.createClient();
const fakeRedis = new Map();
const nock = require('nock');
const baseSite = process.env['BASE_SITE'];
const rewiremock = require('rewiremock/node');
const utils = require('./utils');
const { expect } = require('chai');

const redisClientStub = {
    set: function (key, value) {
        fakeRedis.set(key, value);
    },
    get: function (key, callback) {
        const value = fakeRedis.get(key);
        callback(null, value);
    }
}
const overrides = {
    redis: {
        createClient() {
            return redisClientStub;
        }
    }
};

const triggerOrderCreated = rewiremock.proxy('../order-created/handler', overrides);
const fnGetOrders = rewiremock.proxy('../get-orders/handler', overrides);

const orderCodeOrderExists = 'orderExists';
const dummyURL = "https://any.url";

describe("order-created Trigger", () => {
    afterEach(nock.cleanAll);
    after(nock.restore);

    it("saves order data", async () => {
        mockServer()
            .get(`/${baseSite}/orders/${orderCodeOrderExists}`)
            .reply(200, utils.orderDetails);
        const event = utils.createEvent(orderCodeOrderExists);
        await triggerOrderCreated.main(event, {});
        expect(JSON.parse(fakeRedis.get(orderCodeOrderExists))).to.be.eql(utils.orderDetails);
        expect(event.extensions.response.statusCode).to.be.eql(200);
    });

    it('fails when oder data is not found', async () => {
        const orderCode = 'orderDoesNotExist';
        mockServer()
            .get(`/${baseSite}/orders/${orderCode}`)
            .reply(404);

        const event = utils.createEvent(orderCode);
        await triggerOrderCreated.main(event, {})
        expect(event.extensions.response.statusCode).to.be.eql(500);
    })

});

describe("get-orders", () => {
    it("gets order data for a valid order", async () => {
        const order = await fnGetOrders.main(utils.createGetOrdersReq(orderCodeOrderExists), {});
        expect(order).to.be.not.null;
        expect(order).to.be.not.undefined;
        expect(order).to.be.eqls(utils.orderDetails);
    });

    it("returns 404 when order does not exists", async () => {
        const event = utils.createGetOrdersReq('invalid');
        const order = await fnGetOrders.main(event,{})
        expect(event.extensions.response.statusCode).to.be.eql(404);
        expect(order).to.be.undefined;
    })
})

function mockServer() {
    return nock(dummyURL, {
        filteringScope: function (_scope) {
            return true;
        }
    });
}
