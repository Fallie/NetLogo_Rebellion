package patch;

import person.Person;
import java.util.ArrayList;
import world.World;

public class Patch {


    private ArrayList<Person> persons = new ArrayList<Person>();

    private int x;

    private int y;

    public Patch(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ArrayList<Patch> getNeiborhood(){

        ArrayList<Patch> neighbors = new ArrayList<Patch>();
        int xVision = x + World.vision;
        int yVision  = y + World.vision;

        //wrapping the patches in the world
        for(int i = x; i < xVision% World.numOfPathes; i ++){
            for(int j = y; j< yVision% World.numOfPathes; j ++){
                neighbors.add(World.patches[i-1][j-1]);
            }
        }

        return neighbors;
    }

    public Person getPerson() {
        return persons.get(0);
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

}