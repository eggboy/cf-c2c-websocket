# cf-c2c-websocket


### Cloud Foundry Container to Container Network

https://content.pivotal.io/blog/building-spring-microservices-with-cloud-foundrys-new-container-networking-stack

### On Pivotal Web Service

```
cf create-service p-service-registry standard testregistry
cf bs serviceA testregistry
cf bs serviceB testregistry
cf allow-access serviceB serviceA --protocol tcp --port 8080
```


