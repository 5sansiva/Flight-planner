import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class Flight_Planner {

    public static void main(String[] args)
    {
        
            Scanner fileEnter = new Scanner(System.in);
            System.out.println("Enter file path for schedule: ");
            String filePath = fileEnter.nextLine();
            Flight_Schedule plan = new Flight_Schedule(filePath);
            plan.setFlightSchedule();
            LinkedList<LinkedList<Flight>> test = plan.getFlights();


        LinkedList<String> out = new LinkedList<String>();
        LinkedList<LinkedList<String>> allPathsOutput = new LinkedList<LinkedList<String>>();
        try
        {
            Scanner getFile = new Scanner(System.in);
            System.out.println("Enter the file path for the desired flight paths: ");

            String filePath1 = getFile.nextLine();
            Scanner input = new Scanner(new File(filePath1));

            int numOfFlights = input.nextInt();
            input.nextLine();

            for(int i = 0; i < numOfFlights; i++)
            {
                String line = input.nextLine();
                String[] data = line.split("\\|");
                RequestFlight r2 = new RequestFlight(data[0], data[1], data[2], test);
                r2.DepthFirst();
                out = r2.displayOutputs(i+1);
                //Checks the output by printing it out to the console
                for(String cities : out)
                {
                    System.out.println(cities);
                }
                allPathsOutput.add(out);
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found. Please try again.");
            throw new RuntimeException(e);
        }


        //Writes all the outputs into a file
        try
        {
            File outputFile = new File("output.txt");
            FileWriter writeFile = new FileWriter(outputFile);
            BufferedWriter bufferedWriter = new BufferedWriter(writeFile);

            for(LinkedList<String> paths : allPathsOutput)
            {
                for(String s : paths){
                    bufferedWriter.write(s);
                    bufferedWriter.newLine();
                }
            }
            bufferedWriter.close();
        }
        catch(IOException e)
        {
            System.out.println("Error writing to the file");

        }

    }

}
