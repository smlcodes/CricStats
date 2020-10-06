package com.stats.satya;

public class PlayerBattingStat {
	private int totMatches, totInnings, notOuts, runsScored, highestScore, ballsFaced, hundreds, doubleHundreds,
	fifties, fours, sixes;
private float battingAvg, battingStrikeRate;

PlayerBattingStat(int totMatches, int totInnings, int notOuts, int runsScored, int highestScore, float battingAvg,
	int ballsFaced, float battingStrikeRate, int hundreds, int doubleHundreds, int fifties, int fours,
	int sixes) {
this.totMatches = totMatches;
this.totInnings = totInnings;
this.notOuts = notOuts;
this.runsScored = runsScored;
this.highestScore = highestScore;
this.ballsFaced = ballsFaced;
this.hundreds = hundreds;
this.doubleHundreds = doubleHundreds;
this.fifties = fifties;
this.fours = fours;
this.sixes = sixes;
this.battingAvg = battingAvg;
this.battingStrikeRate = battingStrikeRate;
}

PlayerBattingStat() {
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

public int getNotOuts() {
return notOuts;
}

public void setNotOuts(int notOuts) {
this.notOuts = notOuts;
}

public int getRunsScored() {
return runsScored;
}

public void setRunsScored(int runsScored) {
this.runsScored = runsScored;
}

public int getHighestScore() {
return highestScore;
}

public void setHighestScore(int highestScore) {
this.highestScore = highestScore;
}

public int getBallsFaced() {
return ballsFaced;
}

public void setBallsFaced(int ballsFaced) {
this.ballsFaced = ballsFaced;
}

public int getHundreds() {
return hundreds;
}

public void setHundreds(int hundreds) {
this.hundreds = hundreds;
}

public int getDoubleHundreds() {
return doubleHundreds;
}

public void setDoubleHundreds(int doubleHundreds) {
this.doubleHundreds = doubleHundreds;
}

public int getFifties() {
return fifties;
}

public void setFifties(int fifties) {
this.fifties = fifties;
}

public int getFours() {
return fours;
}

public void setFours(int fours) {
this.fours = fours;
}

public int getSixes() {
return sixes;
}

public void setSixes(int sixes) {
this.sixes = sixes;
}

public float getBattingAvg() {
return battingAvg;
}

public void setBattingAvg(float battingAvg) {
this.battingAvg = battingAvg;
}

public float getBattingStrikeRate() {
return battingStrikeRate;
}

public void setBattingStrikeRate(float battingStrikeRate) {
this.battingStrikeRate = battingStrikeRate;
}

@Override
public String toString() {
return "PlayerBattingStat [totMatches=" + totMatches + ", totInnings=" + totInnings + ", notOuts=" + notOuts
		+ ", runsScored=" + runsScored + ", highestScore=" + highestScore + ", ballsFaced=" + ballsFaced
		+ ", hundreds=" + hundreds + ", doubleHundreds=" + doubleHundreds + ", fifties=" + fifties + ", fours="
		+ fours + ", sixes=" + sixes + ", battingAvg=" + battingAvg + ", battingStrikeRate=" + battingStrikeRate
		+ "]";
}
}
