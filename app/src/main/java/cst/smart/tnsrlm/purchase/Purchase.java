package cst.smart.tnsrlm.purchase;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * Created by admin on 29-11-2018.
 */

public class Purchase implements Serializable {
    String id;
    String date;
    String crops;
    String farmerName;
    String variety;
    String grade;
    String humidity;
    String pricePerKg;
    String purchasedQuantity;
    String totalPrice;
    String itemsRejected;
    String reasonsRejected;

    public Purchase(String id,String date, String crops, String farmerName, String variety,
                    String grade,String humidity, String pricePerKg, String purchasedQuantity, String totalPrice, String itemsRejected, String reasonsRejected) {
        this.id = id;
        this.date=date;
        this.crops = crops;
        this.farmerName = farmerName;
        this.variety = variety;
        this.grade=grade;
        this.humidity = humidity;
        this.pricePerKg = pricePerKg;
        this.purchasedQuantity = purchasedQuantity;
        this.totalPrice = totalPrice;
        this.itemsRejected = itemsRejected;
        this.reasonsRejected = reasonsRejected;
    }

    public Purchase(String date,String crops, String farmerName, String variety,
                    String grade,String humidity, String pricePerKg, String purchasedQuantity, String totalPrice, String itemsRejected, String reasonsRejected) {
      this.date=date;
        this.crops = crops;
        this.farmerName = farmerName;
        this.variety = variety;
        this.humidity = humidity;
        this.pricePerKg = pricePerKg;
        this.grade=grade;
        this.purchasedQuantity = purchasedQuantity;
        this.totalPrice = totalPrice;
        this.itemsRejected = itemsRejected;
        this.reasonsRejected = reasonsRejected;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCrops() {
        return crops;
    }

    public void setCrops(String crops) {
        this.crops = crops;
    }

    public String getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
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

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getItemsRejected() {
        return itemsRejected;
    }

    public void setItemsRejected(String itemsRejected) {
        this.itemsRejected = itemsRejected;
    }

    public String getReasonsRejected() {
        return reasonsRejected;
    }

    public void setReasonsRejected(String reasonsRejected) {
        this.reasonsRejected = reasonsRejected;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public LinkedHashMap<String, String> toMap() {
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("Record No :", id);
        linkedHashMap.put("Date :", date);
        linkedHashMap.put("Crop :", crops);
        linkedHashMap.put("Farmer Name :", farmerName);
        linkedHashMap.put("Variety :", variety);
        linkedHashMap.put("Humidity :", humidity);
        linkedHashMap.put("Price/Kg :", pricePerKg);
        linkedHashMap.put("Purchased Quantity :", purchasedQuantity);
        linkedHashMap.put("Total Price :", totalPrice);
        linkedHashMap.put("Items Rejected :", itemsRejected);
        linkedHashMap.put("Reasons Rejected :", reasonsRejected);
        return linkedHashMap;
    }






}
