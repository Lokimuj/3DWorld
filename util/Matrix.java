package util;

import java.util.Arrays;

public class Matrix {

    public static final Matrix INVALID_MATRIX = new Matrix(1);

    protected double[][] matrix;
    protected int rows, cols;
    protected int precision = Constants.DEFAULT_PRECISION;
    protected boolean precisionActive = false;

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

    public Matrix(Matrix other){
        this.rows = other.rows;
        this.cols = other.cols;
        this.matrix  = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for(int j = 0; j<cols; j++){
                this.matrix[i][j] = other.matrix[i][j];
            }
        }
    }

    public Matrix(double[][] matrix){

        this.rows = matrix.length;
        this.cols = matrix[0].length;
        this.matrix = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for(int j = 0; j<cols; j++){
                this.matrix[i][j] = matrix[i][j];
            }
        }
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

    public static Matrix constructIdentityMatrix(int dimensions){
        Matrix result = new Matrix(dimensions);
        for(int i = 0; i<dimensions; i++){
            result.matrix[i][i] = 1;
        }
        return result;
    }


    public static Matrix constructByXYRotation(double theta){
        Matrix result =  new Matrix(3);
        result.matrix[0][0] = Math.cos(theta);
        result.matrix[0][1] = -1*Math.sin(theta);
        result.matrix[1][0] = Math.sin(theta);
        result.matrix[1][1] = Math.cos(theta);
        result.matrix[2][2] = 1;
        return result;
    }
    public static Matrix constructByYZRotation(double theta){
        Matrix result =  new Matrix(3);
        result.matrix[1][1] = Math.cos(theta);
        result.matrix[1][2] = -1*Math.sin(theta);
        result.matrix[2][1] = Math.sin(theta);
        result.matrix[2][2] = Math.cos(theta);
        result.matrix[0][0] = 1;
        return result;
    }
    public static Matrix constructByXZRotation(double theta){
        Matrix result =  new Matrix(3);
        result.matrix[0][0] = Math.cos(theta);
        result.matrix[0][2] = -1*Math.sin(theta);
        result.matrix[2][0] = Math.sin(theta);
        result.matrix[2][2] = Math.cos(theta);
        result.matrix[1][1] = 1;
        return result;
    }

    public Vector[] toRows(){
        Vector[] result = new Vector[rows];
        for(int i = 0;i<rows;i++){
            result[i] = new Vector(matrix[i]);
        }
        return result;
    }

    public Vector[] toCols(){
        Vector[] result = new Vector[cols];
        for(int i = 0; i<cols;i++){
            double[] vector = new double[rows];
            for(int j = 0;j<rows;j++){
                vector[j] = matrix[j][i];
            }
            result[i] = new Vector(vector);
        }
        return result;
    }

    public int getPrecision() {
        return precision;
    }

    private void precisify(){
        for(int i = 0; i<rows;i++){
            for(int j = 0; j<cols;j++){
                matrix[i][j] = precisifyValue(matrix[i][j]);
            }
        }
    }

    private double precisifyValue(double val){
        return Math.round(val*Math.pow(10,precision))/Math.pow(10,precision);
    }

    public void setPrecision(int precision) {

        if(precision<this.precision && precisionActive){
            this.precision = precision;
            precisify();
        }else{
            this.precision = precision;
        }


    }
    public void setRestrictPrecision(boolean precisionActive){
        this.precisionActive = precisionActive;
        if(precisionActive){
            precisify();
        }
    }

    public void addToThis(Matrix other){
        equivalentMatrixCheck(this,other);
        for(int i = 0; i<this.rows; i++){
            for(int j = 0;j<this.cols;j++){
                this.matrix[i][j]+=other.matrix[i][j];
            }
        }
        if(precisionActive){
            precisify();
        }
    }

    public Matrix add(Matrix other){
        Matrix result = new Matrix(this);
        result.addToThis(other);
        return result;
    }

    public void scalarMultiplyThis(double scalar){
        for(int i = 0; i<this.rows; i++){
            for(int j = 0; j<this.cols; j++){
                matrix[i][j]*=scalar;
            }
        }
        if(precisionActive){
            precisify();
        }
    }

    public Matrix scalarMultiple(double scalar){
        Matrix result = new Matrix(this);
        result.scalarMultiplyThis(scalar);
        return result;
    }

    public void subFromThis(Matrix other){
        equivalentMatrixCheck(this,other);
        for(int i = 0;i<this.rows;i++){
            for(int j = 0 ;j<this.cols;j++){
                this.matrix[i][j]-=other.matrix[i][j];
            }
        }
        if(precisionActive){
            precisify();
        }
    }

    public Matrix sub(Matrix other){
        Matrix result = new Matrix(this);
        result.subFromThis(other);
        return result;
    }

    public Matrix leftMultiply(Matrix other){
        colRowEquivalenceCheck(this,other);
        Matrix matrix = new Matrix(this.rows,other.cols);
        for(int i = 0;i<this.rows;i++){
            for(int j = 0;j<other.cols;j++){
                for(int k = 0; k<other.rows; k++){
                    matrix.matrix[i][j]+=this.matrix[i][k]*other.matrix[k][j];
                }
            }
        }
        return matrix;
    }

    public Vector leftMultiply(Vector other){
        colRowEquivalenceCheck(this,other);
        double[] vector = new double[other.getDimensions()];
        for(int i = 0;i<this.rows;i++){
            for (int k = 0; k < other.getDimensions(); k++) {
                vector[i] += this.matrix[i][k] * other.getIndex(k);
            }
        }
        return new Vector(vector);
    }

