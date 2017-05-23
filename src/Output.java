import world.World;

import java.io.IOException;

/**
 * Created by fallie on 15/5/17.
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

        boolean watchOne = Boolean.parseBoolean(args[7]);

        int ticks = Integer.parseInt(args[8]);

        boolean extension = Boolean.parseBoolean(args[9]);

        boolean graduallyChangeGov = Boolean.parseBoolean(args[10]);

        boolean sharplyChangGov = Boolean.parseBoolean(args[11]);

        double numOfAgents = numOfPathes*numOfPathes*initialAgentDensity;

        double numOfCops = numOfPathes*numOfPathes*initialCopDensity;



        World world = new World(numOfPathes, (int)numOfAgents, (int)numOfCops,
        governmentLegitimacy, maxJailTerm, movement, vision, watchOne, ticks, extension, graduallyChangeGov
        , sharplyChangGov);

        world.setup();

        world.go(ticks);

    }
}
