package edu.upenn.cit594.data;

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
	
	
}
