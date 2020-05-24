import EnumTypes.*;

public class Figure {
    private Animal animal;
    private Color color;
    private String side;

    public Figure(Animal animal, Color color) {
        this.animal = animal;
        this.color = color;
    }

    public boolean equals(Figure other_figure){
        return (this.animal == other_figure.animal) && (this.color ==other_figure.color);
    }

    //GETTER

    public Animal getAnimal() {
        return animal;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return  animal.name()+" "+color.name();
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }
}
