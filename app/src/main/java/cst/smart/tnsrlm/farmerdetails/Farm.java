package cst.smart.tnsrlm.farmerdetails;


import java.io.Serializable;

public class Farm implements Serializable {

    public String cultivatedCrop1,cultivatedCrop2,cultivatedCrop3,cultivatedCrop4,cultivatedCrop5,cultivatedCrop6;

    public Farm(String cultivatedCrop1, String cultivatedCrop2, String cultivatedCrop3, String cultivatedCrop4, String cultivatedCrop5, String cultivatedCrop6) {
        this.cultivatedCrop1 = cultivatedCrop1;
        this.cultivatedCrop2 = cultivatedCrop2;
        this.cultivatedCrop3 = cultivatedCrop3;
        this.cultivatedCrop4 = cultivatedCrop4;
        this.cultivatedCrop5 = cultivatedCrop5;
        this.cultivatedCrop6 = cultivatedCrop6;
    }

    public String getCultivatedCrop1() {
        return cultivatedCrop1;
    }

    public void setCultivatedCrop1(String cultivatedCrop1) {
        this.cultivatedCrop1 = cultivatedCrop1;
    }

    public String getCultivatedCrop2() {
        return cultivatedCrop2;
    }

    public void setCultivatedCrop2(String cultivatedCrop2) {
        this.cultivatedCrop2 = cultivatedCrop2;
    }

    public String getCultivatedCrop3() {
        return cultivatedCrop3;
    }

    public void setCultivatedCrop3(String cultivatedCrop3) {
        this.cultivatedCrop3 = cultivatedCrop3;
    }

    public String getCultivatedCrop4() {
        return cultivatedCrop4;
    }

    public void setCultivatedCrop4(String cultivatedCrop4) {
        this.cultivatedCrop4 = cultivatedCrop4;
    }

    public String getCultivatedCrop5() {
        return cultivatedCrop5;
    }

    public void setCultivatedCrop5(String cultivatedCrop5) {
        this.cultivatedCrop5 = cultivatedCrop5;
    }

    public String getCultivatedCrop6() {
        return cultivatedCrop6;
    }

    public void setCultivatedCrop6(String cultivatedCrop6) {
        this.cultivatedCrop6 = cultivatedCrop6;
    }
}
