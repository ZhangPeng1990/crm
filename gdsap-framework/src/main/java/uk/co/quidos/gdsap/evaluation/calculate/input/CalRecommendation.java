package uk.co.quidos.gdsap.evaluation.calculate.input;

import java.util.Collection;

public class CalRecommendation {

	private Collection<CalImprovement> improvements;
	private Collection<CalImprovement> improvements_GDIP;

	public Collection<CalImprovement> getImprovements() {
		return improvements;
	}

	public void setImprovements(Collection<CalImprovement> improvements) {
		this.improvements = improvements;
	}

	public Collection<CalImprovement> getImprovements_GDIP() {
		return improvements_GDIP;
	}

	public void setImprovements_GDIP(Collection<CalImprovement> improvements_GDIP) {
		this.improvements_GDIP = improvements_GDIP;
	}
}
