package cst.smart.tnsrlm.farmerdetails;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by admin on 28-
 * 11-2018.
 */

public class Farmer implements Serializable {

    public String id;
    public String imgUrl;
    public String farmerName;
    public String husbandFatherName;
    public String contact;
    public String geoTag;
    public String address;
    public String pincode;
    public String communityType;
    public String typeOfFarming;
    public String landType;
    public String deptStatus;
    public String bankName;
    public String branch;
    public String accountNumber;
    public String accountStartDate;
    public String ifscCode;

    public ArrayList<Farm> farms;

    public Farmer(String id, String imgUrl, String farmerName, String husbandFatherName, String contact,
                  String geoTag, String address, String pincode, String communityType, String typeOfFarming, String landType, String deptStatus, ArrayList<Farm> farms, String bankName, String branch, String accountNumber, String accountStartDate, String ifscCode) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.farmerName = farmerName;
        this.husbandFatherName = husbandFatherName;
        this.contact = contact;
        this.geoTag = geoTag;
        this.address = address;
        this.pincode = pincode;
        this.communityType = communityType;
        this.typeOfFarming = typeOfFarming;
        this.landType = landType;
        this.deptStatus = deptStatus;
        this.farms = farms;
        this.bankName = bankName;
        this.branch = branch;
        this.accountNumber = accountNumber;
        this.accountStartDate = accountStartDate;
        this.ifscCode = ifscCode;
    }

    public Farmer(String imgUrl, String farmerName, String husbandFatherName, String contact, String geoTag, String address, String pincode, String communityType, String typeOfFarming, String landType, String deptStatus, ArrayList<Farm> farms, String bankName, String branch, String accountNumber, String accountStartDate, String ifscCode) {
        this.imgUrl = imgUrl;
        this.farmerName = farmerName;
        this.husbandFatherName = husbandFatherName;
        this.contact = contact;
        this.geoTag = geoTag;
        this.address = address;
        this.pincode = pincode;
        this.communityType = communityType;
        this.typeOfFarming = typeOfFarming;
        this.landType = landType;
        this.deptStatus = deptStatus;
        this.farms = farms;
        this.bankName = bankName;
        this.branch = branch;
        this.accountNumber = accountNumber;
        this.accountStartDate = accountStartDate;
        this.ifscCode = ifscCode;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public String getHusbandFatherName() {
        return husbandFatherName;
    }

    public void setHusbandFatherName(String husbandFatherName) {
        this.husbandFatherName = husbandFatherName;
    }

    public String getGeoTag() {
        return geoTag;
    }

    public void setGeoTag(String geoTag) {
        this.geoTag = geoTag;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCommunityType() {
        return communityType;
    }

    public void setCommunityType(String communityType) {
        this.communityType = communityType;
    }

    public String getTypeOfFarming() {
        return typeOfFarming;
    }

    public void setTypeOfFarming(String typeOfFarming) {
        this.typeOfFarming = typeOfFarming;
    }

    public String getLandType() {
        return landType;
    }

    public void setLandType(String landType) {
        this.landType = landType;
    }

    public String getDeptStatus() {
        return deptStatus;
    }

    public void setDeptStatus(String deptStatus) {
        this.deptStatus = deptStatus;
    }

    public ArrayList<Farm> getFarms() {
        return farms;
    }

    public void setFarms(ArrayList<Farm> farms) {
        this.farms = farms;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountStartDate() {
        return accountStartDate;
    }

    public void setAccountStartDate(String accountStartDate) {
        this.accountStartDate = accountStartDate;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }
}
