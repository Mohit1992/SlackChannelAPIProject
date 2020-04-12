package pojos.responsePojo;

public class Response_metadata {
    private String[] warnings;

    public String[] getWarnings() {
        return warnings;
    }

    public void setWarnings(String[] warnings) {
        this.warnings = warnings;
    }

    @Override
    public String toString() {
        return "ClassPojo [warnings = " + warnings + "]";
    }
}