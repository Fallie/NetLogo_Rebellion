package patch;

import person.Agent;
import person.Cop;
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
        this.updateNeighborhood();
    }


    public void updateNeighborhood(){

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

    public ArrayList<Person> getPerson() {
        return persons;
    }

    public void setPerson(Person newPerson) {

        this.persons.add(newPerson);
    }

    public void removePerson(Person leftPerson){
        this.persons.remove(leftPerson);
    }

    public boolean isMoveable(){

        //if the persons is null, return true
        if(persons == null) return true;

        //if the all the person on this patch has jail term > 0, return true
        for(Person person : this.persons){
            if(person.getJailTerm()==0) return false;
        }
        return true;
    }

    public int[] countInNeighborhood(){
        int cops = 0;
        int activeAgents= 0;
        for(Patch patch : neighborhood){
            if(patch.isCop()) cops ++;
            if(patch.isActiveAgent()) activeAgents ++;
        }
        return new int[]{cops,activeAgents};
    }

    private boolean isCop(){

        for(Person person : this.persons){
            if(person instanceof Cop) return true;
        }
        return false;
    }

    private boolean isActiveAgent(){

        for(Person person : this.persons){
            if(person instanceof Agent && ((Agent) person).isActive()) return true;
        }
        return false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}