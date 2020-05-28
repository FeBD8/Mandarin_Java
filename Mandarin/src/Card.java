import EnumTypes.*;

import java.util.ArrayList;

public class Card {
    private Figure[][] figures;
    private boolean[][] bool_figures;

    public Card() {
        this.figures = new Figure[6][12];
        this.bool_figures = new boolean[6][12];
        create_card();
    }

    private void create_card(){
        int i = 0;
        int j = 0;
        for (Color color:Color.values()) {
            for (Animal animal:Animal.values()) {
                figures[i][j] = new Figure(animal,color);
                //bool_figures[i][j] = false;
                j++;
            }
            j = 0;
            i++;
        }
    }

    public void add_figure_on_card(Figure figure){
        for( int i = 0; i < 6; i++){
            for( int j = 0; j < 12; j++){
                if (figures[i][j].equals(figure)){
                    bool_figures[i][j] = true;
                    break;
                }
            }
        }
    }

    public boolean check_allTrue(){
        int n_true = 0;
        for (int i=0;i<6;i++){
            for (int j = 0; j<12; j++){
                if (bool_figures[i][j]){
                    n_true +=1;
                }
            }
        }
        if (n_true == 72){
            return true;
        }
        return false;
    }

    public int howTrue(){
        int n_true = 0;
        for (int i=0;i<6;i++){
            for (int j = 0; j<12; j++){
                if (bool_figures[i][j]){
                    n_true +=1;
                }
            }
        }
        return n_true;
    }

    public String stampaFigure(){
        String animals= "";
        for( int i = 0; i < 6; i++){
            for( int j = 0; j < 12; j++){
                if (bool_figures[i][j]){
                    animals= animals + figures[i][j].toString() + ", ";
                }
            }
        }
        if(!animals.equals(""))
            animals=animals.substring(0, animals.length()-2);

        return animals + "\n";
    }

    public ArrayList<Figure> getFigures()
    {
        ArrayList<Figure> figure=new ArrayList<>();
        for( int i = 0; i < 6; i++){
            for( int j = 0; j < 12; j++){
                if (bool_figures[i][j]){
                    figure.add(figures[i][j]);
                }
            }
        }
        return figure;
    }
    public void removeFgureCard(Figure figure){
        for( int i = 0; i < 6; i++){
            for( int j = 0; j < 12; j++){
                if (figures[i][j].equals(figure)){
                    bool_figures[i][j] = false;
                    break;
                }
            }
        }
    }
}
