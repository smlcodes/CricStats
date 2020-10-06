package com.stats.satya;

public class ODIPlayerSummary {
	private PlayerBattingStat odiBatSummary;
	private PlayerBowlingStat odiBowlSummary;
	private String playerName;
	private String country;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public ODIPlayerSummary(PlayerBattingStat odiBatSummary, PlayerBowlingStat odiBowlSummary, 	String playerName, String country ) {
		this.odiBatSummary = odiBatSummary; 
		this.odiBowlSummary = odiBowlSummary; 
		this.playerName = playerName;
		this.country = country;
	}

	ODIPlayerSummary() {
	}

	public PlayerBattingStat getOdiBatSummary() {
		return odiBatSummary;
	}

	public void setOdiBatSummary(PlayerBattingStat odiBatSummary) {
		this.odiBatSummary = odiBatSummary;
	}

	public PlayerBowlingStat getOdiBowlSummary() {
		return odiBowlSummary;
	}

	public void setOdiBowlSummary(PlayerBowlingStat odiBowlSummary) {
		this.odiBowlSummary = odiBowlSummary;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	@Override
	public String toString() {
		return "ODIPlayerSummary [odiBatSummary=" + odiBatSummary + ", odiBowlSummary=" + odiBowlSummary
				+ ", playerName=" + playerName + " , country: "+country+"]";
	}
 



}