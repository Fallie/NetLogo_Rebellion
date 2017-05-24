package person;

import patch.Patch;
import world.World;

import java.util.ArrayList;

import static world.World.randInt;

/**
 * The class represents the cop in the world.
 */
public class Cop extends Person {

    //The constructor of the cop.
    public Cop(Patch currentPatch) {
        super(currentPatch);
    }

    /**
     * Cops conducting arresting to a random active agents within the vision.
     */
    public void enforce(){
        // logger.info("cop is enforcing");
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

            //move this cop to that patch
            World.patches[this.getCurrentPatch().getX()][this.getCurrentPatch()
                .getY()].removePerson(this);
            World.patches[selectedX][selectedY].setPerson(this);
            this.setCurrentPatch(World.patches[selectedX][selectedY]);

        }
    }
}
