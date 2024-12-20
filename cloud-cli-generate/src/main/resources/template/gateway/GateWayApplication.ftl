package  ${packageName}.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.discovery.GatewayDiscoveryClientAutoConfiguration;

@Slf4j
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "${packageName}.gateway", exclude = GatewayDiscoveryClientAutoConfiguration.class)
public class GateWayApplication {
        public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);
        log.info("Gateway started successfully.");
    }
}