public class Percolation {
    private final int[] cells;
    private final int[] forestSize;
    private final int grid;
    private int openCounter;
    private final int lastIndex;

    /**
     * Constructor
     * @param n int - number of rows and columns (full grid is n * n)
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("invalid n argument");
        }

        cells = new int[n * n + 2];
        forestSize = new int[n * n + 2];
        grid = n;

        lastIndex = grid * grid + 1;
        cells[lastIndex] = lastIndex;
    }

    /**
     * Opens the site (row, col) if it is not open already
     * @param row int - row number
     * @param col int - column number
     */
    public void open(int row, int col) {
        int index = getIndex(row, col);

        if (cells[index] == 0) {
            cells[index] = index;
            forestSize[index] = 1;

            openCounter++;
            cluster(row, col);
        }
    }

    /**
     * Returns is site (row, col) is open
     * @param row int - row number
     * @param col int - column number
     * @return boolean
     */
    public boolean isOpen(int row, int col) {
        return cells[getIndex(row, col)] != 0;
    }

    /**
     * Returns is the site (row, col) full
     * @param row int - row number
     * @param col int - column number
     * @return boolean
     */
    public boolean isFull(int row, int col) {
        return isOpen(row, col) && cells[0] != 0 && find(getIndex(row, col)) == find(0);
    }

    /**
     * Returns the number of open sites
     * @return int
     */
    public int numberOfOpenSites() {
        return openCounter;
    }

    /**
     * Returns does the system perlocates
     * @return boolean
     */
    public boolean percolates() {
        return find(0) == find(lastIndex);
    }

    /**
     * Returns index as we use flat array
     * @param row int - row number
     * @param col int - column number
     * @return boolean
     */
    private int getIndex(int row, int col) {
        if (row < 1 || row > grid || col < 1 || col > grid) {
            throw new IllegalArgumentException("row or column out of grid range");
        }

        return grid * (row - 1) + col;
    }

    /**
     * Clusters site (row, col)
     * @param row int - row number
     * @param col int - column number
     */
    private void cluster(int row, int col) {
        int index = getIndex(row, col);
        boolean clustered = false;

        if (row - 1 >= 1 && isOpen(row - 1, col)) {
            union(index, getIndex(row - 1, col));
            clustered = true;
        }

        if (col - 1 >= 1 && isOpen(row, col - 1)) {
            union(index, getIndex(row, col - 1));
            clustered = true;
        }

        if (row + 1 <= grid && isOpen(row  + 1, col)) {
            union(index, getIndex(row + 1, col));
            clustered = true;
        }

        if (col + 1 <= grid && isOpen(row, col + 1)) {
            union(index, getIndex(row, col + 1));
            clustered = true;
        }

        if (!clustered) {
            cells[index] = index;
        }

        if (row == 1) {
            union(0, index);
        }

         if (row == grid) {
             union(lastIndex, index);
         }
    }

    /**
     * Makes union between two site indexes
     * @param index int  - first index
     * @param index1 int - second index
     */
    private void union(int index, int index1) {
        int root = find(index);
        int root1 = find(index1);

        if (root == root1) {
            return;
        }

        if (root == 0 || root == lastIndex || forestSize[root] < forestSize[root1]) {
            cells[root] = root1;
            forestSize[root1] += forestSize[root];
            return;
        }

        cells[root1] = root;
        forestSize[root] += forestSize[root1];
    }

    /**
     * Finds root by index
     * @param index int - site index
     * @return int
     */
    private int find(int index) {
        while (index != cells[index]) {
            index = cells[index];
        }

        return index;
    }

    /**
     * Optional for tests
     * @param args String[]
     */
    public static void main(String[] args) {
        // optional for test
    }
}
