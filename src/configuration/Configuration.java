package configuration;

/**
 * The constant configured variables shared by all agents.
 */

public class Configuration {

    //All the agent will share a same value of risk-aversion which
    // describes how much they dislike the risk of being arrested
    // because of rebelling.
    public final static double RISK_VERSION = 1.0;

    //Describes how hard they think of their life, which may motivate
    // them to rebel.
    public final static double PERCEIVED_HARDSHIP = 1.0;

    //Will be used to get a reasonable value of the estimated arrest
    // probability
    public final static double ARREST_FACTOR = 2.3;

    //A threshold which shows how much must the agents' grievance
    // tops the risk of been arrested to make one rebel.
    public final static double REBEL_THRESHOLD = 0.1;



}
