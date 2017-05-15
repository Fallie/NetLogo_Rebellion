package patch;

import person.Person;
import java.util.ArrayList;

public class Patch {


    private ArrayList<Person> persons;

    public Patch(ArrayList<Person> persons) {
        this.persons = persons;
    }

    public ArrayList<Person> getNeiborhood(){return null;}

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

}