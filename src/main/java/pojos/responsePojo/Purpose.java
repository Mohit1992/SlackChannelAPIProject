package pojos.responsePojo;

public class Purpose {
    private String last_set;

    private String creator;

    private String value;

    public String getLast_set() {
        return last_set;
    }

    public void setLast_set(String last_set) {
        this.last_set = last_set;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ClassPojo [last_set = " + last_set + ", creator = " + creator + ", value = " + value + "]";
    }
}