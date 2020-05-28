import EnumTypes.Animal;
import EnumTypes.Color;

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
        sellFigure(scanner,pagoda);
        die.roll();
        Square oldLocation= player_location;
        ArrayList<Player> babbi=new ArrayList<>();
        player_location = board.setSquare(player_location,die.getFace_value());
        boolean superato=surpass(players,oldLocation,board,babbi);

        if(superato){
            for (Player player:babbi) {
                if (player.getCard().howTrue() != 0) {
                    this.stoleFigure(player,scanner);
                }else
                    System.out.print("fiiga manca una figura ha "+ player.toString().toUpperCase()+"\n\n");
            }
            die.roll();
            player_location = board.setSquare(player_location,die.getFace_value());
            figuresOfPlayer();
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

    public boolean isThereFigure(Figure figura, ArrayList<Figure> figures){
        boolean a=false;
        boolean c=false;
        for (Figure f:figures) {
            if (f.getAnimal()==figura.getAnimal()) {
                a = true;
                if (f.getColor() == figura.getColor())
                    c = true;
            }if (a&&c)
                break;

        }
        return a&&c;
    }
    public void moveFigures(Animal animal,Color color,  Player player) {
        Figure figure=new Figure(animal,color);
        player.getCard().removeFgureCard(figure);
        this.getCard().add_figure_on_card(figure);

    }

    public Figure chooseFigura(Scanner scanner){
        System.out.print("Scegli l'animale :  ");
        Animal animal = Animal.valueOf(scanner.next().toUpperCase());
        System.out.print("\nScegli il colore :  ");
        Color color = Color.valueOf(scanner.next().toUpperCase());
        return new Figure(animal,color);
    }

    public void sellFigure(Scanner scanner,Pagoda pagoda)
    {
        if(this.getCard().howTrue() != 0) {
            System.out.print(this.toString().toUpperCase()+ " Vuoi vendere una figura? ");
            if(scanner.next().toLowerCase().equals("si")) {
                System.out.print(this.getCard().stampaFigure());
                boolean noSold=true;
                do{
                    Figure soldFigure= chooseFigura(scanner);
                    if (isThereFigure(soldFigure, this.getCard().getFigures())) {
                        this.getCard().removeFgureCard(soldFigure);
                        this.add_yuhan(200);
                        pagoda.getPag_figures().add(soldFigure); // non ho testato se funziona
                        noSold=false;
                    }
                    else{
                        System.out.print(this.toString().toUpperCase()+" non ha la figura selezionata, riprova" + "\n");
                    }
                }while (noSold);

            }
        }
    }

    public void stoleFigure(Player robbed,Scanner scanner){
        System.out.print("Scegli tra queste figure di " + robbed.toString() + " quale prendere\n" + robbed.getCard().stampaFigure() + "\n\n");
        boolean noStolen = true;
        do {
            Figure f= chooseFigura(scanner);
            if (isThereFigure(f, robbed.getCard().getFigures())) {
                this.moveFigures(f.getAnimal(), f.getColor(), robbed);
                noStolen=false;
            }else{
                System.out.print(robbed.toString().toUpperCase()+" non ha la figura selezionata, riprova" + "\n");
            }
        } while (noStolen);
    }

    public boolean surpass(Player[] players, Square oldLocation, Board board ,ArrayList<Player> babbi){
        boolean superato=false;
        for (Player player:players) {
            if(board.getValue(oldLocation)<board.getValue(player.getPlayer_location()) &&
                    board.getValue(player_location)>board.getValue(player.getPlayer_location()))
            {
                babbi.add(player);
                System.out.print(this.name.toUpperCase()+" ha superato "+player.toString().toUpperCase()+"\n");
                superato=true;
            }
        }
        return superato;
    }
}

