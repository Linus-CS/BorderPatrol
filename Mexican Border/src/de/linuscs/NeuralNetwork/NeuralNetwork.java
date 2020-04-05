package de.linuscs.NeuralNetwork;

import de.linuscs.Utils.MatrixFNN;

public class NeuralNetwork {

	private int inputs;
	private int hidden;
	private int outputs;

	private MatrixFNN weightsIH;
	private MatrixFNN weightsHO;

	private MatrixFNN bias_h;
	private MatrixFNN bias_o;

	public NeuralNetwork(int inputs, int hidden, int outputs) {
		this.inputs = inputs;
		this.hidden = hidden;
		this.outputs = outputs;

		weightsIH = new MatrixFNN(this.inputs, this.hidden); // i 2 h 2
		weightsHO = new MatrixFNN(this.hidden, this.outputs);
		weightsIH.randomize();
		weightsHO.randomize();
		
		bias_h = new MatrixFNN(this.hidden, 1);
		bias_o = new MatrixFNN(this.outputs, 1);
		bias_h.randomize();
		bias_o.randomize();
	}

	public double[] feedforward(MatrixFNN inputs) {
		MatrixFNN hiddenN = new MatrixFNN(this.hidden, 1);
		hiddenN = weightsIH.times(inputs.transpose());
		hiddenN.add(bias_h);
		hiddenN.mapToSigmoid();

		MatrixFNN outputN = new MatrixFNN(this.outputs, 0);
		
		outputN = weightsHO.transpose().times(hiddenN);
		outputN.add(bias_o);

		outputN.mapToSigmoid();

		return outputN.toArray();
	}

	public void train(MatrixFNN inputs, MatrixFNN targets) {
		MatrixFNN outputs = MatrixFNN.fromArray(feedforward(inputs));
		
		MatrixFNN output_errors = targets;
		output_errors.subtract(outputs);
		
		MatrixFNN who_transpose = weightsHO;
		who_transpose.transpose();
		
		MatrixFNN hidden_errors = who_transpose;
		hidden_errors.times(output_errors);
	}
}
