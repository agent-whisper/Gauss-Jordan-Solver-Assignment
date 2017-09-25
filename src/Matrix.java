import java.util.Scanner;
import java.io.*;

public class Matrix {
    
    //Konstruktor
    public Matrix(String fileDir) {
        BufferedReader br = null;
        String line;
        int currCol = 0, currRow = 0;
        int countCol = 0, countRow = 0;

        //Inisialisasi Matriks
        try {
            br = new BufferedReader(new FileReader(fileDir));
            line = br.readLine();

            String countLine = line;
            Scanner scanner = new Scanner(countLine);

            while (scanner.hasNext()) {
                if (scanner.hasNextInt()) {
                    countCol++;
                    scanner.nextInt();
                }
            }

            while (line != null) {
                countRow++;
                line = br.readLine();
            }

            br.close();
        } catch (IOException e) {
            System.out.println("File not found! 1");
        } 

        this.row = countRow;
        this.col = countCol;
        element = new int[this.row][this.col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                element[i][j] = 0;
            }
        }

        //Isi elemen matriks
        try {
            br = new BufferedReader(new FileReader(fileDir));
            while ((line = br.readLine()) != null && (currRow <= this.row)) {
                Scanner scanner = new Scanner(line);
                currCol = 0;
                while (scanner.hasNext() && (currCol <= this.col)) {
                    if (scanner.hasNextInt()) {
                        element[currRow][currCol] = scanner.nextInt();
                        currCol++;
                    }
                }

                currRow++;
            }

            br.close();
        } catch (IOException e) {
            System.out.println("File not found!");
        }
    }

    public Matrix(int row, int col) {
        this.row = row;
        this.col = col;
        element = new int[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                element[i][j] = 0;
            }
        }
    }

    public Matrix() {
        this.row = 5;
        this.col = 5;
        element = new int[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                element[i][j] = 0;
            }
        }
    }

    //Getter Setter
    public int getElement(int row, int col){
        return (element[row][col]);
    }

    public void setElement(int row, int col, int val){
        (element[row][col]) = val;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    //Methods
    public void fill() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.format("Element (%d, %d): ", i + 1, j + 1);
                setElement(i, j, input.nextInt());
            }
        }
    }

    public void save(String fileDir) {
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(fileDir));
            
            for (int i = 0; i < this.row; i++) {
                for (int j = 0; j < this.col; j++) {
                    writer.write(Integer.toString(this.element[i][j]));

                    if ((j == this.col - 1) && (i != this.row - 1))
                        writer.newLine();
                    else 
                        writer.write(" ");
                }
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Error in saving file!");
        }
    }

    //Variabel
    private int[][] element;
    private final int row;
    private final int col;
    private Scanner input = new Scanner (System.in);

    //toString
    public String toString() {
        String tempStr = "";

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.format("%3d", element[i][j]);

                if (j < (col - 1)) {
                    System.out.print(" ");
                } else {
                    System.out.println();
                }
            }
        }

        return tempStr;
    }

    public static void main (String[] args) {
        Matrix mx = new Matrix(10, 10);
        mx.toString();
        mx.save("myMatrix.txt");
    }
}