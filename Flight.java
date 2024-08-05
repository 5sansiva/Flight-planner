public class Flight {

    private String start;
    private String destination;
    private int flight_time;
    private double flight_cost;

    public Flight(String start, String destination, double cost, int time)
    {
        this.start = start;
        this.destination = destination;
        flight_cost = cost;
        flight_time = time;
    }

    public String getStart()
    {
        return this.start;
    }

    public String getDestination() {

        return this.destination;
    }

    public double getFlight_cost()
    {
        return flight_cost;
    }

    public int getFlight_time()
    {
        return flight_time;
    }


    public String toString()
    {
        String output = "Start: " + start + ", Destination: " + destination;
        return output;
    }


}
