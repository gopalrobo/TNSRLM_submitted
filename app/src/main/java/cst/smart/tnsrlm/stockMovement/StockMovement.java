package cst.smart.tnsrlm.stockMovement;

import java.io.Serializable;

/**
 * Created by admin on 29-11-2018.
 */

public class StockMovement implements Serializable {
    String id;
    String date;
    String determinationDate;
    String materialAmount;
    String salesVolume;
    String personName;
    String goodsReceived;
    String dateCreditedAccount;
    String finalBalanceAccount;
    String employeeReserve;
    String contact;

    public StockMovement(String id, String date, String determinationDate, String materialAmount, String salesVolume, String personName, String goodsReceived, String dateCreditedAccount, String finalBalanceAccount, String employeeReserve, String contact) {
        this.id = id;
        this.date = date;
        this.determinationDate = determinationDate;
        this.materialAmount = materialAmount;
        this.salesVolume = salesVolume;
        this.personName = personName;
        this.goodsReceived = goodsReceived;
        this.dateCreditedAccount = dateCreditedAccount;
        this.finalBalanceAccount = finalBalanceAccount;
        this.employeeReserve = employeeReserve;
        this.contact = contact;
    }

    public StockMovement(String date, String determinationDate, String materialAmount, String salesVolume, String personName, String goodsReceived, String dateCreditedAccount, String finalBalanceAccount, String employeeReserve, String contact) {
        this.date = date;
        this.determinationDate = determinationDate;
        this.materialAmount = materialAmount;
        this.salesVolume = salesVolume;
        this.personName = personName;
        this.goodsReceived = goodsReceived;
        this.dateCreditedAccount = dateCreditedAccount;
        this.finalBalanceAccount = finalBalanceAccount;
        this.employeeReserve = employeeReserve;
        this.contact = contact;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDeterminationDate() {
        return determinationDate;
    }

    public void setDeterminationDate(String determinationDate) {
        this.determinationDate = determinationDate;
    }

    public String getMaterialAmount() {
        return materialAmount;
    }

    public void setMaterialAmount(String materialAmount) {
        this.materialAmount = materialAmount;
    }

    public String getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(String salesVolume) {
        this.salesVolume = salesVolume;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getGoodsReceived() {
        return goodsReceived;
    }

    public void setGoodsReceived(String goodsReceived) {
        this.goodsReceived = goodsReceived;
    }

    public String getDateCreditedAccount() {
        return dateCreditedAccount;
    }

    public void setDateCreditedAccount(String dateCreditedAccount) {
        this.dateCreditedAccount = dateCreditedAccount;
    }

    public String getFinalBalanceAccount() {
        return finalBalanceAccount;
    }

    public void setFinalBalanceAccount(String finalBalanceAccount) {
        this.finalBalanceAccount = finalBalanceAccount;
    }

    public String getEmployeeReserve() {
        return employeeReserve;
    }

    public void setEmployeeReserve(String employeeReserve) {
        this.employeeReserve = employeeReserve;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
