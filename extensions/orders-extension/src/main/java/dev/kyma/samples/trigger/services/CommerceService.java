package dev.kyma.samples.trigger.services;

import dev.kyma.samples.trigger.config.ApplicationConfig;
import dev.kyma.samples.trigger.model.OrderDetails;
import dev.kyma.samples.trigger.utils.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommerceService {
    private final HttpClient httpClient;
    private final ApplicationConfig applicationConfig;

    @Autowired
    public CommerceService(HttpClient httpClient, ApplicationConfig applicationConfig) {
        this.httpClient = httpClient;
        this.applicationConfig = applicationConfig;
    }

    public Optional<OrderDetails> retrieveOrder(String orderCode) {
        return this.httpClient.get(this.applicationConfig.getGatewayUrl()
                        + "/"
                        + this.applicationConfig.getBaseSite()
                        + "/orders"
                        + "/"
                        + orderCode,
                OrderDetails.class);
    }
}
