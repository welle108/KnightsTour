import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class KnightsTour {
    private static Position init;
    private static boolean running = true;
    private static int full;
    private static int dimen;
    private  static int x;
    private static int y;




    public static void main(String[] args) {
        int[][]mytour;
        Options options = new Options();
        options.addOption("h",false, "Enables heuristic knights tour with Warnsdorf algorithm");
        CommandLineParser parser = new DefaultParser();
        try{
            CommandLine cmd = parser.parse(options, args);
            if(cmd.hasOption("h")){
                System.out.println("DOIN' HEURISTICS");
            dimen = Integer.parseInt(args[1]);
            x = Integer.parseInt(args[2]);
            y = Integer.parseInt(args[3]);
            full = new Integer(dimen*dimen);
            init = new Position(x,y);
            //Run heuristic analysis with Warnsdorf
                mytour = heuristicTour();
                printTour(mytour);

            }else{
            dimen = Integer.parseInt(args[0]);
            //This is ugly but was the only way to assign this value
            full = new Integer(dimen*dimen);
            x = Integer.parseInt(args[1]);
            y = Integer.parseInt(args[2]);
            init = new Position(x,y);
            //Brute force approach
            mytour = bruteTour();
            printTour(mytour);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public static int[][] bruteTour(){
        ArrayList<Position> current_tour = new ArrayList<>();
        current_tour.add(init);
        int[][] visited = new int[dimen][dimen];
        for(int[] row : visited){
            Arrays.fill(row, 0);
        }
        visited[init.getX()][init.getY()] = 1;
        bruteBacktrack(current_tour, visited);
         int[][] tour = new int[dimen][dimen];
         for(int i = 0; i < full; i++){
             Position cur = current_tour.get(i);
             tour[cur.getX()][cur.getY()] = i+1;
         }

         return tour;
    }

    public static int[][] heuristicTour(){
        ArrayList<Position> current_tour = new ArrayList<>();
        current_tour.add(init);
        int[][] visited = new int[dimen][dimen];
        for(int[] row : visited){
            Arrays.fill(row, 0);
        }
        visited[init.getX()][init.getY()] = 1;
        warnBacktrack(current_tour, visited);
        int[][] tour = new int[dimen][dimen];
        for(int i = 0; i < full; i++){
            Position cur = current_tour.get(i);
            tour[cur.getX()][cur.getY()] = i+1;
        }

        return tour;
    }

    private static void bruteBacktrack(ArrayList<Position> tour, int[][]visited){

        if(tour.size() >= full){
            System.out.println("SOLVED");
            running = false;
        }
        else{
            List<Position> moves = checkMoves(tour, visited);
            for(Position p : moves){
                tour.add(p);
                visited[p.getX()][p.getY()] = 1;
                bruteBacktrack(tour, visited);
                if(!running){
                    return;
                }
                else{
                    tour.remove(p);
                    visited[p.getX()][p.getY()] = 0;
                }
            }
        }
    }

    private static void warnBacktrack(ArrayList<Position> tour, int[][]visited){

        if(tour.size() >= full){
            System.out.println("SOLVED");
            running = false;
        }
        else{
            Position next_move = nextMoveWarnsdorff(tour, visited);

            tour.add(next_move);
            visited[next_move.getX()][next_move.getY()] = 1;
            bruteBacktrack(tour, visited);
            if(!running){
                return;
            }
            else{
                tour.remove(next_move);
                visited[next_move.getX()][next_move.getY()] = 0;
            }

        }
    }


    private static List<Position> checkMoves(ArrayList<Position> tour, int[][] visited){
        ArrayList<Position> possible_moves = new ArrayList<>();
        Position current_position = tour.get(tour.size()-1);
        int x = current_position.getX();
        int y = current_position.getY();

        if(x-2 >= 0 && y+1 < dimen && visited[x-2][y+1] != 1) {
            possible_moves.add(new Position(x - 2, y + 1));
        }
        if(x-2 >= 0 && y-1 >= 0 && visited[x-2][y-1] != 1){
            possible_moves.add(new Position(x-2,y-1));
        }
        if(x-1 >= 0 && y+2 < dimen && visited[x-1][y+2] != 1){
            possible_moves.add(new Position(x-1,y+2));
        }
        if(x-1 >= 0 && y-2 >= 0 && visited[x-1][y-2] != 1){
            possible_moves.add(new Position(x-1,y-2));
        }
        if(x+1 < dimen && y-2 >= 0 && visited[x+1][y-2] != 1){
            possible_moves.add(new Position(x+1,y-2));
        }
        if(x+1 < dimen && y+2 < dimen && visited[x+1][y+2] != 1){
            possible_moves.add(new Position(x+1,y+2));
        }
        if(x+2 < dimen && y-1 >= 0 && visited[x+2][y-1] != 1){
            possible_moves.add(new Position(x+2,y-1));
        }
        if(x+2 < dimen && y+1 < dimen && visited[x+2][y+1] != 1){
            possible_moves.add(new Position(x+2,y+1));
        }
        return possible_moves;
    }

    private static Position nextMoveWarnsdorff(ArrayList<Position> tour, int[][] visited){
        Position next_move = new Position();
        ArrayList<Position> possible_moves = new ArrayList<>();
        Position current_position = tour.get(tour.size()-1);
        int x = current_position.getX();
        int y = current_position.getY();
        int weight;
        int min_weight;
        ArrayList<Integer> weight_list = new ArrayList<>();
        Position p;

        if(x-2 >= 0 && y+1 < dimen && visited[x-2][y+1] != 1) {
            p = new Position(x - 2, y + 1);
            weight = calcWeight(p, visited);
            p.setWeight(weight);
            weight_list.add(weight);
            possible_moves.add(p);
        }
        if(x-2 >= 0 && y-1 >= 0 && visited[x-2][y-1] != 1){
            p = new Position(x-2,y-1);
            weight = calcWeight(p, visited);
            p.setWeight(weight);
            weight_list.add(weight);
            possible_moves.add(p);
        }
        if(x-1 >= 0 && y+2 < dimen && visited[x-1][y+2] != 1){
            p = new Position(x-1,y+2);
            weight = calcWeight(p, visited);
            p.setWeight(weight);
            weight_list.add(weight);
            possible_moves.add(p);
        }
        if(x-1 >= 0 && y-2 >= 0 && visited[x-1][y-2] != 1){
            p = new Position(x-1,y-2);
            weight = calcWeight(p, visited);
            p.setWeight(weight);
            possible_moves.add(p);
        }
        if(x+1 < dimen && y-2 >= 0 && visited[x+1][y-2] != 1){
            p = new Position(x+1,y-2);
            weight = calcWeight(p, visited);
            p.setWeight(weight);
            weight_list.add(weight);
            possible_moves.add(p);
        }
        if(x+1 < dimen && y+2 < dimen && visited[x+1][y+2] != 1){
            p = new Position(x+1,y+2);
            weight = calcWeight(p, visited);
            p.setWeight(weight);
            weight_list.add(weight);
            possible_moves.add(p);
        }
        if(x+2 < dimen && y-1 >= 0 && visited[x+2][y-1] != 1){
            p = new Position(x+2,y-1);
            weight = calcWeight(p, visited);
            p.setWeight(weight);
            weight_list.add(weight);
            possible_moves.add(p);
        }
        if(x+2 < dimen && y+1 < dimen && visited[x+2][y+1] != 1){
            p = new Position(x+2,y+1);
            weight = calcWeight(p, visited);
            p.setWeight(weight);
            weight_list.add(weight);
            possible_moves.add(p);
        }
        min_weight = weight_list.indexOf(Collections.min(weight_list));
        for(Position pos : possible_moves){
            if(pos.getWeight() == min_weight){
                next_move = pos;
            }
        }
        return next_move;
    }

    private static int calcWeight(Position position, int[][] visited){
        int weight = 0;
        int x = position.getX();
        int y = position.getY();

        if(x-2 >= 0 && y+1 < dimen && visited[x-2][y+1] != 1) {
            weight++;
        }
        if(x-2 >= 0 && y-1 >= 0 && visited[x-2][y-1] != 1){
            weight++;
        }
        if(x-1 >= 0 && y+2 < dimen && visited[x-1][y+2] != 1){
            weight++;
        }
        if(x-1 >= 0 && y-2 >= 0 && visited[x-1][y-2] != 1){
            weight++;
        }
        if(x+1 < dimen && y-2 >= 0 && visited[x+1][y-2] != 1){
            weight++;
        }
        if(x+1 < dimen && y+2 < dimen && visited[x+1][y+2] != 1){
            weight++;
        }
        if(x+2 < dimen && y-1 >= 0 && visited[x+2][y-1] != 1){
            weight++;
        }
        if(x+2 < dimen && y+1 < dimen && visited[x+2][y+1] != 1){
            weight++;
        }
        return weight;
    }

    public static void printTour(int[][] tour){
        for(int i = 0; i<tour.length; i++){
            for(int j = 0; j<tour.length; j++){
                if(j == tour.length-1){
                    System.out.print(" | " + tour[i][j]+" | " + "\n");
                }else{
                    System.out.print(" | " + tour[i][j]+" | ");
                }

            }
        }
    }

}

