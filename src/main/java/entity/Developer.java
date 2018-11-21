package entity;

public class Developer extends Worker{


    public Developer(int id, int prj1_id, int prj2_id, String name, String surname) {
        super(id, prj1_id, prj2_id, name, surname);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
