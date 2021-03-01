const { promisify } = require('util');
const urlUtil = require("url");
var redis = require("redis"),
    redisClient = redis.createClient(process.env.REDIS_PORT, process.env.REDIS_HOST);
const getAsync = promisify(redisClient.get).bind(redisClient);
module.exports = {
    main : async function (event, context) {
        try{
            const orderCode = urlUtil.parse(event.extensions.request.url).pathname.replace(/\/*/, "");
            const orderString = await getAsync(orderCode);
            if (orderString == null || orderString == undefined || orderString === "") {
                event.extensions.response.status(404).send("Order does not exist");
                return
            }else {
                const order = JSON.parse(orderString);
                return order;
            }
        }catch(error) {
            console.log(error);
            event.extensions.response.status(500).send("Error");
        }
    }
}