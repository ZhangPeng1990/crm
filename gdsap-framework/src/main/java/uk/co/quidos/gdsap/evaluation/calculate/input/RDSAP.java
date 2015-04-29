package uk.co.quidos.gdsap.evaluation.calculate.input;


public class RDSAP {

	private double standNumOfOcc;
	private int judgeType;
	private boolean maingasAvilable;
	
	private CalOutput dataOutput;
	
	public double getStandNumOfOcc() {
		return standNumOfOcc;
	}

	public void setStandNumOfOcc(double standNumOfOcc) {
		this.standNumOfOcc = standNumOfOcc;
	}

	public int getJudgeType() {
		return judgeType;
	}

	public void setJudgeType(int judgeType) {
		this.judgeType = judgeType;
	}

	public boolean isMaingasAvilable() {
		return maingasAvilable;
	}

	public void setMaingasAvilable(boolean maingasAvilable) {
		this.maingasAvilable = maingasAvilable;
	}

	public CalOutput getDataOutput() {
		return dataOutput;
	}

	public void setDataOutput(CalOutput dataOutput) {
		this.dataOutput = dataOutput;
	}
}
