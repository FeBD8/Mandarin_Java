import EnumTypes.Animal;

import java.util.Scanner;

public class SordiSquare extends Square{

    public SordiSquare(Animal animal) {
        super(animal);
    }

    @Override
    public void landedOn(Player player, Pagoda pagoda,Scanner scanner) {
        player.add_yuhan(100);
    }
}
