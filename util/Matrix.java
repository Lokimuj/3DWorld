package util;

public class Matrix {
    private double[][] matrix;
    int rows, cols;

    public Matrix(int rows, int cols){
        if(rows<=0 || cols<=0){
            throw new LinearAlgebraException("Matrices require at least 1 dimension in each direction. Your parameters: rows: "+rows+" cols: "+cols);
        }
        matrix = new double[rows][cols];
        this.rows = rows;
        this.cols = cols;
    }

    public Matrix(int dimensions){
        if(dimensions<=0){
            throw new LinearAlgebraException("Matrices require at least 1 dimension in each direction. Your parameter: "+dimensions);
        }
        matrix = new double[dimensions][dimensions];
        this.rows = this.cols = dimensions;
    }

    public static Matrix constructByRows(Vector... vectors){
        int rows = vectors.length;
        if(vectors.length<=0){
            throw new LinearAlgebraException("Constructing by rows needs at least 1 vector");
        }
        int cols = vectors[0].getDimensions();
        Matrix matrix = new Matrix(rows,cols);
        for(int i = 0; i<vectors.length; i++){
            Vector vector = vectors[i];
            if(vector.getDimensions()!=cols){
                throw new LinearAlgebraException("constructByRows: Matrices must not be jagged. Some vectors were not of equal dimensions");
            }
            for(int j = 0; j<cols; j++){
                matrix.matrix[i][j] = vector.getIndex(i);
            }
        }
        return matrix;
    }

    public static Matrix constructByColumns(Vector... vectors){
        int cols = vectors.length;
        if(vectors.length<=0){
            throw new LinearAlgebraException("Constructing by columns needs at least 1 vector");
        }
        int rows = vectors[0].getDimensions();
        Matrix matrix = new Matrix(rows,cols);
        for(int i = 0; i<vectors.length; i++){
            Vector vector = vectors[i];
            if(vector.getDimensions()!=rows){
                throw new LinearAlgebraException("constructByColumns: Matrices must not be jagged. Some vectors were not of equal dimensions");
            }
            for(int j = 0; j<rows; j++){
                matrix.matrix[j][i] = vector.getIndex(i);
            }
        }
        return matrix;
    }

}
