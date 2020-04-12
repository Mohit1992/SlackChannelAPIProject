package utils;

public enum EndPointURL {
    CREATE("/channels.create"),
    LEAVE("/channels.leave"),
    JOIN("/channels.join"),
    RENAME("/channels.rename"),
    LIST("/channels.list"),
    ARCHIVE("/channels.archive");

    private String url;

    EndPointURL(String url) {
        this.url = url;
    }

    public String url() {
        return url;
    }

}
