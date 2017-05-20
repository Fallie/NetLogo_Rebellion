package patch;

import person.Person;
import java.util.ArrayList;
import world.World;

public class Patch {


    private ArrayList<Person> persons = new ArrayList<Person>();

    private ArrayList<Patch> neighborhood = new ArrayList<Patch>();

    private int x;

    private int y;

    public Patch(int x, int y) {
        this.x = x;
        this.y = y;
        this.cacheNeiborhood();
    }

    public void cacheNeiborhood(){

        ArrayList<Patch> neighbors = new ArrayList<Patch>();
        int xVision = x + World.vision;
        int yVision  = y + World.vision;

        //wrapping the patches in the world
        for(int i = x; i < xVision% World.numOfPathes; i ++){
            for(int j = y; j< yVision% World.numOfPathes; j ++){
                neighbors.add(World.patches[i-1][j-1]);
            }
        }

        this.neighborhood = neighbors;
    }

    public ArrayList<Patch> getNeighborhood() {
        return neighborhood;
    }

    public Person getPerson() {
        return persons.get(0);
    }

    public void setPerson(Person newPerson) {
        if(this.persons.size() != 0){
            for(int i = this.persons.size()-1; i <= 0; i--){
                this.persons.add(i+1,this.persons.get(i));
            }

        }
        this.persons.add(0,newPerson);

    }

}