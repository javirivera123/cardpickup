package CardPickup;
import java.util.ArrayList;
import java.util.Random;

/**
 * Some important variables inherited from the Player Class:
 * protected Node[] graph; //Contains the entire graph
 * protected Hand hand; //Contains your current hand (Use the cardsHole array list)
 * protected int turnsRemaining; //Number of turns before the game ends
 * protected int currentNode; //Your current location
 * protected int oppNode; //Opponent's current position
 * protected Card oppLastCard;	//Opponent's last picked up card
 *
 * Important methods inherited from Player Class:
 * Method that is used to determine if a move is valid. This method should be used to help players
 * determine if their actions are valid. GameMaster has a local copy of this method and all the
 * required variables (such as the true graph), so manipulating the variables to turn a previously
 * invalid action in to a "valid" one will not help you as the GameMaster will still see the action
 * as invalid.
 * protected boolean isValidAction(Action a); //This method can be used to determine if an action is valid
 *
 * NOTE TO STUDENTS: The game master will only tell the player the results of your and your opponents actions.
 * It will not update your graph for you. That is something we left you to do so that you can update your
 * graphs, opponent hand, horoscope, etc. intelligently and however you like.

 *
 * @author Marcus Gutierrez
 * @version 04/15/2015
 */
public class danBilzerian extends Player {
    protected final String newName = "DanBilzerian"; //Overwrite this variable in your player subclass

    /**
     * Do not alter this constructor as nothing has been initialized yet. Please use initialize() instead
     */
    public danBilzerian() {
        super();
        playerName = newName;
    }

    public void initialize() {
        //WRITE ANY INITIALIZATION COMPUTATIONS HERE
    }

    /**
     * THIS METHOD SHOULD BE OVERRIDDEN if you wish to make computations off of the opponent's moves.
     * GameMaster will call this to update your player on the opponent's actions. This method is called
     * after the opponent has made a move.
     *
     * @param opponentNode     Opponent's current location
     * @param opponentPickedUp Notifies if the opponent picked up a card last turn
     * @param c                The card that the opponent picked up, if any (null if the opponent did not pick up a card)
     */
    protected void opponentAction(int opponentNode, boolean opponentPickedUp, Card c) {
        oppNode = opponentNode;
        if (opponentPickedUp)
            oppLastCard = c;
        else
            oppLastCard = null;
    }

    protected void evaluateGraph() {
        HandEvaluator he; // A reference to HandEval class

        he = new HandEvaluator(); // Creating class B

        //b.doSomething();  // Calling a method contained in class B from class A

        for (int i = 0; i < graph.length; i++) {
            ArrayList<Card> possibleHand = new ArrayList<Card>();  //define possible Hand size of hole size +1 (room for each card at node) to evaluate

            for(int j =0; j<this.hand.cardsHole.size(); j++)
                possibleHand.add(this.hand.getHoleCard(j)); //fill with current cards in hand

            for (int k = 0; k < graph[i].getPossibleCards().size(); k++) { //for each possible card in node
                     possibleHand.add(graph[k].getPossibleCards().get(k));
                switch (possibleHand.size()) {
                    case 1:
                        he.rankHand(possibleHand.get(0));
                        break;
                    case 2:
                        he.rankHand(possibleHand.get(0), possibleHand.get(1));
                        break;
                    case 3:
                        he.rankHand(possibleHand.get(0), possibleHand.get(1), possibleHand.get(2));
                        break;
                    case 4:
                        he.rankHand(possibleHand.get(0), possibleHand.get(1), possibleHand.get(2), possibleHand.get(3));
                        break;
                    case 5:
                        he.rankHand(possibleHand.get(0), possibleHand.get(1), possibleHand.get(2), possibleHand.get(3), possibleHand.get(4));
                        break;
                    default:
                        System.out.println("Invalid size of rank");
                        break;
                }



                    }//end for each in hand
                }
            }





    /**
     * THIS METHOD SHOULD BE OVERRIDDEN if you wish to make computations off of your results.
     * GameMaster will call this to update you on your actions.
     *
     * @param currentNode Opponent's current location
     * @param c           The card that you picked up, if any (null if you did not pick up a card)
     */
    protected void actionResult(int currentNode, Card c) {
        this.currentNode = currentNode;
        if (c != null)
            addCardToHand(c);
    }

    //The suit of the Card.  Clubs = 1, Diamonds = 2, Hearts = 3, Spades = 4
    public void getNameOfSuit(int suit) {
        switch (suit) {
            case 1:
                System.out.println("Dominant Suit in My Hand is: Clubs");
                break;

            case 2:
                System.out.println("Dominant Suit in My Hand is: Diamonds");
                break;

            case 3:
                System.out.println("Dominant Suit in My Hand is: Hearts");
                break;

            case 4:
                System.out.println("Dominant Suit in My Hand is: Spades");
                break;

            default:
                System.out.println("No dominant card");
                break;

        }
    }//end of getnamesuit

    public Action makeAction() {
        int dominantSuit = getDominant();  //evaluate dominant suit in hand
        getNameOfSuit(dominantSuit);// print name of dominant suit in hand

        evaluateGraph();


                int maxIndex = 0;
              /*  for (int k = 1; k < sums.length; k++)
                    if (sums[maxIndex] < sums[k])
                        maxIndex = k;*/
                if (graph[currentNode].getNeighbor(maxIndex).getPossibleCards().size() == 0) {
                    Random r = new Random();
                    //int neighbor = graph[currentNode].getNeighbor(maxIndex).getNodeID();
                    int neighbor = graph[currentNode].getNeighbor(r.nextInt(graph[currentNode].getNeighborAmount())).getNodeID();
                    return new Action(ActionType.MOVE, neighbor);
                }
                int neighbor = graph[currentNode].getNeighbor(maxIndex).getNodeID();
                return new Action(ActionType.PICKUP, neighbor);

        }
    private int getDominant() {
        int[] suits = new int[5];
        int dominantSuit = 1;
        //int[] sameDominance = new int[5];

        ArrayList<Card> myHand = this.hand.getCardHole(); // list of cards in my hand
        if (myHand.size() != 0) {
            for (int j = 0; j < myHand.size(); j++)
                suits[myHand.get(j).getSuit()] += 1;  //marking which suits are present in hand
        }


        for (int i = 1; i < suits.length; i++) {
            if (suits[i] > suits[dominantSuit])
                dominantSuit = i;

        }
        return dominantSuit;
    } //end getDominant() class

}//end danBilzerian class
