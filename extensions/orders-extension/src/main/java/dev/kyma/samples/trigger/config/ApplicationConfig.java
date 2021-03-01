package dev.kyma.samples.trigger.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfig {

    @Value("${gateway.url}")
    private String gatewayUrl;

    @Value("${base.site}")
    private String baseSite;

    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public void setGatewayUrl(String gatewayUrl) {
        this.gatewayUrl = gatewayUrl;
    }

    public String getBaseSite() {
        return baseSite;
    }
}
