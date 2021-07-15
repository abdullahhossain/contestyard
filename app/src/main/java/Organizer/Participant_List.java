package Organizer;

public class Participant_List {
    int id;
    String comp_name, comp_image, comp_code, comp_status, un_number;

    public Participant_List(int id, String comp_name, String comp_image, String comp_code, String comp_status, String un_number) {
        this.id = id;
        this.comp_name = comp_name;
        this.comp_image = comp_image;
        this.comp_code = comp_code;
        this.comp_status = comp_status;
        this.un_number = un_number;
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
