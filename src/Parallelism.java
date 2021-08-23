//Thabang Sambo
//08 August 2021
//one-dimensional time series data using a median filter
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;


public class Parallelism{
    static final ForkJoinPool fjPool = new ForkJoinPool();
    public static void main(String[] args) {
    
       //Parallel  method getting a file
        ParaConfig parallelism = new ParaConfig("sample1.txt");// input file name that is stored on the data folder from data that I created
        Scanner input = new Scanner(System.in);
        int SQLCutOff = parallelism.upperValue; // we get the value and set the cutoff
        double speedup;

        System.out.print("Input sequential cutoff that is less than: "+ SQLCutOff+": " );
        int cutOff = input.nextInt();
        parallelism.setCuttOff(cutOff);
        System.gc();

        long beginCount = System.currentTimeMillis();
        fjPool.invoke(parallelism);
        long stopCount = System.currentTimeMillis();
        double T2 =(((stopCount-beginCount)* 1.0 )/1000);
        input.close();
    
       //here we are using the Sequential process to calculate the interval
        ParaConfig sequential = new ParaConfig("sample1.txt");// input file name that is stored on the data folder from data that I created  
        sequential.setCuttOff(cutOff);
        System.gc();
        beginCount = System.currentTimeMillis();
        sequential.Calculate();
        stopCount = System.currentTimeMillis();
        double T1 =(((stopCount-beginCount)* 1.0 )/1000);
        speedup = T2/T1;
        
        //Produce the amount of time it took each program to complete the task 
        System.out.println("The Parallel Progam ran: "+T1+ " milliseconds");
        System.out.println("The Sequential Program ran: "+T2+" milliseconds");        
        System.out.println("SpeedUp ratio is: "+speedup);
        
        //data is stored on the file
        parallelism.WriteToFile("./results/" + "Result.txt");}
}
