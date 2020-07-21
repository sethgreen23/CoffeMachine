

import java.util.Scanner;

public class CoffeeMachine {
    //public static int water,milk,coffeBeans,cups,money;
    //public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {


        //stage5
        //instatiate the machine object
        Scanner scanner = new Scanner(System.in);
        Machine machine = new Machine(400,540,120,9,550);
        //we need to loop throw till we exit
        while(machine.isWorking()){
            machine.execute(scanner.next());
        }
    }

    //stage5
    //here is the machine states 
    enum MachineState{
        EXIT,BUY,MAIN,FILL_WATTER,FILL_MILK,FILL_COFFE,FILL_CUPS
    }
    //here the beverage enumeration 
    //we specify the variety of beverage and the ingredient needed to create them
    enum Beverage{
        EXPRESSO(250,0,16,4),
        LATTE(350,75,20,7),
        CAPPUCINO(200,100,12,6);
        private final int water;
        private final int milk;
        private final int coffeBeans;
        private final int cost;
        Beverage(int water,int milk,int coffeBeans,int cost){
            this.water=water;
            this.milk=milk;
            this.coffeBeans=coffeBeans;
            this.cost = cost;
        }
        int neededWater(){
            return water;
        }
        int neededMilk(){
            return milk;
        }
        int neededCoffeBeans(){
            return coffeBeans;
        }
        int getCost(){
            return cost;
        }
    }
    static class Machine{
        private int water;
        private int milk;
        private int coffeBeans;
        private int cups;
        private int money;
        //the machine have state in every step you use it for
        MachineState state;
        public Machine (int water,int milk,int coffeBeans,int cups,int money){
            this.water=water;
            this.milk=milk;
            this.coffeBeans=coffeBeans;
            this.cups=cups;
            this.money=money;
            //here we call the main state 
            setMainState();
        }
        //setMainState
        private void setMainState() {
        	//this is the message that we get when we are starting a new opeation
            System.out.println("\nWrite action (buy, fill, take, remaining, exit):");
            state=MachineState.MAIN;
        }

        //is working method
        boolean isWorking(){
        	//this is the condition that make us exit from the machine
            return state!=MachineState.EXIT;
        }
        //execute method
        public void execute(String input){
        	//depending on the state we will perform somthing 
        	//and it is the opening for getting input from the user
        	//we get input when we are in the main menu
        	//when we fill the machine with splies
        	//when we buy
        	//when we exit
            switch (state){
                case BUY:
                    handleBuying(input);
                    setMainState();
                    break;
                case MAIN:
                    setState(input);
                    break;
                case FILL_WATTER:
                    water += Integer.parseInt(input);
                    state = MachineState.FILL_MILK;
                    System.out.println("\nWrite how many ml of milk do you want to add:");
                    break;
                case FILL_MILK:
                    milk+=Integer.parseInt(input);
                    state=MachineState.FILL_COFFE;
                    System.out.println("\nWrite how many grams of coffee beans do you want to add:");
                    break;
                case FILL_COFFE:
                    coffeBeans+=Integer.parseInt(input);
                    state=MachineState.FILL_CUPS;
                    System.out.println("\nWrite how many disposable cups of coffee do you want to add:");
                    break;
                case FILL_CUPS:
                    cups+=Integer.parseInt(input);
                    setMainState();
                    break;
                case EXIT:
                    break;
            }
        }

        private void setState(String command) {
        	//this is our router
        	//with every select in the main menu we get a state
            switch(command){
                case "buy":
                    System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:");
                    state=MachineState.BUY;
                    break;
                case "fill":
                    state=MachineState.FILL_WATTER;
                    System.out.println("\nWrite how many ml of water do you want to add:");
                    break;
                case "take":
                    take_handler();
                    setMainState();
                    break;
                case "remaining":
                    showCurrentState();
                    setMainState();
                    break;
                case "exit":
                    state=MachineState.EXIT;
                    break;
            }
        }
        //show the state of the machine from suplies
        private void showCurrentState(){
            System.out.println("\nThe coffee machine has:");
            System.out.println(water+" of water");
            System.out.println(milk+" of milk");
            System.out.println(coffeBeans+" of coffee beans");
            System.out.println(cups+" of disposable cups");
            System.out.println(money+" of money");
            System.out.println();
        }
        //this take care of showing what we got when the 
        //employer take way the money from the machine
        private void take_handler() {
            System.out.println("\nI gave you $"+money);
            money=0;
        }
        
        //this handle the action of buying
        private void handleBuying(String input) {
            Beverage drink=null;
            switch(input){
                case "1":
                    drink=Beverage.EXPRESSO;
                    break;
                case "2":
                    drink=Beverage.LATTE;
                    break;
                case "3":
                    drink=Beverage.CAPPUCINO;
                    break;
                case "back":
                    setMainState();
                    return;
            }
            checkOrder(drink);


        }
        //here a method that add the cost of the beverage
        //to the global cash
        private void getPayment(int cost) {
            money+=cost;
        }
        //this method take care of checking if we have available 
        //supply for the order or not
        //if it exist then we make the coffe and we take the money
        //if not we tell that we have shortage in a specific ingredient
        private void checkOrder(Beverage drink) {
            if(water<=drink.neededWater()){
                System.out.println("\nSorry, not enough water!");
                return;
            }
            if(milk<=drink.neededMilk()){
                System.out.println("\nSorry, not enough milk!");
                return;
            }
            if(coffeBeans<=drink.neededCoffeBeans()){
                System.out.println("\nSorry, not enough coffee beans!");
                return;
            }
            if(cups<=1){
                System.out.println("\nSorry, not enough disposable cups!");
                return;
            }
            water-=drink.neededWater();
            milk-=drink.neededMilk();
            coffeBeans-=drink.neededCoffeBeans();
            cups-=1;
            getPayment(drink.getCost());
            System.out.println("\nI have enough resources, making you a coffee!");
        }
    }



}

