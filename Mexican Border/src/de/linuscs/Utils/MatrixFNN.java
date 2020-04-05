package de.linuscs.Utils;

public class MatrixFNN {

	private int rows;
	private int colums;

	private double[][] matrix;

	public MatrixFNN(int rows, int colums) {
		this.rows = rows;
		this.colums = colums;

		matrix = new double[this.rows][this.colums];

		for (int i = 0; i < rows; i++) {
			for (int p = 0; p < colums; p++) {
				matrix[i][p] = 0;
			}
		}

	}

	public MatrixFNN(double[][] array) {
		matrix = array;
	}

	public void add(MatrixFNN m) {
		if (this.rows != m.getRows() && m.getColums() != 1 || this.colums != m.getColums() && m.getColums() != 1) {
			throw new IllegalArgumentException("Matrices must have same dimensions.");
		} else if (m.getColums() == 1) {
			for (int i = 0; i < rows; i++) {
				for (int p = 0; p < colums; p++) {
					matrix[i][p] = matrix[i][p] + m.getArray()[i][0];
				}
			}
		} else {
			for (int i = 0; i < rows; i++) {
				for (int p = 0; p < colums; p++) {
					matrix[i][p] = matrix[i][p] + m.getArray()[i][p];
				}
			}
		}
	}

	public void add(int num) {
		for (int i = 0; i < rows; i++) {
			for (int p = 0; p < colums; p++) {
				matrix[i][p] = matrix[i][p] + num;
			}
		}
	}

	public void subtract(MatrixFNN m) {
		if (this.rows != m.getRows() && m.getColums() != 1 || this.colums != m.getColums() && m.getColums() != 1) {
			throw new IllegalArgumentException("Matrices must have same dimensions.");
		} else if (m.getColums() == 1) {
			for (int i = 0; i < rows; i++) {
				for (int p = 0; p < colums; p++) {
					matrix[i][p] = matrix[i][p] - m.getArray()[i][0];
				}
			}
		} else {
			for (int i = 0; i < rows; i++) {
				for (int p = 0; p < colums; p++) {
					matrix[i][p] = matrix[i][p] - m.getArray()[i][p];
				}
			}
		}
	}

	public MatrixFNN times(MatrixFNN inputs) {
		MatrixFNN newMatrix = new MatrixFNN(this.rows, inputs.getColums());
		if (this.colums != inputs.getRows()) {
			throw new IllegalArgumentException("A colums must be B rows!");
		} else {
			for (int i = 0; i < newMatrix.getRows(); i++) {
				for (int p = 0; p < newMatrix.getColums(); p++) {

					double value = 0;
					for (int k = 0; k < this.colums; k++) {
						value = matrix[i][p] * inputs.getArray()[i][p];
					}
					newMatrix.set(i, p, value);

				}
			}
		}
		return newMatrix;
	}

	public void multiply(int num) {
		for (int i = 0; i < rows; i++) {
			for (int p = 0; p < colums; p++) {
				matrix[i][p] = matrix[i][p] * num;
			}
		}
	}

	public MatrixFNN transpose() {
		MatrixFNN newMatrix = new MatrixFNN(this.colums, this.rows);
		for (int i = 0; i < rows; i++) {
			for (int p = 0; p < colums; p++) {
				newMatrix.set(p, i, matrix[i][p]);
			}
		}
		return newMatrix;
	}

	public void mapToSigmoid() {
		for (int i = 0; i < rows; i++) {
			for (int p = 0; p < colums; p++) {
				matrix[i][p] = sigmoid(matrix[i][p]);
			}
		}
	}

	public void times(int num) {
		for (int i = 0; i < rows; i++) {
			for (int p = 0; p < colums; p++) {
				matrix[i][p] = matrix[i][p] * num;
			}
		}
	}

	public void randomize() {
		for (int i = 0; i < rows; i++) {
			for (int p = 0; p < colums; p++) {
				matrix[i][p] = Math.floor(Math.random() * 10 - 3);
			}
		}
	}

	public static MatrixFNN fromArray(double[] array) {
		MatrixFNN newMatrix = new MatrixFNN(1, array.length);
		for (int i = 0; i < array.length; i++) {
			newMatrix.set(0, i, array[i]);
		}
		return newMatrix;
	}

	public void set(int row, int colum, double input) {
		matrix[row][colum] = input;
	}

	public double get(int row, int colum) {
		return matrix[row][colum];
	}

	public int getRows() {
		return rows;
	}

	public int getColums() {
		return colums;
	}

	public double[][] getArray() {
		return matrix;
	}

	public double[] toArray() {
		double[][] twoDArray = matrix;
		double[] oneDArray = new double[rows * colums];
		int k = 0;
		for (int i = 0; i < rows; i++) {
			for (int p = 0; p < colums; p++) {
				oneDArray[k] = twoDArray[i][p];
				k++;
			}
		}
		return oneDArray;
	}

	public static double sigmoid(double x) {
	    return (1/( 1 + Math.pow(Math.E,(-1*x))));
	 }
}
