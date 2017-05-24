package person;

import configuration.Configuration;
import patch.Patch;
import world.World;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

import static world.World.maxJailTerm;
import static world.World.randInt;


/**
 * The class represents for the agent which can rebel at some point.
 */
public class Agent extends Person {

    //If this agent is active.
    private boolean isActive;

    //The susceptibility of this agent, for the extension use only.
    private double susceptibility = 0;

    //All the agent will share a same value of risk-aversion which
    // describes how much they dislike the risk of being arrested
    // because of rebelling.
    private double riskAversion = 0;

    //Describes how hard they think of their life, which may motivate
    // them to rebel.
    private double perceivedHardship = 0;

    //The current jail term of this agent.
    private int jailTerm = 0;


    /**
     * The constructor of agent class.
     * @param currentPatch
     * @param isActive
     */
    public Agent(Patch currentPatch, boolean isActive) {
        super(currentPatch);
        this.isActive = isActive;
        this.generateAttribute();
    }

    /**
     * Calculate the grievance.
     * @return double
     */
    public double returnGrievance(){
        return this.perceivedHardship * (1 - World.governmentLegitimacy);
    }

    /**
     * Calculate the arrestProbability.
     * @return double
     */
    public double returnArrestProbability(){

        int[] counts = getCurrentPatch().countInNeighborhood();
        return 1 -  Math.exp(-Configuration.ARREST_FACTOR *
                (counts[0] / (1+counts[1])) );
    }

    /**
     * Check if this agent is going to rebel.
     */
    public void determinBehavior(){

        if(returnGrievance() - (this.riskAversion * returnArrestProbability())
            > Configuration.REBEL_THRESHOLD){
            this.isActive = true;
        }
        else this.isActive = false;

    }

    /**
     * Check if this agent is going to rebel, for extension use only.
     */
    public void extensionBehavior(){
        double grievance = returnGrievance();
        if(grievance - this.riskAversion * returnArrestProbability()
            - susceptibility * (grievance - averageGrievance())>
                Configuration.REBEL_THRESHOLD){
            this.isActive = true;
        }
        else this.isActive = false;

    }

    /**
     * Calculate the average grievance around this agent.
     * @return double
     */
    private double averageGrievance(){
        double sum = 0;
        ArrayList<Patch> neiborhoods =  this.getCurrentPatch().
                                        getNeighborhood();
        for(Patch patch : neiborhoods){
            if(!patch.isCop()){
                if(patch.getAgent() != null)
                    sum += patch.getAgent().returnGrievance();
                if(patch.getSuspect() != null)
                    sum += patch.getSuspect().returnGrievance();
            }
        }
        return sum/neiborhoods.size();
    }

    public boolean isActive() {
        return this.isActive;
    }

    /**
     * Arrest this agent.
     */
    public void arrest(){
        this.isActive = false;
        this.setJailTerm(randInt(0, maxJailTerm));
    }

    /**
     * Generate riskAversion, susceptibility and the perceivedHardship.
     */
    private void generateAttribute(){
        this.riskAversion = Math.random();
        this.susceptibility = Math.random();
        this.perceivedHardship = Math.random();
    }

    public int returnJailTerm()
    {
        return this.jailTerm;
    }

    public void setJailTerm(int jailTerm) {
        this.jailTerm = jailTerm;
    }

    public void reduceJailTerm(){ this.jailTerm --; }

    public int getJailTerm() {

        return jailTerm;
    }

}
