package model;

public class ArrivalDTO {
	private String station;
	private String trainLine;
	private String direction;
	private String arrivalMsg;

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getTrainLine() {
		return trainLine;
	}

	public void setTrainLine(String trainLine) {
		this.trainLine = trainLine;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getArrivalMsg() {
		return arrivalMsg;
	}

	public void setArrivalMsg(String arrivalMsg) {
		this.arrivalMsg = arrivalMsg;
	}
}