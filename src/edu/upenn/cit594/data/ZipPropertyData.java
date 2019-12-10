package edu.upenn.cit594.data;

// This class uses the ZipPropertyData class which is part of a larger algorithm.
public class ZipPropertyData {
	private int count = 0;
	private double total = 0;

	public ZipPropertyData() {

	}

	public int getCount() {
		return count;
	}

	public double getTotal() {
		return total;
	}

	public void addTotal(double value) {
		total += value;
		count++;
	}

	public int calculateAverage(double total, int count) {
		return ((int) total / count);
	}
}
