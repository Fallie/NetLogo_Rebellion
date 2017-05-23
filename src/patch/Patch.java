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

    private int x;

    private int y;

    public Patch(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ArrayList<Patch> getNeighborhood() {

        ArrayList<Patch> neighbors = new ArrayList<>();

        for (int offsetY = 1; offsetY <= World.numOfPathes; offsetY++) {
            neighbors.add(GetPatch(x, y + offsetY));
            neighbors.add(GetPatch(x, y - offsetY));
        }
        for (int offsetX = 1; offsetX <= World.numOfPathes; offsetX++) {
            int sq = (int) Math.sqrt(World.numOfPathes * World.numOfPathes - offsetX * offsetX);
            neighbors.add(GetPatch(x + offsetX, y));
            neighbors.add(GetPatch(x - offsetX, y));

            for (int offsetY = 1; offsetY <= sq; offsetY++) {
                neighbors.add(GetPatch(x + offsetX, y + offsetY));
                neighbors.add(GetPatch(x - offsetX, y + offsetY));
                neighbors.add(GetPatch(x + offsetX, y - offsetY));
                neighbors.add(GetPatch(x - offsetX, y - offsetY));
            }
        }
        // //x-axis
        // //right
        // for(int i = y + 1; i <= y + World.vision; i ++){
        //     neighbors.add(World.patches[x][(i + World.numOfPathes) % World.numOfPathes]);
        // }
        // //left
        // for(int i = y - 1; i >= y - World.vision; i --){
        //     neighbors.add(World.patches[x][(i  + World.numOfPathes) % World.numOfPathes]);
        // }

        // //y-axis
        // //right
        // for(int i = x + 1; i <= x + World.vision; i ++){
        //     neighbors.add(World.patches[(i  + World.numOfPathes) % World.numOfPathes][y]);
        // }
        // //left
        // for(int i = x - 1; i >= x - World.vision; i --) {
        //     neighbors.add(World.patches[(i  + World.numOfPathes) % World.numOfPathes][y]);
        // }

        return neighbors;
    }

    public Patch GetPatch(int x, int y)
    {

        return World.patches[ (x + World.numOfPathes) % World.numOfPathes][(y + World.numOfPathes) % World.numOfPathes];
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
            if(person instanceof Cop || person instanceof Agent && ((Agent)person).getJailTerm()==0) return false;
        }
        return true;
    }

    public int[] countInNeighborhood(){
        int cops = 0;
        int activeAgents= 0;
        for(Patch patch : getNeighborhood()){
            if(patch.isCop()) cops ++;
            if(patch.isActiveAgent()) activeAgents ++;
        }
        return new int[]{cops,activeAgents};
    }

    public boolean isCop(){

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

    public Agent getAgent(){
        for(Person person : persons){
            if(person instanceof Agent && !((Agent) person).isActive()) return (Agent)person;
        }
        return null;
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