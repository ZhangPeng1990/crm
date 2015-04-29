package uk.co.quidos.gdsap.evaluation.calculate.input;

public class OccupancyParameters {

	private Integer occupants;
	private String averageHoursOfHeating;
	private Integer thermostatSetting;
	private Integer unheatedRooms;
	private boolean recommendOutsideDrying;
	private boolean recommendShowers;
	
	
	public Integer getOccupants() {
		return occupants;
	}
	public void setOccupants(Integer occupants) {
		this.occupants = occupants;
	}
	public String getAverageHoursOfHeating() {
		return averageHoursOfHeating;
	}
	public void setAverageHoursOfHeating(String averageHoursOfHeating) {
		this.averageHoursOfHeating = averageHoursOfHeating;
	}
	public Integer getThermostatSetting() {
		return thermostatSetting;
	}
	public void setThermostatSetting(Integer thermostatSetting) {
		this.thermostatSetting = thermostatSetting;
	}
	public Integer getUnheatedRooms() {
		return unheatedRooms;
	}
	public void setUnheatedRooms(Integer unheatedRooms) {
		this.unheatedRooms = unheatedRooms;
	}
	public boolean isRecommendOutsideDrying() {
		return recommendOutsideDrying;
	}
	public void setRecommendOutsideDrying(boolean recommendOutsideDrying) {
		this.recommendOutsideDrying = recommendOutsideDrying;
	}
	public boolean isRecommendShowers() {
		return recommendShowers;
	}
	public void setRecommendShowers(boolean recommendShowers) {
		this.recommendShowers = recommendShowers;
	}
}
