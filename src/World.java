import patch.Patch;

/**
 * The driver used to simulate the NetLogo_Rebellion.
 * This class describes the attributes and contains the behaviors of
 * the world of the Netlogo model.
 */

public class World {



        //
        private int numOfPathes;

        //
        private double initialCopDensity;

        //
        private double initialAgentDensity;

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

        public World(int numOfPathes, double initialCopDensity, double initialAgentDensity,
                     double governmentLegitimacy, double maxJailTerm, boolean movement,
                     int vision, boolean watchOne, int ticks) {
                this.numOfPathes = numOfPathes;
                this.initialCopDensity = initialCopDensity;
                this.initialAgentDensity = initialAgentDensity;
                this.governmentLegitimacy = governmentLegitimacy;
                this.maxJailTerm = maxJailTerm;
                this.movement = movement;
                this.vision = vision;
                this.watchOne = watchOne;
                this.ticks = ticks;
        }


        public void setup(){

        }

        public void go(){

        }



}
