import world.World;

import java.io.IOException;

/**
 * The entrance of the program (main method) which
 * runs the world till the tick is 0.
 */
public class Output {
    public static void main(String[] args) throws IOException{

        int numOfPathes = Integer.parseInt(args[0]);

        double initialAgentDensity = Double.parseDouble(args[1]);

        double initialCopDensity = Double.parseDouble(args[2]);

        double governmentLegitimacy = Double.parseDouble(args[3]);

        int maxJailTerm = Integer.parseInt(args[4]);

        boolean movement = Boolean.parseBoolean(args[5]);

        int vision = Integer.parseInt(args[6]);

        int ticks = Integer.parseInt(args[7]);

        boolean extension = Boolean.parseBoolean(args[8]);

        boolean graduallyChangeGov = Boolean.parseBoolean(args[9]);

        boolean sharplyChangGov = Boolean.parseBoolean(args[10]);

        double numOfAgents = numOfPathes*numOfPathes*initialAgentDensity;

        double numOfCops = numOfPathes*numOfPathes*initialCopDensity;


        //Instantiate a world with the input arguments.
        World world = new World(numOfPathes, (int)numOfAgents, (int)numOfCops,
        governmentLegitimacy, maxJailTerm, movement, vision, ticks,
            extension, graduallyChangeGov
        , sharplyChangGov);

        //Setup the world.
        world.setup();

        //Run the world.
        world.go(ticks);

    }
}
