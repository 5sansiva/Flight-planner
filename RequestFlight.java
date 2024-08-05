import java.util.*;
public class RequestFlight {

    String start = "";
    String end = "";

    Stack<LinkedList<String>> marked;
    String sortPref = "";
    LinkedList<LinkedList<Flight>> schedule;

    LinkedList<LinkedList<String>> allPaths;


    public RequestFlight(String start, String end, String pref, LinkedList<LinkedList<Flight>> fs)
    {
        this.start = start;
        this.end = end;
        sortPref = pref;
        schedule = fs;
        marked = new Stack<LinkedList<String>>();
        allPaths = new LinkedList<LinkedList<String>>();
    }

    public LinkedList<LinkedList<Flight>> getSchedule()
    {
        return schedule;
    }


    //Returns all the neighbors of the given start string
    public LinkedList<String> getNeighbors(String s)
    {
        //Gets the neighbors from the adjacency list
        LinkedList<String> n = new LinkedList<String>();
        //LinkedList<Flight> n = new LinkedList<Flight>();
        for(LinkedList<Flight> l : schedule)
        {
            if(l.getFirst().getStart().equals(s))
            {

                for(Flight c : l)
                {
                    n.add(c.getDestination());
                }
            }
        }
        return n;
    }

    public void DepthFirst()
    {
        if(marked.isEmpty())
        {
            LinkedList<String> source = new LinkedList<String>();
            source.add(start);
            marked.add(source);
        }


        //While the stack isn't empty, each element gets popped, then is changed to be marked visited, then is added to the path
        while(!marked.isEmpty())
        {

            LinkedList<String> path = marked.pop();
            String currentCity = path.get(path.size() -1);
            if(currentCity.equals(end))
            {
                allPaths.add(path);
            }
            else{
                for(String s : getNeighbors(currentCity))
                {
                    if(!path.contains(s))
                    {
                        LinkedList<String> newPath = new LinkedList<String>(path);
                        newPath.add(s);
                        marked.push(newPath);
                    }
                }
            }

        }

    }

    public LinkedList<String> displayOutputs(int flightNumber)
    {
        int count = 1;
        LinkedList<String> output = new LinkedList<String>();
        LinkedList<String> totalOutput = new LinkedList<String>();
        LinkedList<LinkedList<Flight>> pathVex = new LinkedList<LinkedList<Flight>>();

        for(int i = 0; i < allPaths.size(); i++)
        {
            LinkedList<Flight> vex = new LinkedList<Flight>();
            for(int j = 0; j < allPaths.get(i).size()-1; j++)
            {
                for(LinkedList<Flight> l : schedule)
                {
                    for(Flight f : l) {
                        if ((f.getStart().equals(allPaths.get(i).get(j))) && f.getDestination().equals(allPaths.get(i).get(j + 1))) {
                            vex.add(f);
                            if(f.getDestination().equals(end))
                            {
                                pathVex.add(vex);
                            }
                        }
                    }
                }
            }
        }

        if(sortPref.equals("T"))
        {
            timeSort(pathVex);
        }
        else{
            costSort(pathVex);
        }

        output.add("Flight " + flightNumber + ": " + start + ", " + end + " (" + (sortPref.equals("T") ? "Time" : "Cost") + ")" );
        int numRoutes = 0;
        for(LinkedList<Flight> fl : pathVex)
        {
            String route = "";
            route = "Path " + count + ": " + start;
            int time = 0;
            double cost = 0;
            for(Flight f: fl)
            {
                route += " -> "+ f.getDestination() + (f.getDestination().equals(end) ? ". " : "");
                time += f.getFlight_time();
                cost += f.getFlight_cost();
            }
            route += "Time: " + time + " Cost: " + String.format("%.2f", cost);
            count++;
            numRoutes++;
            totalOutput.add(route);
        }
        if(numRoutes > 2)
        {
            for(int i = 0; i < 3; i++)
            {
                output.add(totalOutput.get(i));
            }
        }
        else{
            for(String s : totalOutput)
            {
                output.add(s);
            }
        }
        return output;
    }
    public void timeSort(LinkedList<LinkedList<Flight>> path)
    {
        LinkedList<String> s = new LinkedList<String>();
        for(int i = 0; i < path.size(); i++)
        {
            for(int j = 0; j < path.size()- i - 1; j++)
            {
                int time1 = 0;
                int time2 = 0;
                for(int k = 0; k < path.get(j).size(); k++)
                {
                    time1 += path.get(j).get(k).getFlight_time();
                }
                for(int k = 0; k < path.get(j+1).size(); k++)
                {
                    time2 += path.get(j+1).get(k).getFlight_time();
                }
                if(time1 > time2)
                {
                    LinkedList<Flight> temp = path.get(j);
                    path.set(j, path.get(j+1));
                    path.set(j+1, temp);
                }
            }
        }

    }

    public void costSort(LinkedList<LinkedList<Flight>> path)
    {
        LinkedList<String> s = new LinkedList<String>();
        for(int i = 0; i < path.size(); i++)
        {
            for(int j = 0; j < path.size()-i - 1; j++)
            {
                int cost1 = 0;
                int cost2 = 0;
                for(int k = 0; k < path.get(j).size(); k++)
                {
                    cost1 += path.get(j).get(k).getFlight_cost();
                }
                for(int k = 0; k < path.get(j+1).size(); k++)
                {
                    cost2 += path.get(j+1).get(k).getFlight_cost();
                }
                if(cost1 > cost2)
                {
                    LinkedList<Flight> temp = path.get(j);
                    path.set(j, path.get(j+1));
                    path.set(j+1, temp);
                }
            }
        }
    }

}
