package dev.dexuby.seaweedfsclient;

import dev.dexuby.seaweedfsclient.result.AssignResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientTests {

    private final SeaweedfsClient client;

    public ClientTests() {

        this.client = new SeaweedfsClient("http://localhost:9333");

    }

    @Test
    public void testAssign() {

        final AssignResult assignResult = this.client.assign().join();
        assertNotNull(assignResult);
        assertTrue(assignResult.getFid().length() > 6);
        assertNotNull(assignResult.getUrl());
        assertNotNull(assignResult.getPublicUrl());

    }

}
