Example usage:
```java
final SeaweedfsClient client = new SeaweedfsClient("http://localhost:9333");
this.client.assign().thenAccept((assignResult) -> {
    ...
});
```
