package person;

import configuration.Configuration;
import patch.Patch;
import world.World;

import static java.lang.Math.exp;
import static world.World.maxJailTerm;
import static world.World.randInt;


/**
 * Created by fallie on 15/5/17.
 */
public class Agent extends Person {


    private boolean isActive;


    /**
     * The constructor of agent class.
     * @param currentPatch
     * @param isActive
     */
    public Agent(Patch currentPatch, boolean isActive) {
        super(currentPatch);
        this.isActive = isActive;
    }

    public double returnGrievance(){
        return Configuration.PERCEIVED_HARDSHIP * (1 - World.governmentLegitimacy);

    }

    public double returnArrestProbability(){
        int[] counts = getCurrentPatch().countInNeighborhood();
        return 1 -  exp(- Configuration.ARREST_FACTOR * (counts[0] / counts[1]));
    }

    public void determinBehavior(){
        if(returnGrievance() - Configuration.RISK_VERSION * returnArrestProbability()
            > Configuration.REBEL_THRESHOLD){
            this.isActive = true;
        }

    }

    public boolean isActive() {
        return isActive;
    }

    public void arrest(){
        this.isActive = false;
        this.setJailTerm(randInt(0, maxJailTerm));
    }


}
