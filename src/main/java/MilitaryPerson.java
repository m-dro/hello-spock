public class MilitaryPerson {
    private String firstname;
    private String lastname;
    private String rank;

    public String createTitle() {
        return lastname + ", " + firstname + " (" + rank + ")";
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
