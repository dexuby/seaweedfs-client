package dev.dexuby.seaweedfsclient.result;

import dev.dexuby.seaweedfsclient.Location;

import java.util.List;

public class LookupResult {

    private int volumeId;
    private List<Location> locations;

    public int getVolumeId() {

        return this.volumeId;

    }

    public List<Location> getLocations() {

        return this.locations;

    }

    public Location getFirstLocation() {

        assert !this.locations.isEmpty();
        return this.locations.get(0);

    }

}
