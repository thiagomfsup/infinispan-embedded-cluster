# infinispan-embedded-cluster
An example on how to create a clustered app using Infinispan (embedded CacheManager) as distributed in-memory key/value data store.

## How does it work?
The app exposes a `\hello` REST endpoint.

When `GET \hello` is called, it just responses a cached message.

Calling `POST \hello` allows a message to be set and, thus, cached. The following `curl` command illustrates how to call this HTTP method:
```
curl -H "Content-type: application/json" -d "Hello to cache!" localhost:8080/hello
```

If none distributed in-memory key/value data store was used, problems would emerge when this app was scaled up. When this app was scaled up (imagine that a load balancer was properly configurated and that node1 and node2 were two distinct app running instance), cached message wouldn't be shared between each app node, what means that calling the `GET \hello` endpoint after set a message throughout `POST \hello` endpoint, would response an unexpected message since node1 had received `POST \hello` command and node2 received the `GET \hello`. 

In this example, Infinispan was used as distributed in-memory key/value data store. It was configurated to work in clustered mode. In this mode, Infinispan uses JGroups for network communications.
