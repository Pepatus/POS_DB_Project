package bll;

public class Person {
    private int id;
    private int idActivity;
    private String firstname;
    private String lastname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdActivity() {
        return idActivity;
    }

    public void setIdActivity(int idActivity) {
        this.idActivity = idActivity;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Person(int id, int idActivity, String firstname, String lastname) {
        this.id = id;
        this.idActivity = idActivity;
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
