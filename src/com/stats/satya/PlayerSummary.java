package com.stats.satya;

public class PlayerSummary {
	private PlayerBattingStat testBatSummary, odiBatSummary, t20iBatSummary, iplBatSummary;
	private PlayerBowlingStat testBowlSummary, odiBowlSummary, t20iBowlSummary, iplBowlSummary;
	private String playerName;

	public PlayerSummary(PlayerBattingStat testBatSummary, PlayerBattingStat odiBatSummary,
			PlayerBattingStat t20iBatSummary, PlayerBattingStat iplBatSummary, PlayerBowlingStat testBowlSummary,
			PlayerBowlingStat odiBowlSummary, PlayerBowlingStat t20iBowlSummary, PlayerBowlingStat iplBowlSummary,
			String playerName) {
		this.testBatSummary = testBatSummary;
		this.odiBatSummary = odiBatSummary;
		this.iplBatSummary = iplBatSummary;
		this.testBowlSummary = testBowlSummary;
		this.odiBowlSummary = odiBowlSummary;
		this.iplBowlSummary = iplBowlSummary;
		this.t20iBatSummary = t20iBatSummary;
		this.t20iBowlSummary = t20iBowlSummary;
		this.playerName = playerName;
	}

	PlayerSummary() {
	}

	public PlayerBattingStat getTestBatSummary() {
		return testBatSummary;
	}

	public void setTestBatSummary(PlayerBattingStat testBatSummary) {
		this.testBatSummary = testBatSummary;
	}

	public PlayerBattingStat getOdiBatSummary() {
		return odiBatSummary;
	}

	public void setOdiBatSummary(PlayerBattingStat odiBatSummary) {
		this.odiBatSummary = odiBatSummary;
	}

	public PlayerBattingStat getT20iBatSummary() {
		return t20iBatSummary;
	}

	public void setT20iBatSummary(PlayerBattingStat t20iBatSummary) {
		this.t20iBatSummary = t20iBatSummary;
	}

	public PlayerBattingStat getIplBatSummary() {
		return iplBatSummary;
	}

	public void setIplBatSummary(PlayerBattingStat iplBatSummary) {
		this.iplBatSummary = iplBatSummary;
	}

	public PlayerBowlingStat getTestBowlSummary() {
		return testBowlSummary;
	}

	public void setTestBowlSummary(PlayerBowlingStat testBowlSummary) {
		this.testBowlSummary = testBowlSummary;
	}

	public PlayerBowlingStat getOdiBowlSummary() {
		return odiBowlSummary;
	}

	public void setOdiBowlSummary(PlayerBowlingStat odiBowlSummary) {
		this.odiBowlSummary = odiBowlSummary;
	}

	public PlayerBowlingStat getT20iBowlSummary() {
		return t20iBowlSummary;
	}

	public void setT20iBowlSummary(PlayerBowlingStat t20iBowlSummary) {
		this.t20iBowlSummary = t20iBowlSummary;
	}

	public PlayerBowlingStat getIplBowlSummary() {
		return iplBowlSummary;
	}

	public void setIplBowlSummary(PlayerBowlingStat iplBowlSummary) {
		this.iplBowlSummary = iplBowlSummary;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	@Override
	public String toString() {
		return "PlayerSummary [testBatSummary=" + testBatSummary + ", odiBatSummary=" + odiBatSummary
				+ ", t20iBatSummary=" + t20iBatSummary + ", iplBatSummary=" + iplBatSummary + ", testBowlSummary="
				+ testBowlSummary + ", odiBowlSummary=" + odiBowlSummary + ", t20iBowlSummary=" + t20iBowlSummary
				+ ", iplBowlSummary=" + iplBowlSummary + ", playerName=" + playerName + "]\n";
	}
}