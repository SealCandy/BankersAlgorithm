package cz.osu;

public class Main {

    public static void main(String[] args) {
        //Creating an instance of the class banker while adding values into it
        Banker banker = new Banker(4);
        banker.setProcessTime();
        banker.addUser();
        //Method in Banker that uses banker algorithm to solve the problem
        banker.solveTimeProblems();
    }
}
