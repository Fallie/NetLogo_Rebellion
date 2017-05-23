package person;

import configuration.Configuration;
import patch.Patch;
import world.World;

import java.util.ArrayList;
import java.util.logging.Logger;

import static java.lang.Math.exp;
import static world.World.maxJailTerm;
import static world.World.randInt;


/**
 * Created by fallie on 15/5/17.
 */
public class Agent extends Person {

    Logger logger = Logger.getLogger("Agent");
    private boolean isActive;
    private double susceptibility = 0;

    //All the agent will share a same value of risk-aversion which
    // describes how much they dislike the risk of being arrested
    // because of rebelling.
    private double riskAversion = 0;
    //Describes how hard they think of their life, which may motivate
    // them to rebel.
    private double perceivedHardship = 0;


    /**
     * The constructor of agent class.
     * @param currentPatch
     * @param isActive
     */
    public Agent(Patch currentPatch, boolean isActive) {
        super(currentPatch);
        this.isActive = isActive;
        this.generateSusceptibility();
    }

    public double returnGrievance(){
        return this.perceivedHardship * (1 - World.governmentLegitimacy);
    }

    public double returnArrestProbability(){
        int[] counts = getCurrentPatch().countInNeighborhood();
        double downward = counts[0] / ( 1 + counts[1]);
//        logger.info("cops: " + counts[0] + " active: " + counts[1]);
        return 1 -  exp(- Configuration.ARREST_FACTOR * (int)downward);
    }

    public void determinBehavior(){
//        logger.info("determing behavior");
        if(returnGrievance() - this.riskAversion * returnArrestProbability()
            > Configuration.REBEL_THRESHOLD){
            this.isActive = true;
        }

        if(this.isActive) logger.info("***found an active agent here!!!!!");

    }

    public void extensionBehavior(){
        double grievance = returnGrievance();
        if(grievance - this.riskAversion * returnArrestProbability()
            - susceptibility * (grievance - averageGrievance())> Configuration.REBEL_THRESHOLD){
            this.isActive = true;
        }
        if(this.isActive) logger.info("***found an active agent with extension!!!!!");
    }

    private double averageGrievance(){
        double sum = 0;
        ArrayList<Patch> neiborhoods =  this.getCurrentPatch().getNeighborhood();
        for(Patch patch : neiborhoods){
            if(!patch.isCop() && !patch.isActiveAgent() && patch.getAgent()!= null)
                sum += patch.getAgent().returnGrievance();
        }
        return sum/neiborhoods.size();
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void arrest(){
        this.isActive = false;
        this.setJailTerm(randInt(0, maxJailTerm));
    }

    private void generateSusceptibility(){
        this.susceptibility = Math.random();
        this.perceivedHardship = Math.random();
        this.riskAversion = Math.random();
    }


}
