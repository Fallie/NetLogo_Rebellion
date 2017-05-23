package person;

import patch.Patch;
import world.World;

import java.util.ArrayList;
import java.util.logging.Logger;

import static world.World.randInt;

/**
 * Created by fallie on 15/5/17.
 */
public class Cop extends Person {

    Logger logger = Logger.getLogger("Patch");
    public Cop(Patch currentPatch) {
        super(currentPatch);
    }

    public void enforce(){
        logger.info("cop is enforcing");
        int[] counts = getCurrentPatch().countInNeighborhood();
        ArrayList<Patch> tempNeighborhood = new ArrayList<>();
        if(counts[1]!=0){
            //select a random suspect and send it to jail
            for(Patch patch : getCurrentPatch().getNeighborhood()){
                if(patch.isActiveAgent()) tempNeighborhood.add(patch);
            }

            int selected = randInt(0, tempNeighborhood.size()-1);
            int selectedX = tempNeighborhood.get(selected).getX();
            int selectedY = tempNeighborhood.get(selected).getY();

            //send the suspect to jail on that patch
            World.patches[selectedX][selectedY].getSuspect().arrest();

        }
    }
}
