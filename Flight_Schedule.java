import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class Flight_Schedule {

    LinkedList<LinkedList<Flight>> flights;
    String fpath = "";

    public Flight_Schedule(String fpath)
    {
        flights = new LinkedList<LinkedList<Flight>>();
        this.fpath = fpath;

    }

    //Create the adjacency list and adds the flights to that
    public void setFlightSchedule()
    {
        try {
            Scanner input = new Scanner(new File(this.fpath));
            int count = input.nextInt();
            input.nextLine();
            for (int i = 0; i < count; i++) {
                String line = input.nextLine();
                String[] data = line.split("\\|");
                Flight f1 = new Flight(data[0], data[1], Double.parseDouble(data[2]), Integer.parseInt(data[3]));
                addFlights(f1);

                //Opposite direction flight
                Flight f2 = new Flight(data[1], data[0], Double.parseDouble(data[2]), Integer.parseInt(data[3]));
                addFlights(f2);
            }
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not Found. Please try again.");
            e.printStackTrace();
        }
    }
    public void addFlights(Flight f)
    {
        //Checks if the list is empty, if it is, then creates a new linkedlist and adds the flight object
        if(flights.isEmpty())
        {
            LinkedList<Flight> c = new LinkedList<Flight>();
            c.add(f);
            flights.add(c);
        }
        //If the is not empty, then it adds the element
        else {
            boolean isThere = true;
            //Goes through the flights linkedlist and checks the head of each linkedlist to see if they share the same starting point and can be in the same row
            for (LinkedList<Flight> l : flights) {
                //If the starting locations match with the head of the list, then the flight object gets added to the linked list
                if(l.getFirst().equals(f))
                {
                    l.add(f);
                    isThere = false;
                }
            }
            //If there isn't a match, a new linkedlist is created and added to the adjacency list
            if (isThere) {
                LinkedList<Flight> c = new LinkedList<Flight>();
                c.add(f);
                flights.add(c);
            }
        }
    }
    public  LinkedList<LinkedList<Flight>> getFlights()
    {
        return flights;
    }


}
