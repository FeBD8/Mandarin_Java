import java.util.Random;

public class Die {
    private int face_value;

    public void roll(){
        Random random = new Random();
        face_value = random.nextInt(5);
        face_value +=1;
    }

    public int getFace_value() {
        return face_value;
    }
}
