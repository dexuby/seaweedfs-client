Example usage:
```java
final SeaweedfsClient client = new SeaweedfsClient("http://localhost:9333");
this.client.assign().thenAccept((assignResult) -> {
    ...
});
```

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
	<url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.dexuby</groupId>
    <artifactId>seaweedfs-client</artifactId>
    <version>...</version>
</dependency>
```
