import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MyLinkedList movieList = new MyLinkedList();
        int corruptedData = 0;
        try {
            Scanner file = new Scanner(new FileInputStream("data.txt"));
            while (file.hasNext()) {
                String line = file.nextLine();
                try {
                    movieList.addSorted(Movie.deserialize(line));
                } catch (Exception ex) {
                    corruptedData += 1;
                }
            }
            System.out.println("Welcome to MovieInventory! " + movieList.getSize() + " movies were found.");
            if (corruptedData > 0) {
                System.out.println(corruptedData + " movie entries are corrupted!");
            }
            file.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found!");
            System.out.println("Continuing with 0 movies.");
        }

        //MENU
        int menuInput = 0;
        while (menuInput != 7) {
            boolean correctInput = false;
            do {
                try {
                    Scanner sc = new Scanner(System.in);
                    System.out.println("""
                                            
                            1)Add a movie
                            2)Search
                            3)Remove a movie
                            4)List all movies(start to end)
                            5)List all movies(end to start)
                            6)List all movies before 2000
                            7)Save and exit""");
                    System.out.print("Please select an operation: ");
                    menuInput = sc.nextInt();
                    if (menuInput > 0 && menuInput < 8) {
                        correctInput = true;
                        continue;
                    }
                    System.out.println("Please enter an integer between 1-7!");
                } catch (Exception ex) {
                    System.out.println("Please enter an integer between 1-7!");
                }
            }
            while (!correctInput); //GETS CORRECT MENU INPUT
            correctInput = false;

            Scanner scanner = new Scanner(System.in);
            System.out.println();
            //MENU METHODS-OPERATIONS
            switch (menuInput) {
                case 1:
                    int year = 0;
                    do {
                        try {
                            Scanner sc = new Scanner(System.in);
                            System.out.print("Enter the year of the movie: ");
                            year = sc.nextInt();
                            if (year > 0) {
                                correctInput = true;
                                continue;
                            }
                            System.out.println("The year can't be negative!");
                        } catch (Exception ex) {
                            System.out.println("Please enter an integer as the year!");
                        }
                    }
                    while (!correctInput);
                    System.out.print("Enter the movie name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter genre: ");
                    String genre = scanner.nextLine();
                    System.out.print("Enter director: ");
                    String director = scanner.nextLine();

                    ArrayList<Actor> addedActors = new ArrayList<>();

                    if (name.trim().equals("") || genre.trim().equals("") || director.trim().equals("")) {
                        System.out.println("Invalid movie data!");
                        continue;
                    }

                    System.out.print("Do you want to add an actor? (y/n): ");
                    char add = Character.toUpperCase(scanner.next().charAt(0));
                    while (add == 'Y') {
                        scanner.nextLine();
                        System.out.print("Enter actors name: ");
                        String addName = scanner.nextLine();
                        if (addName.equalsIgnoreCase("ryan gosling")) {
                            System.out.println("He's literally me.");
                        }
                        System.out.print("Enter actors gender: ");
                        String addGender = scanner.nextLine();
                        System.out.print("Enter country: ");
                        String addCountry = scanner.nextLine();
                        if (addName.trim().equals("") || addGender.trim().equals("") || addCountry.trim().equals("")) {
                            System.out.println("Invalid actor data.");
                        } else {
                            addedActors.add(new Actor(addName, addGender, addCountry));
                        }
                        System.out.print("Do you want to add another actor? (y/n): ");
                        add = Character.toUpperCase(scanner.next().charAt(0));
                    }
                    Movie addMovie = new Movie(year, name, genre, director, addedActors);
                    if (movieList.findNode(name, year) != null && movieList.findNode(name, year).getMovie().getName().equals(name) && movieList.findNode(name, year).getMovie().getYear() == year) {
                        System.out.println("Movie already exists.");
                        continue;
                    }
                    movieList.addSorted(addMovie);
                    System.out.println("Movie added.");
                    continue;
                case 2:
                    System.out.print("Enter a movie name to search: ");
                    String movieName = scanner.nextLine();
                    Node findNode = movieList.findNode(movieName, movieList.getHead());
                    if (findNode == null) {
                        System.out.println("Movie does not exist.");
                        continue;
                    }
                    System.out.println(findNode.getMovie());
                    Node nextFind = movieList.findNode(movieName, findNode.getNext());
                    while (nextFind != null) {
                        System.out.println(nextFind.getMovie());
                        nextFind = movieList.findNode(movieName, nextFind.getNext());
                    }
                    System.out.println("End of list.");
                    continue;
                case 3:
                    System.out.print("Enter a movie name to delete: ");
                    movieName = scanner.nextLine();
                    findNode = movieList.findNode(movieName, movieList.getHead());
                    if (findNode == null) {
                        System.out.println("Movie does not exist.");
                        continue;
                    }
                    System.out.println(findNode.getMovie());
                    nextFind = movieList.findNode(movieName, findNode.getNext());
                    int count = 0;
                    while (nextFind != null) {
                        System.out.println(nextFind.getMovie());
                        count++;
                        nextFind = movieList.findNode(movieName, nextFind.getNext());
                    }
                    int deleteYear = 0;
                    if (count > 0) {
                        System.out.println("There are more than 1 movies sharing the name \"" + movieName + "\"");
                        boolean correctYear = false;
                        while (!correctYear) {
                            try {
                                Scanner sc = new Scanner(System.in);
                                System.out.print("Please enter the year of the movie you want to delete: ");
                                deleteYear = sc.nextInt();
                                if (deleteYear > 0) {
                                    correctYear = true;
                                    continue;
                                }
                                System.out.println("The year can't be negative!");
                            } catch (Exception ex) {
                                System.out.println("Please enter an integer as the year!");
                            }
                        }
                        System.out.println("This process can not be undone!");
                        System.out.print("Are you sure? (y/n): ");
                        char permission = Character.toUpperCase(scanner.next().charAt(0));
                        while (permission != 'Y' && permission != 'N') {
                            System.out.print("Are you sure? (y/n): ");
                            permission = Character.toUpperCase(scanner.next().charAt(0));
                        }
                        if (permission == 'N') {
                            continue;
                        }
                        Node nodeToRemove = movieList.findNode(movieName, deleteYear);
                        if (nodeToRemove == null) {
                            System.out.println("Movie does not exist.");
                            continue;
                        }
                        movieList.removeNode(nodeToRemove);
                        System.out.println("Removed movie successfully");
                        continue;
                    }
                    System.out.println("This process can not be undone!");
                    System.out.print("Are you sure? (y/n): ");
                    char permission = Character.toUpperCase(scanner.next().charAt(0));
                    while (permission != 'Y' && permission != 'N') {
                        System.out.print("Are you sure? (y/n): ");
                        permission = Character.toUpperCase(scanner.next().charAt(0));
                    }
                    if (permission == 'N') {
                        continue;
                    }
                    movieList.removeNode(findNode);
                    System.out.println("Removed movie successfully");
                    continue;

                case 4:
                    System.out.println();
                    movieList.printList(false);
                    continue;
                case 5:
                    System.out.println();
                    movieList.printList(true);
                    continue;
                case 6:
                    System.out.println();
                    movieList.printBefore(2000);
                    continue;
                case 7:
                    break;
            }
        }
        movieList.writeList("data.txt");
    }
}