package USER;

public class User_Profile_List {
    int id;
    String org_name, location, org_logo_name, comp_name, comp_date, comp_fees, comp_banner_name, comp_details, comp_code;


    public User_Profile_List(int id, String org_name, String location, String org_logo_name, String comp_name, String comp_date, String comp_fees, String comp_banner_name, String comp_details, String comp_code) {
        this.id = id;
        this.org_name = org_name;
        this.location = location;
        this.org_logo_name = org_logo_name;
        this.comp_code = comp_code;
        this.comp_name = comp_name;
        this.comp_date = comp_date;
        this.comp_fees = comp_fees;
        this.comp_banner_name = comp_banner_name;
        this.comp_details = comp_details;
    }

    public String getComp_code() {
        return comp_code;
    }

    public void setComp_code(String comp_code) {
        this.comp_code = comp_code;
    }

    public String getComp_details() {
        return comp_details;
    }

    public void setComp_details(String comp_details) {
        this.comp_details = comp_details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOrg_logo_name() {
        return org_logo_name;
    }

    public void setOrg_logo_name(String org_logo_name) {
        this.org_logo_name = org_logo_name;
    }

    public String getComp_name() {
        return comp_name;
    }

    public void setComp_name(String comp_name) {
        this.comp_name = comp_name;
    }

    public String getComp_date() {
        return comp_date;
    }

    public void setComp_date(String comp_date) {
        this.comp_date = comp_date;
    }

    public String getComp_fees() {
        return comp_fees;
    }

    public void setComp_fees(String comp_fees) {
        this.comp_fees = comp_fees;
    }

    public String getComp_banner_name() {
        return comp_banner_name;
    }

    public void setComp_banner_name(String comp_banner_name) {
        this.comp_banner_name = comp_banner_name;
    }


}
