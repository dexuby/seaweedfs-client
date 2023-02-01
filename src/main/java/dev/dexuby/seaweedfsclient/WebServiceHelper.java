package dev.dexuby.seaweedfsclient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class WebServiceHelper {

    private static class WebServiceHelperSingleton {

        private static final WebServiceHelper INSTANCE = new WebServiceHelper();

    }

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private WebServiceHelper() {

    }

    public <T> CompletableFuture<T> submit(final Consumer<CompletableFuture<T>> consumer) {

        final CompletableFuture<T> future = new CompletableFuture<>();
        executorService.submit(() -> consumer.accept(future));

        return future;

    }

    public static WebServiceHelper getInstance() {

        return WebServiceHelperSingleton.INSTANCE;

    }

}
