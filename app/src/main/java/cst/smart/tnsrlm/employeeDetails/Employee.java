package cst.smart.tnsrlm.employeeDetails;

import java.io.Serializable;

/**
 * Created by admin on 29-11-2018.
 */

public class Employee implements Serializable {
    public String id;
    public String imgUrl;
    public String employeeName;
    public String responsibility;
    public String mobileNumber;
    public String educationQualification;
    public String geoTag;
    public String address;
    public String pincode;

    public Employee(String id, String imgUrl, String employeeName, String responsibility, String mobileNumber, String educationQualification, String geoTag, String address, String pincode) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.employeeName = employeeName;
        this.responsibility = responsibility;
        this.mobileNumber = mobileNumber;
        this.educationQualification = educationQualification;
        this.geoTag = geoTag;
        this.address = address;
        this.pincode = pincode;
    }

    public Employee(String imgUrl, String employeeName, String responsibility, String mobileNumber, String educationQualification, String geoTag, String address, String pincode) {
        this.imgUrl = imgUrl;
        this.employeeName = employeeName;
        this.responsibility = responsibility;
        this.mobileNumber = mobileNumber;
        this.educationQualification = educationQualification;
        this.geoTag = geoTag;
        this.address = address;
        this.pincode = pincode;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEducationQualification() {
        return educationQualification;
    }

    public void setEducationQualification(String educationQualification) {
        this.educationQualification = educationQualification;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
