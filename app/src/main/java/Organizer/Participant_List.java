package Organizer;

public class Participant_List {
    int id;
    String comp_name, comp_image, comp_code, comp_status, un_number, username, institution, email;

    public Participant_List(int id, String comp_name, String comp_image, String comp_code, String comp_status, String un_number, String username, String institution, String email) {
        this.id = id;
        this.comp_name = comp_name;
        this.comp_image = comp_image;
        this.comp_code = comp_code;
        this.comp_status = comp_status;
        this.un_number = un_number;
        this.username = username;
        this.institution = institution;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComp_name() {
        return comp_name;
    }

    public void setComp_name(String comp_name) {
        this.comp_name = comp_name;
    }

    public String getComp_image() {
        return comp_image;
    }

    public void setComp_image(String comp_image) {
        this.comp_image = comp_image;
    }

    public String getComp_code() {
        return comp_code;
    }

    public void setComp_code(String comp_code) {
        this.comp_code = comp_code;
    }

    public String getComp_status() {
        return comp_status;
    }

    public void setComp_status(String comp_status) {
        this.comp_status = comp_status;
    }

    public String getUn_number() {
        return un_number;
    }

    public void setUn_number(String un_number) {
        this.un_number = un_number;
    }
}
