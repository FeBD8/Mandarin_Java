import EnumTypes.Animal;

public class Board {
    private Square[] board_squares;

    public Board() {
        this.board_squares = new Square[36];
        create_squares();
    }

    private void create_squares(){
        int i = 0;
        for (Animal animal:Animal.values()) {
            board_squares[i] = new PagodaSquare(animal);
            board_squares[i+1] = new SordiSquare(animal);
            board_squares[i+2] = new PagodaSquare(animal);
            i+=3;
        }
    }

    public Square setSquare(Square oldloc, int die_value){
        for (int i = 0; i<36; i ++){
            if(board_squares[i] == oldloc){
                int new_indice = i + die_value;
                if (new_indice>35){
                    new_indice -= 36;
                }
                return board_squares[new_indice];
            }
        }
        return null;
    }

    public int getValue(Square square) {
        int i = 0;
        while (i < 36) {
            if (board_squares[i] == square) {
                return i;
            }
            i++;
        }
        return i;
    }

        public Square[] getBoard_squares() {
        return board_squares;
    }
}
