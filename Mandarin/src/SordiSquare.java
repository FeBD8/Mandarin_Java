import EnumTypes.Animal;

import java.util.Scanner;

public class SordiSquare extends Square{

    public SordiSquare(Animal animal) {
        super(animal);
    }

    @Override
    public void landedOn(Player player, Pagoda pagoda,Scanner scanner) {
        player.add_yuhan(100);
        System.out.print(player.toString().toUpperCase()+" ha preso 100 Yuhan ( i sordi fanno sempre bbene)\n\n");
    }

    public String getPosizione(){
        return toString()+ " sordiSquare"; //fixme vorrei sapere su quale animale sono? come faccio
    }
}
