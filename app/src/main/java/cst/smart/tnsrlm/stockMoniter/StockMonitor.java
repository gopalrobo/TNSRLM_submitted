package cst.smart.tnsrlm.stockMoniter;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * Created by admin on 29-11-2018.
 */

public class StockMonitor implements Serializable{
    String id;
    String supervisedDate;
    String supervisorName;
    String commodity;
    String variety;
    String commodityQualityParameters;
    String moisture;
    String foreignMaterial;
    String color;
    String damaged;
    String firstGradePrice;
    String firstGradeKg;
    String secondGradePrice;
    String secondGradeKg;
    String thirdGradePrice;
    String thirdGradeKg;

    public StockMonitor(String id, String supervisedDate, String supervisorName, String commodity, String variety, String commodityQualityParameters, String moisture, String foreignMaterial, String color, String damaged, String firstGradePrice, String firstGradeKg, String secondGradePrice, String secondGradeKg, String thirdGradePrice, String thirdGradeKg) {
        this.id = id;
        this.supervisedDate = supervisedDate;
        this.supervisorName = supervisorName;
        this.commodity = commodity;
        this.variety = variety;
        this.commodityQualityParameters = commodityQualityParameters;
        this.moisture = moisture;
        this.foreignMaterial = foreignMaterial;
        this.color = color;
        this.damaged = damaged;
        this.firstGradePrice = firstGradePrice;
        this.firstGradeKg = firstGradeKg;
        this.secondGradePrice = secondGradePrice;
        this.secondGradeKg = secondGradeKg;
        this.thirdGradePrice = thirdGradePrice;
        this.thirdGradeKg = thirdGradeKg;
    }

    public StockMonitor(String supervisedDate, String supervisorName, String commodity, String variety, String commodityQualityParameters, String moisture, String foreignMaterial, String color, String damaged, String firstGradePrice, String firstGradeKg, String secondGradePrice, String secondGradeKg, String thirdGradePrice, String thirdGradeKg) {
        this.supervisedDate = supervisedDate;
        this.supervisorName = supervisorName;
        this.commodity = commodity;
        this.variety = variety;
        this.commodityQualityParameters = commodityQualityParameters;
        this.moisture = moisture;
        this.foreignMaterial = foreignMaterial;
        this.color = color;
        this.damaged = damaged;
        this.firstGradePrice = firstGradePrice;
        this.firstGradeKg = firstGradeKg;
        this.secondGradePrice = secondGradePrice;
        this.secondGradeKg = secondGradeKg;
        this.thirdGradePrice = thirdGradePrice;
        this.thirdGradeKg = thirdGradeKg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSupervisedDate() {
        return supervisedDate;
    }

    public void setSupervisedDate(String supervisedDate) {
        this.supervisedDate = supervisedDate;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getCommodityQualityParameters() {
        return commodityQualityParameters;
    }

    public void setCommodityQualityParameters(String commodityQualityParameters) {
        this.commodityQualityParameters = commodityQualityParameters;
    }

    public String getMoisture() {
        return moisture;
    }

    public void setMoisture(String moisture) {
        this.moisture = moisture;
    }

    public String getForeignMaterial() {
        return foreignMaterial;
    }

    public void setForeignMaterial(String foreignMaterial) {
        this.foreignMaterial = foreignMaterial;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDamaged() {
        return damaged;
    }

    public void setDamaged(String damaged) {
        this.damaged = damaged;
    }

    public String getFirstGradePrice() {
        return firstGradePrice;
    }

    public void setFirstGradePrice(String firstGradePrice) {
        this.firstGradePrice = firstGradePrice;
    }

    public String getFirstGradeKg() {
        return firstGradeKg;
    }

    public void setFirstGradeKg(String firstGradeKg) {
        this.firstGradeKg = firstGradeKg;
    }

    public String getSecondGradePrice() {
        return secondGradePrice;
    }

    public void setSecondGradePrice(String secondGradePrice) {
        this.secondGradePrice = secondGradePrice;
    }

    public String getSecondGradeKg() {
        return secondGradeKg;
    }

    public void setSecondGradeKg(String secondGradeKg) {
        this.secondGradeKg = secondGradeKg;
    }

    public String getThirdGradePrice() {
        return thirdGradePrice;
    }

    public void setThirdGradePrice(String thirdGradePrice) {
        this.thirdGradePrice = thirdGradePrice;
    }

    public String getThirdGradeKg() {
        return thirdGradeKg;
    }

    public void setThirdGradeKg(String thirdGradeKg) {
        this.thirdGradeKg = thirdGradeKg;
    }

    public LinkedHashMap<String, String> toMap() {
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("No :", id);
        linkedHashMap.put("Supervised Date :", supervisedDate);
        linkedHashMap.put("Supervisor Name :", supervisorName);
        linkedHashMap.put("Commodity :", commodity);
        linkedHashMap.put("Variety :", variety);
        linkedHashMap.put("Commodity Quality Parameters :", commodityQualityParameters);
        linkedHashMap.put("Moisture :", moisture);
        linkedHashMap.put("Foreign Material :", foreignMaterial);
        linkedHashMap.put("Color :", color);
        linkedHashMap.put("Damaged :", damaged);
        linkedHashMap.put("First Grade Price :", firstGradePrice);
        linkedHashMap.put("First Grade Kg :", firstGradeKg);
        linkedHashMap.put("Second Grade Price :", secondGradePrice);
        linkedHashMap.put("Second Grade Kg :", secondGradeKg);
        linkedHashMap.put("Third Grade Price :", thirdGradePrice);
        linkedHashMap.put("Third Grade Kg :", thirdGradeKg);

        return linkedHashMap;
    }
}
