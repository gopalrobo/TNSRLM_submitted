package cst.smart.tnsrlm.bank;

import java.io.Serializable;

/**
 * Created by admin on 29-11-2018.
 */

public class BankDetails implements Serializable {
    String id;
    String bankname;
    String bankbranch;
    String bankacno;
    String bankstartdate;
    String bankifsc;
    String bankbalanceamount;


    public BankDetails(String bankname, String bankbranch, String bankacno, String bankstartdate, String bankifsc, String bankbalanceamount) {
        this.bankname = bankname;
        this.bankbranch = bankbranch;
        this.bankacno = bankacno;
        this.bankstartdate = bankstartdate;
        this.bankifsc = bankifsc;
        this.bankbalanceamount = bankbalanceamount;
    }


    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBankbranch() {
        return bankbranch;
    }

    public void setBankbranch(String bankbranch) {
        this.bankbranch = bankbranch;
    }

    public String getBankacno() {
        return bankacno;
    }

    public void setBankacno(String bankacno) {
        this.bankacno = bankacno;
    }

    public String getBankstartdate() {
        return bankstartdate;
    }

    public void setBankstartdate(String bankstartdate) {
        this.bankstartdate = bankstartdate;
    }

    public String getBankifsc() {
        return bankifsc;
    }

    public void setBankifsc(String bankifsc) {
        this.bankifsc = bankifsc;
    }

    public String getBankbalanceamount() {
        return bankbalanceamount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBankbalanceamount(String bankbalanceamount) {
        this.bankbalanceamount = bankbalanceamount;


    }
}
