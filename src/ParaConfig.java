//Thabang Sambo
//08 August 2021
//one-dimensional time series data using a median filter
import java.util.*;
import java.io.*;
import java.util.concurrent.RecursiveAction;
 
public class ParaConfig extends RecursiveAction{
    
    //declaring variable for the class
    private static final long serialVersionUID = -7903192583700978127L;
    static int RowVariable;
    static int CNT =0 ;    
    static int cutOff;       
    public int LowPoint;
    static float[][] storeData;
    static String[] storeResults;
    public int upperValue;
    static int colmVariable;
    
    //creating constructors
    public ParaConfig(String textFile){
        readFile(textFile);}
        
    public ParaConfig(int low,int high){
        LowPoint = low;
        upperValue = high;}
   
    public void setCuttOff(int cutOff){
        ParaConfig.cutOff = cutOff;}
        
    public void Calculate(){
        calculate(0,upperValue);}
   
    //reading data from file and adding it to a 2D array
    public void readFile(String textFile){
        try {
            File file = new File(textFile);
            Scanner input = new Scanner(file);
            RowVariable = input.nextInt();            
            colmVariable = input.nextInt();
            storeData = new float[RowVariable][colmVariable];
            storeResults = new String[RowVariable*colmVariable];
            upperValue = RowVariable*colmVariable;

            while (input.hasNext()) {
                for(int i = 0;i<RowVariable;i++){
                for(int k = 0; k<colmVariable;k++){
                storeData[i][k] = Float.parseFloat(input.next());}}}
                input.close();}
                
             catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND");}} 
      
    //method to calculate basins   
    public void calculate(int start, int end){      
        for(int k=start; k<end;k++){
            int hold1 = k % (RowVariable*colmVariable)/colmVariable;
            int hold2 = k % (colmVariable); double offset= 0.01;
            float middle = storeData[hold1][hold2] + (float)offset;
            float minimum  = middle;
            
            try {
                if(minimum >= storeData[hold1-1][hold2+1]){
                   minimum =storeData[hold1-1][hold2+1];}
                    
                if(minimum>=storeData[hold1][hold2+1]){
                   minimum =storeData[hold1][hold2+1];}
                       
                if(minimum>=storeData[hold1+1][hold2+1]){
                   minimum =storeData[hold1+1][hold2+1];}
                   
                 if(minimum>=storeData[hold1-1][hold2]){
                    minimum =storeData[hold1-1][hold2];}
                    
                 if(minimum>=storeData[hold1+1][hold2]){
                    minimum =storeData[hold1+1][hold2];}
                    
                 if(minimum>=storeData[hold1-1][hold2-1]){
                    minimum =storeData[hold1-1][hold2-1];}
                    
                 if(minimum>=storeData[hold1][hold2-1]){
                    minimum =storeData[hold1][hold2-1];}
                    
                 if(minimum>=storeData[hold1+1][hold2-1]){
                    minimum =storeData[hold1+1][hold2-1];}

                 if(middle==minimum){
                    CNT++;
                    storeResults[k] = "Yes";}
                else{
                    storeResults[k] = "No";}}
                     
               catch (Exception e) {
                storeResults[k] = "No";}}}
                
    protected void compute() {
        if(upperValue-LowPoint < cutOff){
            calculate(LowPoint,upperValue);}
            
            else{
            ParaConfig left  = new ParaConfig(LowPoint,(LowPoint+upperValue)/2);
            ParaConfig right  = new ParaConfig((LowPoint+upperValue)/2,upperValue);
            invokeAll(left,right);}}
            
    public void WriteToFile(String textFile){   
         try{ 
            FileWriter fileWriter = new FileWriter(textFile,false);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.printf("%d\r\n",(CNT/2));
            
            for(int k = 0; k<colmVariable*RowVariable; k++){
                if(storeResults[k].equals("Yes")){
                    int hold1 = k % ((RowVariable*colmVariable)/colmVariable);
                    int hold2 = k % (colmVariable);
                    printWriter.printf("%s \r\n",hold1+":x co-ordinate "+hold2+ ":y co-ordinate ");
                }
            }
            printWriter.close();
            }
            catch (IOException e){
            System.out.println("COULD NOT DETECT FOLDER");            
            }
        }
}
