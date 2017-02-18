import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;
import java.util.LinkedList;

public class Solver {
    private final Board initial;
    private SearchNode goalNode;
    private boolean isSolvable;
    private boolean hasBeenRun;

    public Solver(Board b) {
        if (b == null) throw new java.lang.NullPointerException();
        initial = b;
    }

    private static class SearchNode {
        private final Board board;
        private final int moves;
        private final SearchNode prev;
        private int priority = 0;

        public SearchNode(Board board, int moves, SearchNode prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
        }

        public Board getBoard() {
            return board;
        }
        
        public int getMoves() {
            return moves;
        }

        public SearchNode getPrevNode() {
            return prev;
        }

        public int getPriority() {
            if (priority == 0) priority = moves + board.manhattan();
            return priority;
        }

        public boolean isGoal() {
            return board.isGoal();
        }

    }
    // 
    private void run() {
        Comparator<SearchNode> cmp = new Comparator<SearchNode>() {
            public int compare(SearchNode sn1, SearchNode sn2) {
                int cmp = sn1.getPriority() - sn2.getPriority();
                if (cmp > 0) return 1;
                else if (cmp < 0) return 0;
                return -1;
            }
        };
        MinPQ<SearchNode> mpq1 = new MinPQ<SearchNode>(cmp);
        MinPQ<SearchNode> mpq2 = new MinPQ<SearchNode>(cmp);
        mpq1.insert(new SearchNode(initial, 0, null));
        mpq2.insert(new SearchNode(initial.twin(), 0, null));
        while (true) {
            SearchNode node = mpq1.delMin();
            if (node.isGoal()) {
                goalNode = node;
                isSolvable = true;
                break;
            }
            Board prevBoard = node.getPrevNode() == null ? null : node.getPrevNode().getBoard();
            for (Board b : node.getBoard().neighbors()) {
                if (!b.equals(prevBoard))
                    mpq1.insert(new SearchNode(b, node.getMoves() + 1, node));
            }
            node = mpq2.delMin();
            if (node.isGoal()) break;
            prevBoard = node.getPrevNode() == null ? null : node.getPrevNode().getBoard();
            for (Board b : node.getBoard().neighbors()) {
                if (!b.equals(prevBoard))
                    mpq2.insert(new SearchNode(b, node.getMoves() + 1, node));
            }
        }
        hasBeenRun = true;
    }

    public boolean isSolvable() {
        if (!hasBeenRun) run();
        return isSolvable;
    }

    public int moves() {
        if (!hasBeenRun) run();
        if (!isSolvable) return -1;
        return goalNode.getMoves();

    }

    public Iterable<Board> solution() {
        if (!hasBeenRun) run();
        if (!isSolvable) return null;
        LinkedList<Board> llb = new LinkedList<Board>();
        SearchNode node = goalNode;
        while (node != null) {
            llb.addFirst(node.getBoard());
            node = node.getPrevNode();
        }
        return llb;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            StdOut.println();
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
