public class Actor {
    private String name;
    private String gender;
    private String country;

    public Actor(){
        name = null;
        gender = null;
        country = null;
    }
    public Actor(String name, String gender, String country){
        this.name = name;
        this.gender = gender;
        this.country = country;
    }

    public Actor(Actor actor){
        this.name = actor.getName();
        this.gender = actor.getGender();
        this.country = actor.getCountry();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "(" + name + ", " + gender + ", " + country + ")";
    }
}
