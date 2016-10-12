package matrix;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

//Sparse Matrix ADT
public class SparseMatrix {

    // nested class for each non-zero entry in Sparse Matrix
    class Entry {   
        public int row, col, value;

        @Override
        public String toString() {
            return "Entry{" +
                    "row=" + row +
                    ", col=" + col +
                    ", value=" + value +
                    '}';
        }

        public Entry(int row, int col, int value) {
            this.row = row;
            this.col = col;
            this.value = value;
        }
    }

    private int rowCount;
    private int colCount;
    private int entryCount;
    private Entry[] data;
    private int nextSlot;

    private SparseMatrix(int rowCount, int colCount, int entryCount) {
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.entryCount = entryCount;

        data = new Entry [entryCount];
        nextSlot = 0;
    }

    /*
     * Construct a sparse matrix by reading data from a specified file
     */
    public static SparseMatrix create(String filename) throws IOException {
        Path path = Paths.get(filename);
        if (!Files.exists(path))
            throw new IllegalArgumentException("No such file");

        SparseMatrix matrix = null;
        int row, col, count, value;
        try (Scanner scanner = new Scanner(path)) {
            row = scanner.nextInt();
            col = scanner.nextInt();
            count = scanner.nextInt();
            matrix = new SparseMatrix(row, col, count);

            for (int i = 0; i < count; i++) {
                row = scanner.nextInt();
                col = scanner.nextInt();
                value = scanner.nextInt();
                matrix.put(row, col, value);
            }
        };

        return matrix;
    }

    /*
     * You can define additional private fields and/or methods here, if necessary.
     */

    private int countEntryOfMatrices(SparseMatrix other) {
        int count = entryCount;
        for (Entry otherData: other.data) {
            boolean isDifferent = true;
            for (Entry thisData: data) {
                if (thisData.row == otherData.row && thisData.col == otherData.col) {
                    if (thisData.value + otherData.value == 0) {
                        count--;
                    }
                    isDifferent = false;
                }
            }
            if (isDifferent) count++;
        }
        return count;
    }

    private int getMaxRow() {
        int maxRow = 0;
        for (Entry d: data) {
            if (d.row > maxRow) maxRow = d.row;
        }
        return maxRow;
    }
    
    private int getMaxCol() {
        int maxCol = 0;
        for (Entry d: data) {
            if (d.col > maxCol) maxCol = d.col;
        }
        return maxCol;
    }
    
    private void put(int row, int col, int value) {
        data[nextSlot++] = new Entry(row, col, value);
    }

    /*
     * Add this matrix with another matrix other.
     * Assume that the dimensions of two matrices are always compatible.
     */
    public SparseMatrix add(SparseMatrix other) {
        // new SparseMatrix(rowCount, colCount, entryCount)
        SparseMatrix matrix = new SparseMatrix(this.rowCount>other.rowCount? this.rowCount:other.rowCount,
                                               this.colCount>other.colCount? this.colCount:other.colCount,
                                               countEntryOfMatrices(other));
        for (Entry thisEntry: data) { // matrix 에  matrixA를 넣는다
            matrix.put(thisEntry.row, thisEntry.col, thisEntry.value);
        }
        // matrix에 other를 넣는데, row와 col 이 서로 같은 entry를 '먼저' 다 더한다
        for (int j = 0; j < other.nextSlot; j++) {
            for (int i = 0; i < matrix.nextSlot; i++) {
                if (matrix.data[i].row == other.data[j].row && matrix.data[i].col == other.data[j].col) {
                    if (matrix.data[i].value + other.data[j].value == 0) { // matrix와 other 의 data[i]의 row,col이 같고, a + b = 0 일 때 한 칸씩 땡김 
                        matrix.nextSlot--;
                        for (int k = i; k < matrix.nextSlot; k++) {
                            matrix.data[k] = matrix.data[k+1];
                        }
                    } else { // matrix와 other 의 data[i]의 row,col이 같고, a + b != 0 일 때 값만 더함
                        matrix.data[i].value += other.data[j].value;
                    }
                    for (int l = j; l < other.nextSlot-1; l++) { // matrix와 other 의 data[i]의 row,col이 같고, 가 같은 경우의 entry를 matrixB에서 제거
                        other.data[l] = other.data[l+1];
                    }
                    other.nextSlot--;
                    j-=1;
                } 
            }
        }
        // matrixB의 남은 entry를 다 더한다
        for (int k = 0; k < other.nextSlot; k ++) {
            boolean isDifferent = true;
            for (int i = 0; i < matrix.nextSlot; i++) {
                if (matrix.data[i].row == other.data[k].row && matrix.data[i].col == other.data[k].col) {
                    isDifferent = false;
                }
            }
            if (isDifferent) { // row col 이 같지 않은 경우
                matrix.put(other.data[k].row, other.data[k].col, other.data[k].value);
                for (int j = matrix.nextSlot-1; j > 0; j--) {
                    if ((matrix.data[j].row < matrix.data[j-1].row) ||
                            ((matrix.data[j].row == matrix.data[j-1].row) && matrix.data[j].col < matrix.data[j-1].col)) {
                        Entry tmpEntry = matrix.data[j];
                        matrix.data[j] = matrix.data[j-1];
                        matrix.data[j-1] = tmpEntry;
                    }
                }
            }
        }
        return matrix;
    }

    /*
     * Transpose matrix
     */
    public SparseMatrix transpose() {
        SparseMatrix matrix = new SparseMatrix(getMaxCol()+1, getMaxRow()+1, entryCount);
        for (int i = 0; i < entryCount; i++) {
            matrix.put(data[i].col, data[i].row, data[i].value);
            for (int j = i; j > 0; j--) {
                if ((matrix.data[j].row < matrix.data[j-1].row) ||
                        ((matrix.data[j].row == matrix.data[j-1].row) && matrix.data[j].col < matrix.data[j-1].col)) {
                    Entry tmpEntry = matrix.data[j];
                    matrix.data[j] = matrix.data[j-1];
                    matrix.data[j-1] = tmpEntry;
                }
            }
        }
        return matrix;
    }

    public void print() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(rowCount + ", " + colCount + ", " + entryCount + "\n");
        for (int i = 0; i < entryCount; i++) {
            builder.append(data[i].row + ", " + data[i].col + ", " + data[i].value + "\n");
        }
        return builder.toString();
    }

    /*
     * DO NOT MODIFY CODE BELOW!!!!!
     */
    public static void main(String... args) throws IOException {
        if (args[0].equals("p")) { // Print current matrix
            System.out.println("Printing a matrix: " + args[1]);
            SparseMatrix m = SparseMatrix.create(args[1]);
            m.print();
        } else if (args[0].equals("a")) { // Add two matrices
            System.out.println("Adding two matrices: " + args[1] + " and " + args[2] + "\n");
            SparseMatrix A = SparseMatrix.create(args[1]);
            System.out.println("Matrix A = \n" + A);
            SparseMatrix B = SparseMatrix.create(args[2]);
            System.out.println("Matrix B = \n" + B);

            System.out.println("Matrix A + B = \n" + A.add(B));
        } else if (args[0].equals("t")) {   // Transpose a matrix
            System.out.println("Transposing a matrix: " + args[1]);
            SparseMatrix matrix = SparseMatrix.create(args[1]);
            System.out.println(matrix);
            SparseMatrix transposedMatrix = matrix.transpose();
            System.out.println("Transposed Matrix = \n" + transposedMatrix);
        } else {
            System.err.println("Unknown operation ...");
        }
    }
}
