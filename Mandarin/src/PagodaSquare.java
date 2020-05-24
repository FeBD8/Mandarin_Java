import EnumTypes.Animal;

import java.util.Scanner;

public class PagodaSquare extends Square{
    public PagodaSquare(Animal animal) {
        super(animal);
    }

    @Override
    public void landedOn(Player player,Pagoda pagoda,Scanner scanner) {
        player.estrazione(pagoda,scanner);
    }
}
