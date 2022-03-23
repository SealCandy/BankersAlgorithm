package cz.osu;

import java.util.Arrays;
import java.util.Scanner;

public class Banker {
    //Attributes that banker needs
    // processTime can be also renamed as = maximumProcessTime
    private  int processTime;
    //array for where the values will be saved for easier use
    private final int[] usersNeeds;
    //currentUsers is used for knowing how many values are currently saved
    private int currentUsers = 0;

    Scanner sc;

    public Banker(int maxUsers) {
        usersNeeds = new int[maxUsers];
        sc = new Scanner(System.in);
    }
//Method for adding values to the userNeeds array
    public void setProcessTime(){
        boolean goodValue = false;
        int inputValue;
        while (!goodValue){
            System.out.println("Input the maximum process time(how much time can processor can give. Don't input zero or two):");
            inputValue = sc.nextInt();
            if(inputValue <= 2){
                System.out.println("Impossible process time. Try again! At least more than 2");
            }
            else{
                processTime = inputValue;
                goodValue = true;
            }
        }
    }
    public void addUser(){
        int requiredProcessTime;
        boolean maxCapacity = false;
        while (!maxCapacity){
            System.out.println("Input process time for user" + (currentUsers +1) + ":");
            requiredProcessTime=sc.nextInt();
            if(requiredProcessTime <= processTime){
                usersNeeds[currentUsers] =  requiredProcessTime;
                currentUsers++;
                if(currentUsers == usersNeeds.length){
                    maxCapacity = true;
                }
            }
            else {
                System.out.println("Cannot create user due to having too much required process time");
            }
        }
    }
//Basically the main method which uses my version of banker algorithm in order to reduce the values to zero in the way the bankers algorithm would
    public void solveTimeProblems(){
        //lowestValue is a local variable that saves the results from the method lowestValueChecker in order to save time
        int lowestValue = lowestValueChecker();
        printAllUsers();
        //while loop that checks the current sum of the values if it is zero the loop will end
        while (Arrays.stream(usersNeeds).sum() != 0){
            //condition for the case if the processTime is the same as the value contained in the array
            if(usersNeeds[lowestValue] == processTime){
                usersNeeds[lowestValue] = 0;
                lowestValue = lowestValueChecker();
                printAllUsers();
            }
            //condition if the value in array is lower than processTime
            else if(usersNeeds[lowestValue] < processTime){
                //local variable for remainingProcessTime that will be used in the upcoming code
                int remainingPcsTime = processTime;
                // local variable used for a while loop
                boolean pcsTrue = false;
                //loop that is used for using the remainingPcsTime
                while (!pcsTrue){
                    //condition for when the sum of the used array is zero in order to end loop
                    if(Arrays.stream(usersNeeds).sum() == 0){
                        pcsTrue = true;
                    }
                    //condition for when the remainingPcsTime is zero the loop ends
                    else if (remainingPcsTime == 0){
                        pcsTrue = true;
                    }
                    //condition when the value in the array is the same as remainingPcsTime
                    else if(usersNeeds[lowestValue] == remainingPcsTime){
                        printAllUsers(lowestValue);
                        usersNeeds[lowestValue] = 0;
                        pcsTrue = true;
                    }
                    //condition when the value in the array is higher than remainingPcsTime
                    else if(usersNeeds[lowestValue] > remainingPcsTime){
                        usersNeeds[lowestValue] -= remainingPcsTime;
                        printAllUsers(lowestValue,remainingPcsTime);
                        pcsTrue = true;
                    }
                    //condition when the value in the array is still lower than remainingPcsTime
                    else {
                        remainingPcsTime -= usersNeeds[lowestValue];
                        printAllUsers(lowestValue);
                        usersNeeds[lowestValue] = 0;
                        lowestValue = lowestValueChecker();
                    }
                }
            }

        }
        printAllUsers();
        System.out.println("Job done");
        sc.nextLine();
        sc.nextLine();

    }

//method for finding the smallest value except zero
    private int lowestValueChecker(){
        int ret = 0;
        for (int i = 0; i < currentUsers; i++) {
            if (usersNeeds[ret] > usersNeeds[i] && usersNeeds[ret] != 0 && usersNeeds[i] != 0 || usersNeeds[ret] == 0){
                ret = i;
            }
        }
        return ret;
    }
//method used for writing out all the currentUsers and their values
    private void printAllUsers(){
        for (int i = 0; i < currentUsers; i++) {
            System.out.println("User"+(i+1) + " Req. process time: " + usersNeeds[i]);
        }
        System.out.println("Remaining process time: " + processTime);
        System.out.println();
    }

    private void printAllUsers(int deletedValue){
        for (int i = 0; i < currentUsers; i++) {
            if(i == deletedValue){
                System.out.println("User"+(i+1) + " Req. process time: " + usersNeeds[i] + "-" + usersNeeds[deletedValue]);
            }
            else{
                System.out.println("User"+(i+1) + " Req. process time: " + usersNeeds[i]);
            }
        }
        System.out.println("Remaining process time: " + (processTime - usersNeeds[deletedValue]));
        System.out.println();
    }
    private void printAllUsers(int deletedValue,int remainingPcs){
        for (int i = 0; i < currentUsers; i++) {
            if(i == deletedValue){
                System.out.println("User"+(i+1) + " Req. process time: " + (usersNeeds[i] + remainingPcs) + "-" + remainingPcs);
            }
            else{
                System.out.println("User"+(i+1) + " Req. process time: " + usersNeeds[i]);
            }
        }
        System.out.println("Remaining process time: " + 0);
        System.out.println();
    }
}
