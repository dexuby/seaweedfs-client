package dev.dexuby.seaweedfsclient;

import dev.dexuby.seaweedfsclient.result.AssignResult;
import dev.dexuby.seaweedfsclient.result.DeleteResult;
import dev.dexuby.seaweedfsclient.result.LookupResult;
import dev.dexuby.seaweedfsclient.result.StoreResult;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

public class SeaweedfsClient {

    private final String baseUrl;

    public SeaweedfsClient(final String baseUrl) {

        this.baseUrl = baseUrl;

    }

    /**
     * Calls the assign endpoint to obtain a fid and volume server.
     *
     * @return A completable future for the assign result, might contain <code>null</code> if the process fails.
     */

    public CompletableFuture<AssignResult> assign() {

        return WebServiceHelper.getInstance().submit((future) -> {
            try (final CloseableHttpClient httpClient = HttpClients.createDefault()) {
                final HttpGet request = new HttpGet(this.baseUrl + Endpoint.ASSIGN_ENDPOINT);
                final CloseableHttpResponse response = httpClient.execute(request);
                final String responseJson = EntityUtils.toString(response.getEntity());
                response.close();
                future.complete(Constants.GSON.fromJson(responseJson, AssignResult.class));
            } catch (final Exception ex) {
                ex.printStackTrace();
                future.complete(null);
            }
        });

    }

    /**
     * Calls the volume server to store the provided file under the provided fid.
     *
     * @return A completable future for the store result, might contain <code>null</code> if the process fails.
     */

    public CompletableFuture<StoreResult> store(final String url, final String fid, final File file) {

        return WebServiceHelper.getInstance().submit((future) -> {
            try (final CloseableHttpClient httpClient = HttpClients.createDefault()) {
                final HttpPost request = new HttpPost("http://" + url + "/" + fid);
                final HttpEntity requestEntity = MultipartEntityBuilder.create()
                        .addBinaryBody("file", file)
                        .build();
                request.setEntity(requestEntity);
                final CloseableHttpResponse response = httpClient.execute(request);
                final String responseJson = EntityUtils.toString(response.getEntity());
                response.close();
                future.complete(Constants.GSON.fromJson(responseJson, StoreResult.class));
            } catch (final Exception ex) {
                ex.printStackTrace();
                future.complete(null);
            }
        });

    }

    /**
     * Calls the volume server to store the provided file under the provided fid.
     *
     * @return A completable future for the store result, might contain <code>null</code> if the process fails.
     */

    public CompletableFuture<StoreResult> store(final AssignResult assignResult, final File file) {

        return this.store(assignResult.getUrl(), assignResult.getFid(), file);

    }

    /**
     * Calls the lookup endpoint to obtain the possible volume servers.
     *
     * @return A completable future for the lookup result, might contain <code>null</code> if the process fails.
     */

    public CompletableFuture<LookupResult> lookup(final String fid) {

        return WebServiceHelper.getInstance().submit((future) -> {
            try (final CloseableHttpClient httpClient = HttpClients.createDefault()) {
                final int volumeId = Utils.extractVolumeIdFromFid(fid);
                if (volumeId == -1) throw new RuntimeException("Could not extract volume id from fid " + fid);
                final HttpGet request = new HttpGet(this.baseUrl + Endpoint.LOOKUP_ENDPOINT + volumeId);
                final CloseableHttpResponse response = httpClient.execute(request);
                final String responseJson = EntityUtils.toString(response.getEntity());
                response.close();
                future.complete(Constants.GSON.fromJson(responseJson, LookupResult.class));
            } catch (final Exception ex) {
                ex.printStackTrace();
                future.complete(null);
            }
        });

    }

    /**
     * Calls the volume server to read the file under the provided fid into a byte array.
     *
     * @return A completable future for the file byte array, might contain <code>null</code> if the process fails.
     */

    public CompletableFuture<byte[]> read(final String url, final String fid) {

        return WebServiceHelper.getInstance().submit((future) -> {
            try (final CloseableHttpClient httpClient = HttpClients.createDefault()) {
                final HttpGet request = new HttpGet("http://" + url + "/" + fid);
                final CloseableHttpResponse response = httpClient.execute(request);
                final InputStream responseInputStream = response.getEntity().getContent();
                final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int data;
                final byte[] bytes = new byte[(int) response.getEntity().getContentLength()];
                while ((data = responseInputStream.read(bytes, 0, bytes.length)) != -1)
                    buffer.write(bytes, 0, data);
                buffer.close();
                response.close();
                future.complete(buffer.toByteArray());
            } catch (final Exception ex) {
                ex.printStackTrace();
                future.complete(null);
            }
        });

    }

    /**
     * Calls the volume server to delete the file under the provided fid.
     *
     * @return A completable future for the delete result, might contain <code>null</code> if the process fails.
     */

    public CompletableFuture<DeleteResult> delete(final String url, final String fid) {

        return WebServiceHelper.getInstance().submit((future) -> {
            try (final CloseableHttpClient httpClient = HttpClients.createDefault()) {
                final HttpDelete request = new HttpDelete("http://" + url + "/" + fid);
                final CloseableHttpResponse response = httpClient.execute(request);
                final String responseJson = EntityUtils.toString(response.getEntity());
                response.close();
                future.complete(Constants.GSON.fromJson(responseJson, DeleteResult.class));
            } catch (final Exception ex) {
                ex.printStackTrace();
                future.complete(null);
            }
        });

    }

}
