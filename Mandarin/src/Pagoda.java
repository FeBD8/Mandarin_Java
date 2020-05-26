import EnumTypes.Animal;
import EnumTypes.Color;

import java.util.ArrayList;
import java.util.Random;

public class Pagoda {
    private ArrayList<Figure> pag_figures;

    public Pagoda() {
        this.pag_figures = new ArrayList<>();
        create_pag_figures();
    }

    public void create_pag_figures(){
        for (Color color:Color.values()) {
            for (Animal animal:Animal.values()) {
                pag_figures.add(new Figure(animal,color));
            }
        }
    }

    public Figure estraiFigure(){
        Random random = new Random();
        Figure extracted = pag_figures.get(random.nextInt(pag_figures.size()));
        int side_choice = random.nextInt(2);
        if(side_choice == 0){
            extracted.setSide("COPERTA");
        }else {
            extracted.setSide("SCOPERTA");
        }
        pag_figures.remove(extracted);
        return extracted;
    }


    public boolean thereIs(){
        if (pag_figures.size() == 0)
            return false;
        return true;
    }
}
