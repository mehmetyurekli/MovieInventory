import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class MyLinkedList {
    private Node head;
    private Node tail;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
    }

    public MyLinkedList(MyLinkedList list) {
        this.head = list.getHead();
        this.tail = list.getTail();
        this.size = list.getSize();
    }

    public void addSorted(Movie movie) { //ADDS A MOVIE TO THE CORRECT SPOT(SORTING)
        Node data = new Node(movie);
        if (head == null) {
            head = data;
            tail = data;
            data.setNext(null);
            data.setPrev(null);
            size += 1;
            return;
        }
        Node iterator = head;
        while (iterator != null) {
            if (isAfter(iterator, data)) {
                iterator = iterator.getNext();
            }
            else {
                Node prevNode = iterator.getPrev();
                if (prevNode == null) {
                    setHead(data);
                    data.setPrev(null);
                    data.setNext(iterator);
                    iterator.setPrev(data);
                    size += 1;
                    return;
                }
                iterator.setPrev(data);
                prevNode.setNext(data);
                data.setPrev(prevNode);
                data.setNext(iterator);
                size += 1;
                return;
            }

        }
        getTail().setNext(data);
        data.setPrev(getTail());
        data.setNext(null);
        setTail(data);
        size += 1;
    }

    public void removeNode(Node node) { //DELETES THE GIVEN NODE
        Node prevNode = node.getPrev();
        Node nextNode = node.getNext();
        if (prevNode == null) {
            if(nextNode == null){
                head = null;
                return;
            }
            nextNode.setPrev(null);
            head = nextNode;
        } else if (nextNode == null) {
            prevNode.setNext(null);
        } else {
            prevNode.setNext(nextNode);
            nextNode.setPrev(prevNode);
        }
    }

    public Node findNode(String movieName, Node startNode) { //FINDS THE FIRST MOVIE GIVEN FROM STARTING NODE
        Node iterator = startNode;
        while (iterator != null) {
            if (iterator.getMovie().getName().equalsIgnoreCase(movieName)) {
                return iterator;
            }
            iterator = iterator.getNext();
        }
        return null;
    }

    public Node findNode(String movieName, int year){ //FINDS THE MOVIE FROM GIVEN NAME AND YEAR
        Node iterator = head;
        while (iterator != null) {
            if (iterator.getMovie().getName().equalsIgnoreCase(movieName) && iterator.getMovie().getYear() == year) {
                return iterator;
            }
            iterator = iterator.getNext();
        }
        return null;
    }

    public boolean isAfter(Node iterator, Node newData) {
        if (newData.getMovie().getYear() == iterator.getMovie().getYear()) {
            String newName = newData.getMovie().getName();
            String iteratorName = iterator.getMovie().getName();
            return newName.compareToIgnoreCase(iteratorName) > 0;
        }
        return newData.getMovie().getYear() > iterator.getMovie().getYear();
    }

    public void printList(boolean reversed){
        if(!reversed){
            Node iterator = getHead();
            while(iterator != null){
                System.out.println(iterator.getMovie());
                iterator = iterator.getNext();
            }
            System.out.println("End of list.");
            return;
        }
        Node iterator = getTail();
        while(iterator != null){
            System.out.println(iterator.getMovie());
            iterator = iterator.getPrev();
        }
        System.out.println("End of list.");
    }

    public void writeList(String path){ //SAVES THE CHANGES TO FILE OR CREATES A NEW FILE IF IT DOESN'T EXIST
        Node iterator = getHead();
        PrintWriter outputStream = null;
        try {
            outputStream =  new PrintWriter(new FileOutputStream(path));
        } catch (FileNotFoundException e) {
            System.out.println("Can't access the file.");
            System.exit(0);
        }
        while(iterator != null){
            outputStream.println(iterator.getMovie());
            iterator = iterator.getNext();
        }
        outputStream.close();
        System.out.println("Success!");
    }

    public void printBefore(int year){
        Node iterator = getHead();
        while(iterator != null){
            if(iterator.getMovie().getYear() < year){
                System.out.println(iterator.getMovie());
            }
            iterator = iterator.getNext();
        }
        System.out.println("End of list.");
    }

    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public Node getTail() {
        return tail;
    }

    public void setTail(Node tail) {
        this.tail = tail;
    }

    public int getSize() {
        return size;
    }
}
