package pojos.responsePojo;

public class ListChannelPojo {
    private Channel[] channels;

    private Response_metadata response_metadata;

    private String ok;


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

    public Channel[] getChannels() {
        return channels;
    }

    public void setChannels(Channel[] channels) {
        this.channels = channels;
    }
}