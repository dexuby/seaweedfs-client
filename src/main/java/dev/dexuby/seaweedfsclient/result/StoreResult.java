package dev.dexuby.seaweedfsclient.result;

import com.google.gson.annotations.SerializedName;

public class StoreResult {

    private String name;
    private int size;
    @SerializedName("eTag")
    private String tag;

    public String getName() {

        return this.name;

    }

    public int getSize() {

        return this.size;

    }

    public String getTag() {

        return this.tag;

    }

}
