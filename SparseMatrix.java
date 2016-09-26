import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Collectors;

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

    private void put(int row, int col, int value) {
        data[nextSlot++] = new Entry(row, col, value);
    }

    /*
     * Add this matrix with another matrix other.
     * Assume that the dimensions of two matrices are always compatible.
     */
    public SparseMatrix add(SparseMatrix other) {
        SparseMatrix matrix = new SparseMatrix(this.rowCount>other.rowCount?this.rowCount:other.rowCount,
                this.colCount>other.colCount?this.colCount:other.colCount,
                        countEntryOfMatrices(other));
        for (Entry thisEntry: this.data) {
            matrix.put(thisEntry.row, thisEntry.col, thisEntry.value);
        }
        for (Entry otherEntry: other.data) {
            boolean flag = false;
            for (int i = 0; i < matrix.nextSlot; i++) {
                // if each row, col is the same
                if (matrix.data[i].row == otherEntry.row && matrix.data[i].col == otherEntry.col) {
                    // if the sum of values is 0
                    if (matrix.data[i].value + otherEntry.value == 0) {
                        System.out.print("i: "+ i + ", ");
                        System.out.println(matrix.data[i].row + " " + matrix.data[i].col + " " +matrix.data[i].value);
                        System.out.println("matrix.nextSlot: "+matrix.nextSlot);
                        for (int j = i; j < matrix.nextSlot; j++) {
                            matrix.data[j] = matrix.data[j+1];
                        }
                        matrix.nextSlot--;
                    } else {
                        matrix.data[i].value += otherEntry.value;
                    }
                    flag = true;
                } 
            }
            if (!flag) {
                matrix.put(otherEntry.row, otherEntry.col, otherEntry.value);
                System.out.println("nextSlot after put: " + matrix.nextSlot);
                System.out.println(otherEntry.row +" "+ otherEntry.col +" "+ otherEntry.value);
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
        SparseMatrix matrix = new SparseMatrix(rowCount, colCount, entryCount);
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
