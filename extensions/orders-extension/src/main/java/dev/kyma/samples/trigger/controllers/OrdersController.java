package dev.kyma.samples.trigger.controllers;

import dev.kyma.samples.trigger.model.OrderCreated;
import dev.kyma.samples.trigger.model.OrderDetails;
import dev.kyma.samples.trigger.services.CommerceService;
import io.cloudevents.CloudEvent;
import io.cloudevents.v1.AttributesImpl;
import io.cloudevents.v1.http.Unmarshallers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping(path = "/")
public class OrdersController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrdersController.class);
    private final CommerceService service;
    private final Map<String, OrderDetails> map = new ConcurrentHashMap<>();

    @Autowired
    public OrdersController(CommerceService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void eventTrigger(@RequestHeader Map<String, Object> headers, @RequestBody String payload) {
        CloudEvent<AttributesImpl, OrderCreated> cloudEvent =
                Unmarshallers.binary(OrderCreated.class)
                        .withHeaders(() -> headers)
                        .withPayload(() -> payload)
                        .unmarshal();

        LOGGER.info("Cloud event attributes {}", cloudEvent.getAttributes());
        LOGGER.info("Event data {}", cloudEvent.getData());
        //implement your business extension logic here

        cloudEvent.getData().ifPresent(data -> {
            String orderCode = data.getOrderCode();
            Optional<OrderDetails> orderDetails = this.service.retrieveOrder(orderCode);
            LOGGER.info("Order details {}", orderDetails);
            orderDetails.ifPresent(value -> map.put(orderCode, value));
        });
    }

    @GetMapping
    public Map<String, OrderDetails> getOrders(){
        return this.map;
    }
}
