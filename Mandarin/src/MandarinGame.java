import EnumTypes.Animal;
import EnumTypes.Color;

import java.util.ArrayList;
import java.util.Scanner;

public class MandarinGame {
    private Pagoda pagoda;
    private Die die;
    private Board board;
    private Player[] players;
    private ArrayList<Figure> figure_extracted;
    private Player winner;

    public MandarinGame() {
        this.pagoda = new Pagoda();
        this.die = new Die();
        this.board = new Board();
        this.figure_extracted = new ArrayList<>();
        this.winner = null;
    }

    public void welcome(){
        System.out.println("BENVENUTO IN MANDARIN\nQuanti giocatori?");
    }

    public void create_players(int n_players,Scanner scanner,Board board){
        players = new Player[n_players];
        for(int i = 0; i <n_players;i++){
            System.out.println("Scegliere il nome: ");
            String name_player = scanner.next();
            players[i] = new Player(name_player,board.getBoard_squares()[0]);
        }
    }

    public void play_round(int n_players,Scanner scanner){
        for (int i = 0; i < n_players; i++){
            players[i].take_turn(die,board,pagoda,scanner);
            if (players[i].getFigures_extracted().size() == 0){
            }else{
                do_auction(players[i].getFigures_extracted(),i,n_players,scanner);
                players[i].getFigures_extracted().removeAll(players[i].getFigures_extracted());
            }
        }
    }

    public void do_auction(ArrayList<Figure> figure_in_palio,int start_index,int n_players,Scanner scanner){
        Auction auction = new Auction(figure_in_palio);
        ArrayList<Player> plyers_in_auction = new ArrayList<>();
        int i = start_index;
        if(i != 0){
            do {
                plyers_in_auction.add(players[i]);
                i++;
            }while (i-n_players != 0);

            for (i = 0; i<start_index; i ++){
                plyers_in_auction.add(players[i]);
            }
        }

        for (i = 0; i<n_players; i ++){
            plyers_in_auction.add(players[i]);
        }

        /** Ora svolgo l' asta vera e propria**/

        while (plyers_in_auction.size() != 1){          // FIXME: mi da errore qui se provo a fare l'asta, nel caso del "no" per questo break
            for (Player player: plyers_in_auction) {
                System.out.println( player.toString()+" Vuoi offrire?  l'offerta è "+auction.getOffer());
                String answer = scanner.next();
                if(answer.equals("si")){
                    System.out.println("Quanto?");
                    int amount = scanner.nextInt();
                    if (amount>auction.getOffer()){
                        auction.add_offer(amount);
                    }else{
                        System.out.println("L'importo non è corretto!");
                    }
                }else if(answer.equals("no")){
                    plyers_in_auction.remove(player);
                          if(plyers_in_auction.size()==1)
                          break;
                }
            }
        }
        Player auction_winner = plyers_in_auction.get(0);
        for (Figure figure:figure_in_palio) {
            auction_winner.add_Figure(figure); //fixme : non vengono assegnate le figure? me ne accorgo dalla stampa
        }
        auction_winner.figuresOfPlayer();
    }


    public boolean chech_players_for_winner(int n_players){
        for (int i = 0; i<n_players; i++){
            if (players[i].check_victory()){
                winner = players[i];
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        MandarinGame game = new MandarinGame();
        Scanner scanner = new Scanner(System.in);
        game.welcome();
        int n_players = scanner.nextInt();
        game.create_players(n_players,scanner,game.board);
        boolean no_winner = true;
        while (no_winner){
            game.play_round(n_players,scanner);
            no_winner = game.chech_players_for_winner(n_players);

        }
        System.out.println("Congratulazioni "+game.winner.toString()+" hai vinto la partita!");
    }


}
