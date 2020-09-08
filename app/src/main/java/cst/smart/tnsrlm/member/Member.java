package cst.smart.tnsrlm.member;

public class Member {
    public String id;
    public String name;
    public String initials;
    public String contact;
    public String whatsapp;
    public String geotag;
    public String address;
    public String pincode;
    public String gmail;
    public String designation;
    public String commodity;
    public String accessLevel;
    public String district;
    public String block;
    public String village;
    public String password;
    public String image;

    public Member() {
    }

    public Member(String name, String initials, String contact, String whatsapp, String geotag, String address, String pincode, String gmail, String designation, String commodity, String accessLevel, String district, String block, String village, String password, String image) {
        this.name = name;
        this.initials = initials;
        this.contact = contact;
        this.whatsapp = whatsapp;
        this.geotag = geotag;
        this.address = address;
        this.pincode = pincode;
        this.gmail = gmail;
        this.designation = designation;
        this.commodity = commodity;
        this.accessLevel = accessLevel;
        this.district = district;
        this.block = block;
        this.village = village;
        this.password = password;
        this.image = image;
    }

    public Member(String id, String name, String initials, String contact, String whatsapp, String geotag, String address, String pincode, String gmail, String designation, String commodity, String accessLevel, String district, String block, String village, String password, String image) {
        this.id = id;
        this.name = name;
        this.initials = initials;
        this.contact = contact;
        this.whatsapp = whatsapp;
        this.geotag = geotag;
        this.address = address;
        this.pincode = pincode;
        this.gmail = gmail;
        this.designation = designation;
        this.commodity = commodity;
        this.accessLevel = accessLevel;
        this.district = district;
        this.block = block;
        this.village = village;
        this.password = password;
        this.image = image;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getGeotag() {
        return geotag;
    }

    public void setGeotag(String geotag) {
        this.geotag = geotag;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
