package edu.upenn.cit594.data;

public class ZipPropertyData {
	private int validResidenceNumForMarketValueForOneZip = 0;
	private int validResidenceNumForLivableAreaForOneZip = 0;
	private double totalMarketValueForOneZip = 0;
	private double totalLivableAreaForOneZip = 0;

	public ZipPropertyData() {

	}

	public int getValidResidenceNumForMarketValueForOneZip() {
		return validResidenceNumForMarketValueForOneZip;
	}

	public int getValidResidenceNumForLivableAreaForOneZip() {
		return validResidenceNumForLivableAreaForOneZip;
	}

	public double getTotalMarketValueForOneZip() {
		return totalMarketValueForOneZip;
	}

	public double getTotalLivableAreaForOneZip() {
		return totalLivableAreaForOneZip;
	}

	public void addMarketValue(double marketValueNum) {
		totalMarketValueForOneZip += marketValueNum;
		validResidenceNumForMarketValueForOneZip++;
	}

	public void addLivableArea(double livableAreaNum) {
		totalLivableAreaForOneZip += livableAreaNum;
		validResidenceNumForLivableAreaForOneZip++;
	}

	public String toString() {
		return String.format("%d %d %g %g", validResidenceNumForMarketValueForOneZip,
				validResidenceNumForLivableAreaForOneZip, totalMarketValueForOneZip, totalLivableAreaForOneZip);
	}
}
