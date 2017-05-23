package configuration;

/**
 * The constant configured variables shared by all agents.
 */

public class Configuration {

    //Will be used to get a reasonable value of the estimated arrest
    // probability
    public final static double ARREST_FACTOR = 2.3;

    //A threshold which shows how much must the agents' grievance
    // tops the risk of been arrested to make one rebel.
    public final static double REBEL_THRESHOLD = 0.1;

}
