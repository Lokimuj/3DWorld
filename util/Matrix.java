package util;

import org.omg.CORBA.MARSHAL;

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

    public Matrix(Matrix other){
        this.rows = other.rows;
        this.cols = other.cols;
        this.matrix  = new double[rows][cols];
        for(int i = 0;i<rows;i++){
            for(int j = 0; j<cols; j++){
                this.matrix[i][j] = other.matrix[i][j];
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

    public void addToThis(Matrix other){
        equivalentMatrixCheck(this,other);
        for(int i = 0; i<this.rows; i++){
            for(int j = 0;j<this.cols;j++){
                this.matrix[i][j]+=other.matrix[i][j];
            }
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
            for(int k = 0; k<other.getDimensions();k++){
                vector[k] = this.matrix[i][k]*other.getIndex(k);
            }
        }
        return new Vector(vector);
    }

    private static void equivalentMatrixCheck(Matrix m1, Matrix m2){
        if(m1.rows!=m2.rows || m1.cols!=m2.cols){
            throw new LinearAlgebraException("Invalid Matrix operation: matrices not of same size.\n " +
                            "Matrix 1: rows: "+m1.rows+" cols: "+m1.cols+" Matrix 2: rows: "+m2.rows+" cols: "+m2.cols);
        }
    }

    private static void colRowEquivalenceCheck(Matrix left, Matrix right){
        if(left.cols!=right.rows){
            throw new LinearAlgebraException("Invalid Matrix operation: left side cols does not equal right side rows. left cols: "+left.cols+" right rows: "+right.rows);
        }
    }
    private static void colRowEquivalenceCheck(Matrix left, Vector right){
        if(left.cols!=right.getDimensions()){
            throw new LinearAlgebraException("Invalid Matrix operation: left side cols does not equal right side rows. left cols: "+left.cols+" right rows: "+right.getDimensions());
        }
    }

}
