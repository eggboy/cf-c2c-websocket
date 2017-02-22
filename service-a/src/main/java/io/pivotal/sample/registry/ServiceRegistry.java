package io.pivotal.sample.registry;

/**
 * Created by leec43 on 2/22/17.
 */
public interface ServiceRegistry {
    String getHostByServiceName(String serviceName);
}
