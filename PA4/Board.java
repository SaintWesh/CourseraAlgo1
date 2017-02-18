import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private final int[][] blocks;
    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] bs) {
        if (bs == null) throw new java.lang.NullPointerException();
        int len = bs.length;
        blocks = new int[len][len];
        for (int i = 0; i < len; ++i) {
            for (int j = 0; j < len; ++j) {
                this.blocks[i][j] = bs[i][j];
            }
        }
    }
    // board dimension n                                          
    public int dimension() {
        return blocks.length;
    }
    // number of blocks out of place
    public int hamming() {
        int num = 1;
        int count = 0;
        int dim = dimension();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (blocks[i][j] != num++) {
                    ++count;
                }
            }
        }
        return count - 1;
    }               
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int count = 0;
        int dim = dimension();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                int elem = blocks[i][j];
                // skip when be zero
                if (elem == 0) continue;
                int row = (elem + dim - 1) / dim;
                int col = elem % dim;
                if (col == 0) col = dim;
                count += Math.abs(row - (i + 1)) + Math.abs(col - (j + 1));
            }
        }
        return count;
    }
    // is this board the goal board?
    public boolean isGoal() {
        // fast check
        int dim = dimension();
        if (blocks[dim-1][dim-1] != 0) return false;
        // boring method
        int num = 0;
        boolean flag = true;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (blocks[i][j] != ++num) {
                    flag = false;
                    break;
                }
            }
            if (!flag) break;
        }
        if (num == dim * dim) return true;
        return false;
    }              
    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int dim = dimension();
        int row1 = 0, col1 = 0;
        if (blocks[row1][col1] == 0) col1 = 1;
        int row2 = dim - 1, col2 = 0;
        if (blocks[row2][col2] == 0) col2 = 1;
        return exchange(row1, col1, row2, col2);
    }                   
    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board b = (Board) y;
        int dim = dimension();
        if (dim != b.dimension()) return false;
        for (int i = 0; i < dim; i++) {
            if (b.blocks[i] == null || b.blocks[i].length != dim) return false;
            for (int j = 0; j < dim; j++) {
                if (blocks[i][j] != b.blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }      
    // x, y start with 0
    private boolean inBoard(int x, int y) {
        int dim = dimension();
        // out of bound
        if (x < 0 || x >= dim || y < 0 || y >= dim) return false;
        return true;
    }
    
    private Board exchange(int x1, int y1, int x2, int y2) {
        // we have to guarantee that the first position is valid
        if (!inBoard(x1, y1)) throw new java.lang.IllegalArgumentException();
        // specail case
        if ((x1 == x2 && y1 == y2) || !inBoard(x2, y2)) return this;
        // normal case
        int[][] copyBlocks = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                copyBlocks[i][j] = blocks[i][j];
            }
        }
        // swap element
        int temp = copyBlocks[x1][y1];
        copyBlocks[x1][y1] = copyBlocks[x2][y2];
        copyBlocks[x2][y2] = temp;
        return new Board(copyBlocks);
    }
    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> sb = new Stack<Board>();
        int dim = dimension();
        // get position of the blank block
        int i = 0, j = 0;
        boolean flag = true;
        for (i = 0; i < dim; i++) {
            for (j = 0; j < dim; j++) {
                if (blocks[i][j] == 0) {
                    flag = false;
                    break;
                }
            }
            if (!flag) break;
        }
        // find neighbors
        Board b1 = exchange(i, j, i, j + 1);
        if (b1 != this) sb.push(b1);
        Board b2 = exchange(i, j, i, j - 1);
        if (b2 != this) sb.push(b2);
        Board b3 = exchange(i, j, i + 1, j);
        if (b3 != this) sb.push(b3);
        Board b4 = exchange(i, j, i - 1, j);
        if (b4 != this) sb.push(b4);
        return sb;

    }     
    // string representation of this board (in the output format specified below)
    public String toString() {
        int dim = dimension();
        StringBuilder s = new StringBuilder();
        s.append(dim + "\n");
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }               
    // unit tests (not graded)
    public static void main(String[] args)  {

    }

}
