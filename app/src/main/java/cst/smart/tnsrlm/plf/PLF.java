package cst.smart.tnsrlm.plf;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Created by admin on 30-11-2018.
 */

public class PLF implements Serializable {
    public String id;
    public String plfName;
    public String secretaryName;
    public String contact;
    public String treasurerName;
    public String gstin;
    public String registrationNumber;
    public String commission;
    public ArrayList<LivelihoodCommitee> livelihoodCommitees;

    public PLF(String id, String plfName, String secretaryName, String contact, String treasurerName, ArrayList<LivelihoodCommitee> livelihoodCommitees, String gstin, String registrationNumber, String commission) {
        this.id = id;
        this.plfName = plfName;
        this.secretaryName = secretaryName;
        this.contact = contact;
        this.treasurerName = treasurerName;
        this.livelihoodCommitees = livelihoodCommitees;
        this.gstin = gstin;
        this.registrationNumber = registrationNumber;
        this.commission = commission;
    }

    public PLF(String plfName, String secretaryName, String contact, String treasurerName, ArrayList<LivelihoodCommitee> livelihoodCommitees, String gstin, String registrationNumber, String commission) {
        this.plfName = plfName;
        this.secretaryName = secretaryName;
        this.contact = contact;
        this.treasurerName = treasurerName;
        this.livelihoodCommitees = livelihoodCommitees;
        this.gstin = gstin;
        this.registrationNumber = registrationNumber;
        this.commission = commission;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlfName() {
        return plfName;
    }

    public void setPlfName(String plfName) {
        this.plfName = plfName;
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

    public ArrayList<LivelihoodCommitee> getLivelihoodCommitees() {
        return livelihoodCommitees;
    }

    public void setLivelihoodCommitees(ArrayList<LivelihoodCommitee> livelihoodCommitees) {
        this.livelihoodCommitees = livelihoodCommitees;
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }
}
