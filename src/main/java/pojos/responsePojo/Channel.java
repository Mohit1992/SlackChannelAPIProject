package pojos.responsePojo;

public class Channel {
    private String is_private;

    private String last_read;

    private String creator;

    private String is_member;

    private String is_mpim;

    private Purpose purpose;

    private String created;

    private String name_normalized;

    private String unread_count_display;

    private String priority;

    private String unlinked;

    private String unread_count;

    private String is_archived;

    private String is_channel;

    private String is_general;

    private String is_shared;

    private String[] members;

    private String name;

    private Topic topic;

    private String id;

    private String[] previous_names;

    private String is_org_shared;


    public String getIs_private() {
        return is_private;
    }

    public void setIs_private(String is_private) {
        this.is_private = is_private;
    }

    public String getLast_read() {
        return last_read;
    }

    public void setLast_read(String last_read) {
        this.last_read = last_read;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getIs_member() {
        return is_member;
    }

    public void setIs_member(String is_member) {
        this.is_member = is_member;
    }

    public String getIs_mpim() {
        return is_mpim;
    }

    public void setIs_mpim(String is_mpim) {
        this.is_mpim = is_mpim;
    }

    public Purpose getPurpose() {
        return purpose;
    }

    public void setPurpose(Purpose purpose) {
        this.purpose = purpose;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getName_normalized() {
        return name_normalized;
    }

    public void setName_normalized(String name_normalized) {
        this.name_normalized = name_normalized;
    }

    public String getUnread_count_display() {
        return unread_count_display;
    }

    public void setUnread_count_display(String unread_count_display) {
        this.unread_count_display = unread_count_display;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getUnlinked() {
        return unlinked;
    }

    public void setUnlinked(String unlinked) {
        this.unlinked = unlinked;
    }

    public String getUnread_count() {
        return unread_count;
    }

    public void setUnread_count(String unread_count) {
        this.unread_count = unread_count;
    }

    public String getIs_archived() {
        return is_archived;
    }

    public void setIs_archived(String is_archived) {
        this.is_archived = is_archived;
    }

    public String getIs_channel() {
        return is_channel;
    }

    public void setIs_channel(String is_channel) {
        this.is_channel = is_channel;
    }

    public String getIs_general() {
        return is_general;
    }

    public void setIs_general(String is_general) {
        this.is_general = is_general;
    }

    public String getIs_shared() {
        return is_shared;
    }

    public void setIs_shared(String is_shared) {
        this.is_shared = is_shared;
    }

    public String[] getMembers() {
        return members;
    }

    public void setMembers(String[] members) {
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getPrevious_names() {
        return previous_names;
    }

    public void setPrevious_names(String[] previous_names) {
        this.previous_names = previous_names;
    }

    public String getIs_org_shared() {
        return is_org_shared;
    }

    public void setIs_org_shared(String is_org_shared) {
        this.is_org_shared = is_org_shared;
    }

   /* public null getLatest ()
    {
        return latest;
    }

    public void setLatest (null latest)
    {
        this.latest = latest;
    }*/

    @Override
    public String toString() {
        return "ClassPojo [is_private = " + is_private + ", last_read = " + last_read + ", creator = " + creator + ", is_member = " + is_member + ", is_mpim = " + is_mpim + ", purpose = " + purpose + ", created = " + created + ", name_normalized = " + name_normalized + ", unread_count_display = " + unread_count_display + ", priority = " + priority + ", unlinked = " + unlinked + ", unread_count = " + unread_count + ", is_archived = " + is_archived + ", is_channel = " + is_channel + ", is_general = " + is_general + ", is_shared = " + is_shared + ", members = " + members + ", name = " + name + ", topic = " + topic + ", id = " + id + ", previous_names = " + previous_names + ", is_org_shared = " + is_org_shared + "]";
    }
}