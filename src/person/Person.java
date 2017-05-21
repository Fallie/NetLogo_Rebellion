package person;

import patch.Patch;
import world.World;

import java.util.ArrayList;
import java.util.Random;

import static world.World.randInt;

/**
 * Created by fallie on 15/5/17.
 */
public class Person {


    private Patch currentPatch;

    private int jailTerm = 0;

    /**
     * The constructor of person class.
     * @param currentPatch
     */
    public Person(Patch currentPatch) {
        this.currentPatch = currentPatch;
    }

    //move a person to a movable patch
    public void move(){

        ArrayList<Patch> tempNeighborhood = new ArrayList<Patch>();

        if(World.movement && this instanceof Cop){

            for(Patch patch : currentPatch.getNeighborhood()){
                if(patch.isMoveable()) tempNeighborhood.add(patch);
            }

            int selected = randInt(0, tempNeighborhood.size()-1);
            int selectedX = tempNeighborhood.get(selected).getX();
            int selectedY = tempNeighborhood.get(selected).getY();

            //move the current person onto a random movable patch
            World.patches[currentPatch.getX()][currentPatch.getY()].removePerson(this);
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

    public void setJailTerm(int jailTerm) {
        this.jailTerm = jailTerm;
    }

    public int getJailTerm() {

        return jailTerm;
    }
}