//    public Matrix solve(Matrix other){
//        if(this.rows!=other.rows){
//            throw new LinearAlgebraException("Solving a matrix requires the same amount of rows on both sides. This rows: "+rows+" other rows: "+other.rows);
//        }
//
//    }
//
//    private Matrix solveSquare(Matrix other){
//        return null;
//    }
//
//    private Matrix solveNonSquare(Matrix other){
//        Vector[] left = this.toRows();
//        Vector[] right = other.toRows();
//        int i = 0;
//        for(; i<rows&&i<cols;i++){
//            if(left[i].getIndex(i)==0){
//                boolean found = false;
//                for(int j = i+)
//            }
//        }
//    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Matrix " + rows + "x" + cols + "\n");
        for(int i = 0; i<rows; i++){
            result.append("{");
            for(int j = 0;j<cols;j++){
                result.append("| ").append(matrix[i][j]).append(" |");
            }
            result.append("}\n");
        }
        return result.toString();
    }

    protected static void equivalentMatrixCheck(Matrix m1, Matrix m2){
        if(m1.rows!=m2.rows || m1.cols!=m2.cols){
            throw new LinearAlgebraException("Invalid Matrix operation: matrices not of same size.\n " +
                            "Matrix 1: rows: "+m1.rows+" cols: "+m1.cols+" Matrix 2: rows: "+m2.rows+" cols: "+m2.cols);
        }
    }

    @Override
    public boolean equals(Object other){
        if (!(other instanceof Matrix)){
            return false;
        }
        Matrix real = (Matrix) other;
        return this.rows == real.rows && this.cols == real.cols && Arrays.deepEquals(this.matrix, real.matrix);
    }

    @Override
    public int hashCode(){
        return Arrays.deepHashCode(this.matrix);
    }

    public Matrix solve(Matrix other){
        return null;
    }

    public double determinant(){
        squareMatrixCheck(this);
        return this.determinantHelper();
    }

    protected double determinantHelper(){
        int dim = this.rows;
        if (dim == 2){
            return this.matrix[0][0]*this.matrix[1][1] - this.matrix[0][1]*this.matrix[1][0];
        }else if(dim == 1){
            return matrix[0][0];
        }
        double sum = 0;
        Matrix sub = new Matrix(dim-1);
        for(int row = 1;row<rows;row++){
            for(int col = 1; col<cols;col++){
                sub.matrix[row-1][col-1] = matrix[row][col];
            }
        }
        for(int i = 0; i<dim;i++){
            if(matrix[0][i]!=0) {
                sum +=  (i % 2 == 0 ? 1 : -1) * matrix[0][i] * sub.determinantHelper();
            }
            if(i!=dim-1){
                for(int j = 0;j<rows-1;j++){
                    sub.matrix[j][i] = matrix[j+1][i];
                }
            }
        }
        return sum;
    }

    protected static void colRowEquivalenceCheck(Matrix left, Matrix right){
        if(left.cols!=right.rows){
            throw new LinearAlgebraException("Invalid Matrix operation: left side cols does not equal right side rows. left cols: "+left.cols+" right rows: "+right.rows);
        }
    }
    protected static void colRowEquivalenceCheck(Matrix left, Vector right){
        if(left.cols!=right.getDimensions()){
            throw new LinearAlgebraException("Invalid Matrix operation: left side cols does not equal right side rows. left cols: "+left.cols+" right rows: "+right.getDimensions());
        }
    }

    protected static void squareMatrixCheck(Matrix matrix){
        if(matrix.rows!=matrix.cols){
            throw new LinearAlgebraException("Invalid Matrix operation: matrix needs to be square. Your matrix rows: "+matrix.rows+" cols: "+matrix.cols);
        }
    }
}
