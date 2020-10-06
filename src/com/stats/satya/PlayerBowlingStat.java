package com.stats.satya;

public class PlayerBowlingStat {
	private int totMatches, totInnings, ballsBowled, runsGiven, wickets, fiveWickets, tenWickets;
	private float bowlingEconomyRate, bowlingAvg, bowlingStrikeRate;
	private String bestBowlingInn, bestBowlingMatch;

	PlayerBowlingStat(int totMatches, int totInnings, int ballsBowled, int runsGiven, int wickets,
			String bestBowlingInn, String bestBowlingMatch, float bowlingEconomyRate, float bowlingAvg,
			float bowlingStrikeRate, int fiveWickets, int tenWickets) {
		this.totMatches = totMatches;
		this.totInnings = totInnings;
		this.ballsBowled = ballsBowled;
		this.runsGiven = runsGiven;
		this.wickets = wickets;
		this.fiveWickets = fiveWickets;
		this.tenWickets = tenWickets;
		this.bowlingEconomyRate = bowlingEconomyRate;
		this.bowlingAvg = bowlingAvg;
		this.bowlingStrikeRate = bowlingStrikeRate;
		this.bestBowlingInn = bestBowlingInn;
		this.bestBowlingMatch = bestBowlingMatch;
	}

	PlayerBowlingStat() {
	}

	public int getTotMatches() {
		return totMatches;
	}

	public void setTotMatches(int totMatches) {
		this.totMatches = totMatches;
	}

	public int getTotInnings() {
		return totInnings;
	}

	public void setTotInnings(int totInnings) {
		this.totInnings = totInnings;
	}

	public int getBallsBowled() {
		return ballsBowled;
	}

	public void setBallsBowled(int ballsBowled) {
		this.ballsBowled = ballsBowled;
	}

	public int getRunsGiven() {
		return runsGiven;
	}

	public void setRunsGiven(int runsGiven) {
		this.runsGiven = runsGiven;
	}

	public int getWickets() {
		return wickets;
	}

	public void setWickets(int wickets) {
		this.wickets = wickets;
	}

	public int getFiveWickets() {
		return fiveWickets;
	}

	public void setFiveWickets(int fiveWickets) {
		this.fiveWickets = fiveWickets;
	}

	public int getTenWickets() {
		return tenWickets;
	}

	public void setTenWickets(int tenWickets) {
		this.tenWickets = tenWickets;
	}

	public float getBowlingEconomyRate() {
		return bowlingEconomyRate;
	}

	public void setBowlingEconomyRate(float bowlingEconomyRate) {
		this.bowlingEconomyRate = bowlingEconomyRate;
	}

	public float getBowlingAvg() {
		return bowlingAvg;
	}

	public void setBowlingAvg(float bowlingAvg) {
		this.bowlingAvg = bowlingAvg;
	}

	public float getBowlingStrikeRate() {
		return bowlingStrikeRate;
	}

	public void setBowlingStrikeRate(float bowlingStrikeRate) {
		this.bowlingStrikeRate = bowlingStrikeRate;
	}

	public String getBestBowlingInn() {
		return bestBowlingInn;
	}

	public void setBestBowlingInn(String bestBowlingInn) {
		this.bestBowlingInn = bestBowlingInn;
	}

	public String getBestBowlingMatch() {
		return bestBowlingMatch;
	}

	public void setBestBowlingMatch(String bestBowlingMatch) {
		this.bestBowlingMatch = bestBowlingMatch;
	}

	@Override
	public String toString() {
		return "PlayerBowlingStat [totMatches=" + totMatches + ", totInnings=" + totInnings + ", ballsBowled="
				+ ballsBowled + ", runsGiven=" + runsGiven + ", wickets=" + wickets + ", fiveWickets=" + fiveWickets
				+ ", tenWickets=" + tenWickets + ", bowlingEconomyRate=" + bowlingEconomyRate + ", bowlingAvg="
				+ bowlingAvg + ", bowlingStrikeRate=" + bowlingStrikeRate + ", bestBowlingInn=" + bestBowlingInn
				+ ", bestBowlingMatch=" + bestBowlingMatch + "]";
	}
}
