package pojos.responsePojo;

public class SlackChannelPojo {
    private Channel channel;

    private String warning;

    private Response_metadata response_metadata;

    private String ok;

    private String error;

    private String detail;

    private String not_in_channel;

    private String already_in_channel;

    public SlackChannelPojo(String already_in_channel) {
        this.already_in_channel = already_in_channel;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public Response_metadata getResponse_metadata() {
        return response_metadata;
    }

    public void setResponse_metadata(Response_metadata response_metadata) {
        this.response_metadata = response_metadata;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

    @Override
    public String toString() {
        return "ClassPojo [channel = " + channel + ", warning = " + warning + ", response_metadata = " + response_metadata + ", ok = " + ok + "]";
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getNot_in_channel() {
        return not_in_channel;
    }

    public void setNot_in_channel(String not_in_channel) {
        this.not_in_channel = not_in_channel;
    }

    public String getAlready_in_channel() {
        return already_in_channel;
    }

    public void setAlready_in_channel(String already_in_channel) {
        this.already_in_channel = already_in_channel;
    }
}
			
			