package patch;

import person.Agent;
import person.Cop;
import person.Person;
import java.util.ArrayList;

import world.World;

/**
 * The class represent the patch in netlogo. The world is made of
 * the patches. The patches are wrapped in this world.
 *
 */
public class Patch {

    //a list of persons on this patch
    private ArrayList<Person> persons = new ArrayList<>();

    //x value
    private int x;

    //y value
    private int y;

    /**
     * The constructor of a patch.
     * @param x
     * @param y
     */
    public Patch(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the real-time neighborhood and their status of this patch within the
     * vision.
     * Avoid adding the current row and column twice. Added the patch itself
     * as what is stated in netlogo's in-radius documentation.
     * @return ArrayList<Patch>
     */
    public ArrayList<Patch> getNeighborhood() {

        ArrayList<Patch> neighbors = new ArrayList<>();
        for (int offsetY = 1; offsetY <= World.vision; offsetY++) {
            neighbors.add(GetPatch(x, y + offsetY));
            neighbors.add(GetPatch(x, y - offsetY));
        }
        for (int offsetX = 1; offsetX <= World.vision; offsetX++) {
            int sq = (int) Math.sqrt(World.vision * World.vision - offsetX *
                offsetX);
            neighbors.add(GetPatch(x + offsetX, y));
            neighbors.add(GetPatch(x - offsetX, y));

            for (int offsetY = 1; offsetY <= sq; offsetY++) {
                neighbors.add(GetPatch(x + offsetX, y + offsetY));
                neighbors.add(GetPatch(x - offsetX, y + offsetY));
                neighbors.add(GetPatch(x + offsetX, y - offsetY));
                neighbors.add(GetPatch(x - offsetX, y - offsetY));
            }
        }
        neighbors.add(this);

        return neighbors;
    }

    /**
     * Get a wrapped patch in the world.
     * @param x
     * @param y
     * @return Patch
     */
    public Patch GetPatch(int x, int y)
    {

        return World.patches[ (x + World.numOfPathes) % World.numOfPathes]
            [(y + World.numOfPathes) % World.numOfPathes];
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

    /**
     * Check if this patch has a cop or a quite agent on it.
     * If not, the patch is movable.
     * @return boolean
     */
    public boolean isMovable(){

        //if the persons is null, return true
        if(persons == null) return true;

        //if the all the person on this patch has jail term > 0, return true
        for(Person person : this.persons){
            if(person instanceof Cop || person instanceof Agent &&
                ((Agent)person).getJailTerm()==0) return false;
        }
        return true;
    }

    /**
     * Return the number of cops and active agents nearby within vision.
     * @return int[]
     */
    public int[] countInNeighborhood(){
        int cops = 0;
        int activeAgents= 0;
        for(Patch patch : getNeighborhood()){
            if(patch.isCop()) cops ++;
            if(patch.isActiveAgent()) activeAgents ++;
        }
        return new int[]{cops,activeAgents};
    }

    /**
     * Check if the current person is a cop.
     * @return boolean
     */
    public boolean isCop(){

        for(Person person : this.persons){
            if(person instanceof Cop) return true;
        }
        return false;
    }

    /**
     * Check if the current person is an active agent.
     * @return boolean
     */
    public boolean isActiveAgent(){

        for(Person person : this.persons){
            if(person instanceof Agent && ((Agent) person).isActive())
                return true;
        }
        return false;
    }

    /**
     * Get the current quiet agent.
     * @return Agent
     */
    public Agent getAgent(){
        for(Person person : persons){
            if(person instanceof Agent && !((Agent) person).isActive())
                return (Agent)person;
        }
        return null;
    }

    /**
     * Get the current active agent.
     * @return Agent
     */
    public Agent getSuspect(){
        for(Person person : persons){
            if(person instanceof Agent && ((Agent) person).isActive())
                return (Agent)person;
        }
        return null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}