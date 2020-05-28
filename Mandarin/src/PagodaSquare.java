import EnumTypes.Animal;

import java.util.Scanner;

public class PagodaSquare extends Square{
    public PagodaSquare(Animal animal) {
        super(animal);
    }

    @Override
    public void landedOn(Player player,Pagoda pagoda,Scanner scanner) {
        if(pagoda.thereIs())
            player.estrazione(pagoda,scanner);
        else
            System.out.print("NON CI SONO PIU' FIGURE\n");
    }

    public String getPosizione(){
        return toString()+ " pagodaSquare"; //fixme vorrei sapere su quale animale sono? come faccio
    }
}
