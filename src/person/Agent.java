package person;

import patch.Patch;

/**
 * Created by fallie on 15/5/17.
 */
public class Agent extends Person {


    private boolean isActive;

    public int jailTerm;

    /**
     * The constructor of agent class.
     * @param currentPatch
     * @param isActive
     * @param jailTerm
     */
    public Agent(Patch currentPatch, boolean isActive, int jailTerm) {
        super(currentPatch);
        this.isActive = isActive;
        this.jailTerm = jailTerm;
    }

    public void returnGrievance(){

    }

    public void returnArrestProbability(){

    }

    public void determinBehavior(){

    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getJailTerm() {
        return jailTerm;
    }

    public void setJailTerm(int jailTerm) {
        this.jailTerm = jailTerm;
    }
}
