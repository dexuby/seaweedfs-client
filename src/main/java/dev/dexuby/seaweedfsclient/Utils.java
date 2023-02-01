package dev.dexuby.seaweedfsclient;

public final class Utils {

    /**
     * Extracts the volume id from the provided fid.
     *
     * @param fid The fid.
     * @return The extracted volume id or -1 if the extraction failed.
     */

    public static int extractVolumeIdFromFid(String fid) {

        final int lastIndex = fid.lastIndexOf(',');
        if (lastIndex == -1) return -1;

        try {
            return Integer.parseInt(fid.substring(0, lastIndex));
        } catch (final NumberFormatException ex) {
            return -1;
        }

    }

    private Utils() {

    }

}
