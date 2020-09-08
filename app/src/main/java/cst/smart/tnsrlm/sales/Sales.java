package cst.smart.tnsrlm.sales;

import java.io.Serializable;

/**
 * Created by admin on 29-11-2018.
 */

public class Sales implements Serializable {
    String id;
    String date;
    String buyerName;
    String commodity;
    String pricePerKg;
    String purchasedQuantity;
    String totalAmount;
    String bankTransferDate;
    String soldBy;
    String warehouseReceiptNumber;

    public Sales(String id, String date, String buyerName, String commodity, String pricePerKg, String purchasedQuantity, String totalAmount, String bankTransferDate, String soldBy, String warehouseReceiptNumber) {
        this.id = id;
        this.date = date;
        this.buyerName = buyerName;
        this.commodity = commodity;
        this.pricePerKg = pricePerKg;
        this.purchasedQuantity = purchasedQuantity;
        this.totalAmount = totalAmount;
        this.bankTransferDate = bankTransferDate;
        this.soldBy = soldBy;
        this.warehouseReceiptNumber = warehouseReceiptNumber;
    }

    public Sales(String date, String buyerName, String commodity, String pricePerKg, String purchasedQuantity, String totalAmount, String bankTransferDate, String soldBy, String warehouseReceiptNumber) {
        this.date = date;
        this.buyerName = buyerName;
        this.commodity = commodity;
        this.pricePerKg = pricePerKg;
        this.purchasedQuantity = purchasedQuantity;
        this.totalAmount = totalAmount;
        this.bankTransferDate = bankTransferDate;
        this.soldBy = soldBy;
        this.warehouseReceiptNumber = warehouseReceiptNumber;
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

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getPricePerKg() {
        return pricePerKg;
    }

    public void setPricePerKg(String pricePerKg) {
        this.pricePerKg = pricePerKg;
    }

    public String getPurchasedQuantity() {
        return purchasedQuantity;
    }

    public void setPurchasedQuantity(String purchasedQuantity) {
        this.purchasedQuantity = purchasedQuantity;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getBankTransferDate() {
        return bankTransferDate;
    }

    public void setBankTransferDate(String bankTransferDate) {
        this.bankTransferDate = bankTransferDate;
    }

    public String getSoldBy() {
        return soldBy;
    }

    public void setSoldBy(String soldBy) {
        this.soldBy = soldBy;
    }

    public String getWarehouseReceiptNumber() {
        return warehouseReceiptNumber;
    }

    public void setWarehouseReceiptNumber(String warehouseReceiptNumber) {
        this.warehouseReceiptNumber = warehouseReceiptNumber;
    }
}
