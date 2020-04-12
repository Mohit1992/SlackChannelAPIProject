package pojos.testdataPojo;

public class ChannelDetails {

    private String channelName;
    private String channelCreationStatus;
    private String error;
    private String errorDetail;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelCreationStatus() {
        return channelCreationStatus;
    }

    public void setChannelCreationStatus(String channelCreationStatus) {
        this.channelCreationStatus = channelCreationStatus;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }
}
