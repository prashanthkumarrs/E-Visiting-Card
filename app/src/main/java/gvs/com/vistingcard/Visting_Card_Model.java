package gvs.com.vistingcard;

public class Visting_Card_Model {
    String id;
    String cname;
    String cemail;
    String cphone;
    String cwebaddress;
    String caddress;
    String choldername;
    String cposition;
    String cname_choldrename;
    String upby;
    String backgroundimage;
    String logoimage;


    public Visting_Card_Model() {
    }

    public Visting_Card_Model(String id, String cname, String cemail, String cphone, String cwebaddress, String caddress, String choldername, String cposition, String cname_choldrename, String upby,  String logoimage) {
        this.id = id;
        this.cname = cname;
        this.cemail = cemail;
        this.cphone = cphone;
        this.cwebaddress = cwebaddress;
        this.caddress = caddress;
        this.choldername = choldername;
        this.cposition = cposition;
        this.cname_choldrename = cname_choldrename;
        this.upby = upby;
        this.backgroundimage = backgroundimage;
        this.logoimage = logoimage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCemail() {
        return cemail;
    }

    public void setCemail(String cemail) {
        this.cemail = cemail;
    }

    public String getCphone() {
        return cphone;
    }

    public void setCphone(String cphone) {
        this.cphone = cphone;
    }

    public String getCwebaddress() {
        return cwebaddress;
    }

    public void setCwebaddress(String cwebaddress) {
        this.cwebaddress = cwebaddress;
    }

    public String getCaddress() {
        return caddress;
    }

    public void setCaddress(String caddress) {
        this.caddress = caddress;
    }

    public String getCholdername() {
        return choldername;
    }

    public void setCholdername(String choldername) {
        this.choldername = choldername;
    }

    public String getCposition() {
        return cposition;
    }

    public void setCposition(String cposition) {
        this.cposition = cposition;
    }

    public String getCname_choldrename() {
        return cname_choldrename;
    }

    public void setCname_choldrename(String cname_choldrename) {
        this.cname_choldrename = cname_choldrename;
    }

    public String getUpby() {
        return upby;
    }

    public void setUpby(String upby) {
        this.upby = upby;
    }

    public String getBackgroundimage() {
        return backgroundimage;
    }

    public void setBackgroundimage(String backgroundimage) {
        this.backgroundimage = backgroundimage;
    }

    public String getLogoimage() {
        return logoimage;
    }

    public void setLogoimage(String logoimage) {
        this.logoimage = logoimage;
    }
}
