import patch.Patch;
import person.Agent;
import person.Person;

/**
 * The driver used to simulate the NetLogo_Rebellion.
 * This class describes the attributes and contains the behaviors of
 * the world of the Netlogo model.
 */

public class World {



        //
        private int numOfPathes;

        //
        private int numOfCops;

        //
        private int numOfAgents;

        //
        private double governmentLegitimacy;

        //
        private double maxJailTerm;

        //
        private boolean movement;

        //
        private int vision;

        //
        private boolean watchOne;

        //
        private int ticks;

        //
        private Patch[][] patches;

        //
        private Agent[] agents;

        /**
         * The constructor of world class.
         * @param numOfPathes
         * @param numOfAgents
         * @param numOfCops
         * @param governmentLegitimacy
         * @param maxJailTerm
         * @param movement
         * @param vision
         * @param watchOne
         * @param ticks
         */
        public World(int numOfPathes, int numOfAgents, int numOfCops,
                     double governmentLegitimacy, double maxJailTerm, boolean movement,
                     int vision, boolean watchOne, int ticks) {
                this.numOfPathes = numOfPathes;
                this.numOfAgents = numOfAgents;
                this.numOfCops = numOfCops;
                this.governmentLegitimacy = governmentLegitimacy;
                this.maxJailTerm = maxJailTerm;
                this.movement = movement;
                this.vision = vision;
                this.watchOne = watchOne;
                this.ticks = ticks;
        }

        /**
         * To setup the model to make it ready to run.
         */
        public void setup(){
            //Initialize the patches in the world.
            patches = new Patch[numOfPathes][numOfPathes];
            for(int i = 0; i < patches.length; i ++){
                for(int j = 0; j < patches[i].length; j ++){
                    patches[i][j] = new Patch(i,j);
                }
            }

            //Generate agents and cops.
            agents = new Agent[numOfAgents];
            for(int k = 0; k < agents.length; k ++){
                agents[k] = generateAgent();
            }
        }

        public void go(int ticks){

        }

        public Agent generateAgent(){
            return null;
        }



}
