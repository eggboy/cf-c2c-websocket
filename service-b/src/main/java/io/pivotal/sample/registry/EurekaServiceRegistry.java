package io.pivotal.sample.registry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by leec43 on 2/22/17.
 */
@RestController
@Service
public class EurekaServiceRegistry implements ServiceRegistry {

    @Autowired
    private DiscoveryClient discoveryClient;

    public String getHostByServiceName(String serviceName) {
        List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances(serviceName);

        for (ServiceInstance serviceInstance : serviceInstanceList) {
            return serviceInstance.getHost();
        }

        return "";
    }

    @GetMapping("/registry/{serviceName}")
    public String serviceHostName(@PathVariable String serviceName) {
        return getHostByServiceName(serviceName);
    }
}
