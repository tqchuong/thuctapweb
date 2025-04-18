package fit.hcmuaf.edu.vn.foodmart.model;

public class Activity_log {
    private int id;
    private Integer user_id;
    private String username;
    private String action;
    private String level_log;
    private String ip_address;
    private String time_log;
    private String source_page;
    private String resource;
    private String old_data;
    private String new_data;

    public Activity_log() {
    }

    public Activity_log(int id, Integer user_id, String username, String action, String level_log, String ip_address, String time_log, String source_page, String resource, String old_data, String new_data) {
        this.id = id;
        this.user_id = user_id;
        this.username = username;
        this.action = action;
        this.level_log = level_log;
        this.ip_address = ip_address;
        this.time_log = time_log;
        this.source_page = source_page;
        this.resource = resource;
        this.old_data = old_data;
        this.new_data = new_data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getLevel_log() {
        return level_log;
    }

    public void setLevel_log(String level_log) {
        this.level_log = level_log;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getTime_log() {
        return time_log;
    }

    public void setTime_log(String time_log) {
        this.time_log = time_log;
    }

    public String getSource_page() {
        return source_page;
    }

    public void setSource_page(String source_page) {
        this.source_page = source_page;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getOld_data() {
        return old_data;
    }

    public void setOld_data(String old_data) {
        this.old_data = old_data;
    }

    public String getNew_data() {
        return new_data;
    }

    public void setNew_data(String new_data) {
        this.new_data = new_data;
    }

    @Override
    public String toString() {
        return "Activity_log{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", username='" + username + '\'' +
                ", action='" + action + '\'' +
                ", level_log='" + level_log + '\'' +
                ", ip_address='" + ip_address + '\'' +
                ", time_log='" + time_log + '\'' +
                ", source_page='" + source_page + '\'' +
                ", resource='" + resource + '\'' +
                ", old_data='" + old_data + '\'' +
                ", new_data='" + new_data + '\'' +
                '}';
    }
}
