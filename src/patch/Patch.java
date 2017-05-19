package patch;

import person.Person;
import java.util.ArrayList;

public class Patch {


    private ArrayList<Person> persons = new ArrayList<Person>();

    private int x;

    private int y;

    public Patch(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ArrayList<Person> getNeiborhood(){return null;}

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

}