package patch;

import person.Agent;
import person.Cop;
import person.Person;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import world.World;

public class Patch {
    Logger logger = Logger.getLogger("Patch");

    private ArrayList<Person> persons = new ArrayList<>();

    private ArrayList<Patch> neighborhood = new ArrayList<>();

    private int x;

    private int y;

    public Patch(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public void updateNeighborhood(){

        ArrayList<Patch> neighbors = new ArrayList<>();

        //x-axis
        //right
        for(int i = y + 1; i <= y + World.vision; i ++){
            neighbors.add(World.patches[x][(i % World.numOfPathes + World.numOfPathes) % World.numOfPathes]);
        }
        //left
        for(int i = y - 1; i >= y - World.vision; i --){
            neighbors.add(World.patches[x][(i % World.numOfPathes + World.numOfPathes) % World.numOfPathes]);
        }

        //y-axis
        //right
        for(int i = x + 1; i <= x + World.vision; i ++){
            neighbors.add(World.patches[(i % World.numOfPathes + World.numOfPathes) % World.numOfPathes][y]);
        }
        //left
        for(int i = x - 1; i >= x - World.vision; i --) {
            neighbors.add(World.patches[(i % World.numOfPathes + World.numOfPathes) % World.numOfPathes][y]);
        }

        this.neighborhood = neighbors;

        //logger.info("updating for patch x = " + x + " y = " + y);
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

    public boolean isActiveAgent(){

        for(Person person : this.persons){
            if(person instanceof Agent && ((Agent) person).isActive()) return true;
        }
        return false;
    }

    public Agent getSuspect(){
        for(Person person : persons){
            if(person instanceof Agent && ((Agent) person).isActive()) return (Agent)person;
        }
        return null;
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