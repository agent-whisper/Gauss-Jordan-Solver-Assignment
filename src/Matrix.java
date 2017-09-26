import java.util.Scanner;
import java.text.NumberFormat;
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
            int tempCol;

            while ((line = br.readLine()) != null) {
                Scanner scanner = new Scanner(line);
                countRow++;
                tempCol = 0;

                while (scanner.hasNext()) {
                    if (scanner.hasNextDouble()) {
                        tempCol++;
                        scanner.nextDouble();
                    }
                }

                if (countCol < tempCol) {
                    countCol = tempCol;
                }
            }

            br.close();
        } catch (IOException e) {
            System.out.println("File not found! 1");
        } 

        this.row = countRow;
        this.col = countCol;
        element = new double[this.row][this.col];

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
                    if (scanner.hasNextDouble()) {
                        element[currRow][currCol] = scanner.nextDouble();
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
        element = new double[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                element[i][j] = 0;
            }
        }
    }

    public Matrix() {
        this.row = 5;
        this.col = 5;
        element = new double[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                element[i][j] = 0;
            }
        }
    }

    //Getter Setter
    public double getElement(int row, int col){
        return (element[row][col]);
    }

    public void setElement(int row, int col, double val){
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
                System.out.format("Element (%.2f, %.2f): ", i + 1, j + 1);
                setElement(i, j, input.nextDouble());
            }
        }
    }

    public void save(String fileDir) {
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(fileDir));
            
            for (int i = 0; i < this.row; i++) {
                for (int j = 0; j < this.col; j++) {
                    writer.write(String.format("%.2f",this.element[i][j]));

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
    private double[][] element;
    private final int row;
    private final int col;
    private Scanner input = new Scanner (System.in);

    //toString
    public String toString() {
        String tempStr = "";

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.format("%5.2f", element[i][j]);

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
        Matrix mx = new Matrix("not_found.txt");
        System.out.println("Before:");
        mx.toString();

        System.out.println("After:");
        OBE.getInstance().divideRow(mx, 2, 4);
        mx.toString();

        mx.save("myMatrix2.txt");
    }
}