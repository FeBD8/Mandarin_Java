import EnumTypes.Animal;

import java.util.Scanner;

public abstract class Square {
    private Animal animal;

    public Square(Animal animal) {
        this.animal = animal;
    }

    public abstract void landedOn(Player player, Pagoda pagoda,Scanner scanner);

    public abstract String getPosizione();
}
