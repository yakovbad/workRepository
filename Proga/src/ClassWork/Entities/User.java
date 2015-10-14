package ClassWork.Entities;

public class User {
    private int id;
    private String lastName;
    private String firstName;
    private String middleName;
    private int age;

    public User(int id, String lastName, String firstName, String middleName, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.age = age;
    }

    public User(String lastName, String firstName, String middleName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public int getAge() {
        return age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        StringBuilder st = new StringBuilder();
        st.append(id);
        st.append("|");
        st.append(lastName);
        st.append("|");
        st.append(firstName);
        st.append("|");
        st.append(middleName);
        st.append("|");
        st.append(age);
        return st.toString();
    }
}
