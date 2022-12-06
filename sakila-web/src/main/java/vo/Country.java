package vo;

public class Country extends Object{
	// private 정보은닉
	private int countryId;
	private String country;
	private String lastUpdate;
	public int getCountryId() {
		return countryId;
	}
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
}
	
	/*
	public Country() { // 정보 은닉할때 여긴 하면안됨
		
		
		super(); //부모 생성자를 호출
		this.countryId = 0; //기본타입은 0으로 초기화
		this.country = null; //참조타입은 null로 초기화
		this.lastUpdate = null;
	}
	public int getCountryId() {
		return countryId;
	}
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	private String getLastUpdate() {
		return lastUpdate;
	}
	private void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
}
	
	public int getCountryId() { //캡슐화
		return this.countryId;
	}
	public void setCountryId(int countryId) {
		if(countryId <0) {
			return;
		}
		
		this.countryId = countryId;
	*/


