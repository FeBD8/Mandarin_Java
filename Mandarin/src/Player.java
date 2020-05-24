import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    private String name;
    private Card card;
    private Square player_location;
    private int yuhan;
    private ArrayList<Figure> figures_extracted;
    private boolean is_offering;

    public Player(String name ,Square player_location) {
        this.name = name;
        this.player_location = player_location;
        this.card = new Card();
        this.yuhan = 3000;
        this.is_offering = true;
        this.figures_extracted = new ArrayList<>();
    }

    public void add_yuhan(int amount){
        yuhan += amount;
    }

    public void estrazione(Pagoda pagoda,Scanner scanner){
        int last_index = 0;
        figures_extracted.add(pagoda.estraiFigure());
        print_figures_extracted();
        do {
            boolean scelta_extrazione = choose_extract(scanner);
            if (scelta_extrazione) {
                figures_extracted.add(pagoda.estraiFigure());
                last_index +=1;
                print_figures_extracted();
            }else {
                for (Figure figure:figures_extracted) {
                    card.add_figure_on_card(figure);
                }
                figures_extracted.removeAll(figures_extracted);
                break;
            }
        }while (figures_extracted.get(last_index).getSide() == "COPERTA");
    }


    public void add_Figure(Figure figure){
        card.add_figure_on_card(figure);
    }

    public Square getPlayer_location() {
        return player_location;
    }

    public void take_turn(Die die,Board board,Pagoda pagoda,Scanner scanner){
        die.roll();
        player_location = board.getSquare(player_location,die.getFace_value());
        player_location.landedOn(this,pagoda,scanner);

    }

    public boolean check_victory(){
        if (card.check_allTrue()){
            return true;
        }
        return false;
    }

    public boolean choose_extract(Scanner scanner){
        System.out.println(this.name.toUpperCase()+" vuoi estrarre ancora?");
        String choice = scanner.next();
        if (choice.equals("si")){
            return true;
        }
        figuresOfPlayer();
        return false;
    }

    public void figuresOfPlayer() { // stampa le figure del giocatore
        String animals="";
        for(int i=0; i<figures_extracted.size();i++)
        {
            animals=animals +", "+figures_extracted.get(i).toString();
        }
        System.out.print(this.name+ " ha " + animals+"\n");
    }

    public ArrayList<Figure> getFigures_extracted() {
        return figures_extracted;
    }

    public void print_figures_extracted(){
        for (Figure figure:figures_extracted) {
            if(figure.getSide().equals("COPERTA")){
                System.out.println("- (COPERTA)");
            }else {
                System.out.println("- "+figure.toString());
            }
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
