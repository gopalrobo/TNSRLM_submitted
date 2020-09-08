package cst.smart.tnsrlm.stockRegister;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * Created by admin on 29-11-2018.
 */

public class StockRegister implements Serializable {
    public String id;
    public String dateOfStock;
    public String locationOfWarehouse;
    public String stockQuantity;
    public String warehouseReceipt;
    public String monthlyWarehouseRent;
    public String secretaryName;
    public String contact;
    public String treasurerName;
    public String warehouseEmployeeName;


    public StockRegister(String id, String dateOfStock, String locationOfWarehouse, String stockQuantity, String warehouseReceipt, String monthlyWarehouseRent, String secretaryName, String contact, String treasurerName, String warehouseEmployeeName) {
        this.id = id;
        this.dateOfStock = dateOfStock;
        this.locationOfWarehouse = locationOfWarehouse;
        this.stockQuantity = stockQuantity;
        this.warehouseReceipt = warehouseReceipt;
        this.monthlyWarehouseRent = monthlyWarehouseRent;
        this.secretaryName = secretaryName;
        this.contact = contact;
        this.treasurerName = treasurerName;
        this.warehouseEmployeeName = warehouseEmployeeName;
    }

    public StockRegister(String dateOfStock, String locationOfWarehouse, String stockQuantity, String warehouseReceipt, String monthlyWarehouseRent, String secretaryName, String contact, String treasurerName, String warehouseEmployeeName) {
        this.dateOfStock = dateOfStock;
        this.locationOfWarehouse = locationOfWarehouse;
        this.stockQuantity = stockQuantity;
        this.warehouseReceipt = warehouseReceipt;
        this.monthlyWarehouseRent = monthlyWarehouseRent;
        this.secretaryName = secretaryName;
        this.contact = contact;
        this.treasurerName = treasurerName;
        this.warehouseEmployeeName = warehouseEmployeeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateOfStock() {
        return dateOfStock;
    }

    public void setDateOfStock(String dateOfStock) {
        this.dateOfStock = dateOfStock;
    }

    public String getLocationOfWarehouse() {
        return locationOfWarehouse;
    }

    public void setLocationOfWarehouse(String locationOfWarehouse) {
        this.locationOfWarehouse = locationOfWarehouse;
    }

    public String getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(String stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getWarehouseReceipt() {
        return warehouseReceipt;
    }

    public void setWarehouseReceipt(String warehouseReceipt) {
        this.warehouseReceipt = warehouseReceipt;
    }

    public String getMonthlyWarehouseRent() {
        return monthlyWarehouseRent;
    }

    public void setMonthlyWarehouseRent(String monthlyWarehouseRent) {
        this.monthlyWarehouseRent = monthlyWarehouseRent;
    }

    public String getSecretaryName() {
        return secretaryName;
    }

    public void setSecretaryName(String secretaryName) {
        this.secretaryName = secretaryName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTreasurerName() {
        return treasurerName;
    }

    public void setTreasurerName(String treasurerName) {
        this.treasurerName = treasurerName;
    }

    public String getWarehouseEmployeeName() {
        return warehouseEmployeeName;
    }

    public void setWarehouseEmployeeName(String warehouseEmployeeName) {
        this.warehouseEmployeeName = warehouseEmployeeName;
    }

    public LinkedHashMap<String, String> toMap() {
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("Stock No :", id);
        linkedHashMap.put("Date Of Stock :", dateOfStock);
        linkedHashMap.put("Location Of Warehouse :", locationOfWarehouse);
        linkedHashMap.put("Stock Quantity :", stockQuantity);
        linkedHashMap.put("Warehouse Receipt :", warehouseReceipt);
        linkedHashMap.put("Monthly Warehouse Rent :", monthlyWarehouseRent);
        linkedHashMap.put("Secretary Name :", secretaryName);
        linkedHashMap.put("Contact :", contact);
        linkedHashMap.put("Treasurer Name :", treasurerName);
        linkedHashMap.put("Warehouse Employee Name :", warehouseEmployeeName);
        return linkedHashMap;
    }
}
