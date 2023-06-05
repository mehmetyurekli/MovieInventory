import java.util.ArrayList;

public class Movie {
    private int year;
    private String name;
    private String genre;
    private String director;
    ArrayList<Actor> actors;

    public Movie() {
        setYear(0);
        setName(null);
        setGenre(null);
        setDirector(null);
    }

    public Movie(int year,String name, String genre, String director, ArrayList<Actor> actors) {
        this.year = year;
        this.name = name;
        this.genre = genre;
        this.director = director;
        this.actors = actors;
    }

    public Movie(Movie movie) {
        this.year = movie.getYear();
        this.name = movie.getName();
        this.genre = movie.getGenre();
        this.director = movie.getDirector();
        this.actors = movie.getActors();
    }

    public static String serialize(Movie movie){ //CONVERTS A MOVIE OBJECT TO STRING
        StringBuilder actors = new StringBuilder();
        for(Actor a : movie.getActors()){
            actors.append(a.toString().trim());
        }
        return (movie.getYear()) + ", " + movie.getName() + ", " + movie.getGenre()
                + ", " + movie.getDirector() + ", {" + actors + "}";
    }

    public static Movie deserialize(String string) { //CONVERTS A STRING TO MOVIE OBJECT
        String movieDataRaw = string.substring(0, string.indexOf('{')).trim();
        String[] movieData = movieDataRaw.split(",");
        String actorsRaw = string.substring(string.indexOf('{'));
        actorsRaw = actorsRaw.replaceAll("[({}]", "");
        String[] actorsString = actorsRaw.split("[)]");
        ArrayList<Actor> actors = new ArrayList<>();
        for(String actor : actorsString){
            String[] actorData = actor.split(",");
            if(actorData.length != 3){
                throw new IndexOutOfBoundsException();
            }
            actors.add(new Actor(actorData[0].trim(), actorData[1].trim(), actorData[2].trim()));
        }

        return new Movie(Integer.parseInt(movieData[0]), movieData[1].trim(), movieData[2].trim(), movieData[3].trim(), actors);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

    public void setActors(ArrayList<Actor> actors) {
        this.actors = actors;
    }

    @Override
    public String toString() {
        return Movie.serialize(this);
    }

}
