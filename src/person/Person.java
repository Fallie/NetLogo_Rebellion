package person;

import patch.Patch;

/**
 * Created by fallie on 15/5/17.
 */
public class Person {


    private Patch currentPatch;

    public Person(Patch currentPatch) {
        this.currentPatch = currentPatch;
    }

    public void move(){

    }


    public Patch getCurrentPatch() {
        return currentPatch;
    }

    public void setCurrentPatch(Patch currentPatch) {
        this.currentPatch = currentPatch;
    }
}
