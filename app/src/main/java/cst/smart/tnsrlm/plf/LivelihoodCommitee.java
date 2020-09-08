package cst.smart.tnsrlm.plf;

/**
 * Created by admin on 03-12-2018.
 */

import java.io.Serializable;


public class LivelihoodCommitee implements Serializable {
    public String descriptionone;
    public String descriptiontwo;
    public String descriptionthree;

    public LivelihoodCommitee(String descriptionone, String descriptiontwo, String descriptionthree) {
        this.descriptionone = descriptionone;
        this.descriptiontwo = descriptiontwo;
        this.descriptionthree = descriptionthree;
    }


    public String getDescriptionone() {
        return descriptionone;
    }

    public void setDescriptionone(String descriptionone) {
        this.descriptionone = descriptionone;
    }

    public String getDescriptiontwo() {
        return descriptiontwo;
    }

    public void setDescriptiontwo(String descriptiontwo) {
        this.descriptiontwo = descriptiontwo;
    }

    public String getDescriptionthree() {
        return descriptionthree;
    }


    public void setDescriptionthree(String descriptionthree) {
        this.descriptionthree = descriptionthree;

    }


}
