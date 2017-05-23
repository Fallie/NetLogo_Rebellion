package person;

import patch.Patch;
import world.World;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

import static world.World.randInt;

/**
 * Created by fallie on 15/5/17.
 */
public class Person {
    Logger logger = Logger.getLogger("Person");

    public Patch currentPatch;


    /**
     * The constructor of person class.
     * @param currentPatch
     */
    public Person(Patch currentPatch) {
        this.currentPatch = currentPatch;
    }

    //move a person to a movable patch
    public void move(){

        ArrayList<Patch> tempNeighborhood = new ArrayList<>();
        if(World.movement || this instanceof Cop){

            for(Patch patch : currentPatch.getNeighborhood()){
                if(patch.isMoveable()) tempNeighborhood.add(patch);
            }

            int selected = randInt(0, tempNeighborhood.size()-1);
            int selectedX = tempNeighborhood.get(selected).getX();
            int selectedY = tempNeighborhood.get(selected).getY();

            //move the current person onto a random movable patch
            World.patches[currentPatch.getX()][currentPatch.getY()].removePerson(this);
//            logger.info("move from x=" + currentPatch.getX() + " y=" + currentPatch.getY()
//                +" to x=" + selectedX + " y=" + selectedY);
            World.patches[selectedX][selectedY].setPerson(this);
            this.setCurrentPatch(World.patches[selectedX][selectedY]);


        }
    }


    public Patch getCurrentPatch() {
        return currentPatch;
    }

    public void setCurrentPatch(Patch currentPatch) {
        this.currentPatch = currentPatch;
    }

}
