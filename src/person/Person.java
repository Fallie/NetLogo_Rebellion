package person;

import patch.Patch;
import world.World;

import java.util.ArrayList;
import static world.World.randInt;

/**
 * The class represents a turtle in netlogo.
 */
public class Person {

    //the current patch of the pserson
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
            //iterate through the neighbors
            for(Patch patch : currentPatch.getNeighborhood()){
                if(patch.isMovable()) tempNeighborhood.add(patch);
            }

            int selected = randInt(0, tempNeighborhood.size()-1);
            int selectedX = tempNeighborhood.get(selected).getX();
            int selectedY = tempNeighborhood.get(selected).getY();

            //move the current person onto a random movable patch
            World.patches[currentPatch.getX()][currentPatch.getY()].
                removePerson(this);
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
