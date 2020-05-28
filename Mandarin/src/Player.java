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
        if (pagoda.thereIs()) {
            figures_extracted.add(pagoda.estraiFigure());
            print_figures_extracted();
            if(pagoda.thereIs()){
                do {
                    boolean scelta_extrazione = choose_extract(scanner);
                    if (scelta_extrazione) {
                        figures_extracted.add(pagoda.estraiFigure());
                        if(!pagoda.thereIs()){
                            System.out.print("NON CI SONO PIU' FIGURE\n");
                            break;
                        }
                        last_index += 1;
                        print_figures_extracted();
                    } else {
                        for (Figure figure : figures_extracted) {
                            card.add_figure_on_card(figure);
                        }
                        figures_extracted.removeAll(figures_extracted);
                        break;
                    }
                } while (figures_extracted.get(last_index).getSide().equals("COPERTA"));
            } else
                System.out.print("NON CI SONO PIU' FIGURE\n");
        }else
            System.out.print("NON CI SONO PIU' FIGURE\n");
    }


    public void add_Figure(Figure figure){
        card.add_figure_on_card(figure);
    }

    public Square getPlayer_location() {
        return player_location;
    }

    public void take_turn(Dice die,Board board,Pagoda pagoda,Scanner scanner, Player[] players){
        die.roll();
        Square oldLocation= player_location;
        boolean superato=false;
        ArrayList<Player> babbi=new ArrayList<>();
        player_location = board.getSquare(player_location,die.getFace_value());

        for (Player player:players) {
            if(board.getValue(oldLocation)<board.getValue(player.getPlayer_location()) &&
                    board.getValue(player_location)>board.getValue(player.getPlayer_location()))
            {
                babbi.add(player);
                System.out.print(this.name.toUpperCase()+" ha superato "+player.toString().toUpperCase()+"\n");
                superato=true;
            }

        }

        if(superato){
            for (Player player:babbi) {
                if(player.getCard().howTrue()!=0)
                    System.out.print("Scegli tra queste figure di "+player.toString()+ " quale prendere\n"+ player.getCard().stampaFigure()+"\n\n");
                // TODO: 28/05/2020 selezione di uno delle figure dell'avversario, magari in un metodo da poterlo usare anche per la vendita
                else
                    System.out.print("fiiga manca una figura ha "+ player.toString().toUpperCase()+"\n\n");
            }
            die.roll();
            player_location = board.getSquare(player_location,die.getFace_value());
        }else{
            player_location.landedOn(this,pagoda,scanner);
        }
        System.out.print(this.name.toUpperCase()+" si trova alla casella numero "+board.getValue(player_location)+"()\n\n");
        // mi serve per sapere dove si trova poi mettero player_location.getPosizione()

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
        printNewFiguresPlayer();
        figuresOfPlayer();
        return false;
    }

    // stampa le figure del giocatore
    public void figuresOfPlayer() {
        if (card.stampaFigure().equals(""+"\n")) {
            System.out.print(name.toUpperCase() + " non aveva altre figure \n\n");
        }else {
            System.out.print(name.toUpperCase() + " ha già "+ card.stampaFigure()+ "\n\n");
        }
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

    public void printNewFiguresPlayer(){
            String animals="";
            for(int i=0; i<figures_extracted.size();i++)
            {
                animals=animals +figures_extracted.get(i).toString()+", ";
            }
           // animals= animals.substring(0,animals.length()-2);
            System.out.print("\n Le figure erano " + animals+"\n");
        }

    public void setIs_offering(boolean is_offering) {
        this.is_offering = is_offering;
    }

    public boolean isIs_offering() {
        return is_offering;
    }

    public void setFigures_extracted(ArrayList<Figure> figures_extracted) {
        this.figures_extracted = figures_extracted;
    }

    @Override
    public String toString() {
        return name;
    }

    public Card getCard() {
        return card;
    }
}
