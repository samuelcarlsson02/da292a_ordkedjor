
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;


public class Main {
    public static void main(String[] args) throws IOException {
        String file = "/Users/samuel/Documents/Skola/Datastrukturer och Algoritmer/OrdKedjor/src/OrdMängd";
        BufferedReader r =
                new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        ArrayList<String> words = new ArrayList<String>();

        while (true) {
            String word = r.readLine();
            if (word == null) { break; }
            assert word.length() == 5;  // indatakoll, om man kör med assertions på
            words.add(word);
        }
        int numOfWords = words.size();
        Digraph digraph = new Digraph(numOfWords);
        buildGraph(digraph, words);

        String file2 = "/Users/samuel/Documents/Skola/Datastrukturer och Algoritmer/OrdKedjor/src/Testfall";
        BufferedReader r2 =
                new BufferedReader(new InputStreamReader(new FileInputStream(file2)));
        while (true) {
            String line = r2.readLine();
            if (line == null) { break; }
            assert line.length() == 11; // indatakoll, om man kör med assertions på
            String start = line.substring(0, 5);
            String goal = line.substring(6, 11);
            int distance = BFS(digraph, start, goal, words);
            System.out.println(distance);
        }

    }
    private static void buildGraph(Digraph digraph, ArrayList<String> words){
        int numOfWords = words.size();

        for(int i = 0; i < numOfWords; i++){
            for (int j = 0; j < numOfWords; j++) {
                if (isConnected(words.get(i), words.get(j))){
                    digraph.addEdge(i,j);
                }
            }
        }

    }

    private static boolean isConnected(String fromWord, String toWord) {
        String fromWordSubstring = fromWord.substring(1, 5);
        ArrayList<String> fromWordList = new ArrayList<>();

        for (int i = 0; i < fromWordSubstring.length(); i++) {
            fromWordList.add(fromWordSubstring.substring(i, i + 1));
        }

        for (int i = 0; i < fromWordList.size(); i++) {
            String letter = fromWordList.get(i);
            if (!toWord.contains(letter)) {
                return false;
            } else {
                toWord = toWord.replaceFirst(letter, "");
            }
        }

        return true;
    }

    public static int BFS(Digraph graph, String startWord, String goalWord, ArrayList<String> words) {
        int startWordIdx = words.indexOf(startWord);
        int goalWordIdx = words.indexOf(goalWord);

        if (startWordIdx == -1 || goalWordIdx == -1) {
            return -1;
        }
        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(graph, startWordIdx);
        if (bfs.hasPathTo(goalWordIdx)) {
            return bfs.distTo(goalWordIdx);
        } else {
            return -1;
        }
    }
}