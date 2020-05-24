import java.util.ArrayList;
import java.util.HashMap;

public class Auction {
    private ArrayList<Figure> figurea_in_palio;
    private int offer;


    public Auction(ArrayList<Figure> figurea_in_palio) {
        this.figurea_in_palio = figurea_in_palio;
        this.offer = 0;
    }

    public void add_offer(int new_offer){
        offer = new_offer;
    }

    public int getOffer() {
        return offer;
    }
}
