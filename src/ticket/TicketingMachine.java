package ticket;

public class TicketingMachine {

    // all the states (starts with state Ready)
    private boolean ready = true;
    private boolean acceptingCoin = false;
    private boolean done = false;

    // ticketing machine variables
    private String curStation;
    private int curAmount = 0;
    private int price;
    private PriceTable priceTable;
    private View view;

    public TicketingMachine(PriceTable priceTable, View view, String curStation) {
        this.priceTable = priceTable;
        this.curStation = curStation;
        this.view = view;
        this.view.displayCurrentStation(); // entry activity for the state Ready
    }

    // transition choose station
    public void chooseStation(String destStation) {

        // must be in state ready to choose station
        if (ready) {

            // activity for this transition
            price = priceTable.calPrice(curStation, destStation);
            view.displayPrice(destStation);

            // change to state paying
            acceptingCoin = true;
            ready = false;

            // entry activity of state paying
            view.displayRemainingAmount();
        }
    }

    // transition insert coin
    public void insertCoin(int amount) {

        if (acceptingCoin) {
            curAmount += amount;

            // guard 3 cases
            if (curAmount < price) {
                view.displayRemainingAmount();  // entry activity for state paying
            } else { // curAmount >= price
                if (curAmount > price) {
                    returnCoins();
                }
                // change to state done
                acceptingCoin = false;
                done = true;
            }
        }
    }

    // transition retrieving ticket
    public void retrieveTicket() {

        if (done) {
            dispenseTicket(); // activity for this transition นี้

            // change to state Reader
            ready = true;
            done = false;
            view.displayCurrentStation(); // entry activity for state Ready
            curAmount = 0 ;
        }
    }
    // help methods to be used
    private void dispenseTicket() {

    }
    private void returnCoins() {

    }
}