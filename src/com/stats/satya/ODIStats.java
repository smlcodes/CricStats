package com.stats.satya;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class ODIStats {
	public static void main(String[] args) {
		String[] cricketProfiles = new String[4000];
		String cricBuzzProfileString = "https://www.cricbuzz.com/profiles/";
		for (int i = 0; i < 4000; i++) {
			cricketProfiles[i] = cricBuzzProfileString + (25 + i);
		}
		long startTime = System.currentTimeMillis();
		
		List<ODIPlayerSummary> cricketersList = readURLsAndBuildPlayersList(cricketProfiles);
		long endTime = System.currentTimeMillis();
		System.out.println("\nTotal time taken (to build the list) = " + (endTime - startTime) + " ms\n");

		startTime = System.currentTimeMillis();
		printRecordList(cricketersList);
		endTime = System.currentTimeMillis();
		System.out.println("\nTotal time taken (to print data and stats) = " + (endTime - startTime) + " ms\n");
	}

	static List<ODIPlayerSummary> readURLsAndBuildPlayersList(String[] cricketProfiles) {
		List<Integer> testNumbers = new ArrayList<>();
		List<Integer> odiNumbers = new ArrayList<>();
		List<Integer> t20iNumbers = new ArrayList<>();
		List<Integer> iplNumbers = new ArrayList<>();
		List<ODIPlayerSummary> cricketersList = new ArrayList<>();

		for (int i = 0; i < cricketProfiles.length; i++) {
			try {
				BufferedReader buffer = new BufferedReader(
						new InputStreamReader(new URL(cricketProfiles[i]).openStream()));
				String inputLine;
				testNumbers.removeAll(testNumbers);
				odiNumbers.removeAll(odiNumbers);
				t20iNumbers.removeAll(t20iNumbers);
				iplNumbers.removeAll(iplNumbers);
				
				
				
				String playerName = "";
				String country="";
				while ((inputLine = buffer.readLine()) != null) {
					String[] words = inputLine.split("[ !<>=\"/,.]");
					int posStart = 0;
					int posEnd = 0;
					String matchType = "";

					for (int x = 0; x < words.length; x++) {
						if (words[x].isEmpty() || words[x] == null || words[x].equals(""))
							continue;
						// System.out.print(x + "->" + words[x] + " ");
						if (words[x].equals("strong")
								&& (words[x + 2].equals("strong") || words[x + 3].equals("strong"))) {
							if (words[x + 1].equals("Test")) {
								posStart = x;
								matchType = "Test";
								/*
								 * System.out.println("In test profile " + i + ", start-pos = " + posStart);
								 * System.out.println("End pos = " + posEnd);
								 */
							} else if (words[x + 1].equals("ODI")) {
								posStart = x;
								matchType = "ODI";
								/*
								 * System.out.println("In ODI profile " + i + ", start-pos = " + posStart);
								 * System.out.println("End pos = " + posEnd);
								 */
							} else if (words[x + 1].equals("T20I")) {
								posStart = x;
								matchType = "T20I";
								/*
								 * System.out.println("In T20I profile " + i + ", start-pos = " + posStart);
								 * System.out.println("End pos = " + posEnd);
								 */
							} else if (words[x + 1].equals("IPL")) {
								posStart = x;
								matchType = "IPL";
								/*
								 * System.out.println("In IPL profile " + i + ", start-pos = " + posStart);
								 * System.out.println("End pos = " + posEnd);
								 */
							}
						}

						if (words[x].equals("tbody") && words[x + 4].equals("table")
								&& (words[x + 7].equals("div") || words[x + 8].equals("div"))) {
							posEnd = x;
							// System.out.println("End pos = " + posEnd);
						}
						if (words[x].equals("cb-font-40")) {
							playerName = words[x + 1] + " " + words[x + 2];
							if (!words[x + 3].equals("h1"))
								playerName += (" " + words[x + 3]);
							if (!words[x + 4].equals("h1"))
								playerName += (" " + words[x + 4]);
						}
						
						if (words[x].equals("cb-font-18")) {
							country = words[x + 2];
							if (!words[x + 3].equals("h3"))
								country += (" " + words[x + 3]);
							if (!words[x + 4].equals("h3"))
								country += (" " + words[x + 4]);
						}
						


						if (StringUtils.isNumeric(words[x]) && x > posStart && posStart > 0 && words[x].length() < 10
								&& posEnd >= 0 && posStart > posEnd) {
							if (matchType.equals("Test"))
								testNumbers.add(Integer.parseInt(words[x]));
							else if (matchType.equals("ODI"))
								odiNumbers.add(Integer.parseInt(words[x]));
							else if (matchType.equals("T20I"))
								t20iNumbers.add(Integer.parseInt(words[x]));
							else if (matchType.equals("IPL"))
								iplNumbers.add(Integer.parseInt(words[x]));
						}
					}
				}
				ODIPlayerSummary playerSummary = buildODIPlayerObject(testNumbers, odiNumbers, t20iNumbers, iplNumbers,
						playerName,country );
				PlayerBattingStat bat = playerSummary.getOdiBatSummary();
				PlayerBowlingStat bow = playerSummary.getOdiBowlSummary();
				if(bat!=null && bow!=null) {

					
				System.out.println("\t "+bat.getTotMatches()+" \t "+bat.getRunsScored()+" \t "+bat.getFifties()+
						" \t "+bat.getHundreds()+" \t "+bat.getBattingAvg()+" \t \t"+bat.getBattingStrikeRate()+
						" \t \t"+bat.getHighestScore()+" \t "+bow.getWickets()+" \t "+bow.getFiveWickets()+
						" \t "+bow.getBowlingAvg()+"\t \t "+bow.getBowlingStrikeRate()+"\t \t"+bow.getBowlingEconomyRate()+"\t \t "+playerSummary.getCountry()+"\t \t "+playerSummary.getPlayerName());
				}
				
				
				cricketersList.add(playerSummary);
				buffer.close();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				//System.out.println("Cached..."+cricketProfiles[i]);
			}
		}
		// printRecord(testNumbers, odiNumbers, t20iNumbers, iplNumbers);
		return cricketersList;
	};

	private static boolean isValidTestPlayer(PlayerSummary playerSummary) {
		if (playerSummary.getPlayerName() == null || playerSummary.getPlayerName().isEmpty())
			return false;
		if (playerSummary.getTestBatSummary() == null && playerSummary.getTestBowlSummary() == null)
			return false;
		return true;
	}

	private static boolean isValidT20iPlayer(PlayerSummary playerSummary) {
		if (playerSummary.getPlayerName() == null || playerSummary.getPlayerName().isEmpty())
			return false;
		if (playerSummary.getT20iBatSummary() == null && playerSummary.getT20iBowlSummary() == null)
			return false;
		return true;
	}

	private static void printSomeStats(List<PlayerSummary> cricketersList) {

		System.out.println("\n **** Most Test Centuries ****\n");
		Collections.sort(cricketersList, new SortByMostTestCenturies());
		System.out.println(String.format("%25s", "Player Name") + String.format("%15s", "No. of 100s")
				+ String.format("%10s", "Tests") + String.format("%10s", "Innings"));
		for (PlayerSummary playerSummary : cricketersList) {
			if (isValidTestPlayer(playerSummary) && hasBattingStatistics(playerSummary, "TEST"))
				System.out.println(String.format("%25s", playerSummary.getPlayerName())
						+ String.format("%15s", playerSummary.getTestBatSummary().getHundreds())
						+ String.format("%10s", playerSummary.getTestBatSummary().getTotMatches())
						+ String.format("%10s", playerSummary.getTestBatSummary().getTotInnings()));
		}

		System.out.println("\n **** Most Test Wickets ****\n");
		Collections.sort(cricketersList, new SortByMostTestWickets());
		System.out.println(String.format("%25s", "Player Name") + String.format("%20s", "No. of Wickets")
				+ String.format("%10s", "Tests") + String.format("%10s", "Innings"));
		for (PlayerSummary playerSummary : cricketersList) {
			if (isValidTestPlayer(playerSummary) && hasBowlingStatistics(playerSummary, "TEST"))
				System.out.println(String.format("%25s", playerSummary.getPlayerName())
						+ String.format("%20s", playerSummary.getTestBowlSummary().getWickets())
						+ String.format("%10s", playerSummary.getTestBowlSummary().getTotMatches())
						+ String.format("%10s", playerSummary.getTestBowlSummary().getTotInnings()));
		}

		System.out.println("\n **** Highest T20i Batting Average ****\n");
		Collections.sort(cricketersList, new SortByHighestT20BattingAvg());
		System.out.println(String.format("%25s", "Player Name") + String.format("%20s", "Batting Average")
				+ String.format("%10s", "Matches") + String.format("%10s", "Innings") + String.format("%10s", "Runs"));
		for (PlayerSummary playerSummary : cricketersList) {
			if (isValidT20iPlayer(playerSummary) && hasBattingStatistics(playerSummary, "T20I"))
				System.out.println(String.format("%25s", playerSummary.getPlayerName())
						+ String.format("%20s", playerSummary.getT20iBatSummary().getBattingAvg())
						+ String.format("%10s", playerSummary.getT20iBatSummary().getTotMatches())
						+ String.format("%10s", playerSummary.getT20iBatSummary().getTotInnings())
						+ String.format("%10s", playerSummary.getT20iBatSummary().getRunsScored()));
		}
	}

	private static boolean hasBattingStatistics(PlayerSummary playerSummary, String matchType) {
		if (matchType.equals("TEST") && playerSummary.getTestBatSummary() != null)
			return true;
		if (matchType.equals("ODI") && playerSummary.getOdiBatSummary() != null)
			return true;
		if (matchType.equals("T20I") && playerSummary.getT20iBatSummary() != null)
			return true;
		if (matchType.equals("IPL") && playerSummary.getIplBatSummary() != null)
			return true;
		return false;
	}

	private static boolean hasBowlingStatistics(PlayerSummary playerSummary, String matchType) {
		if (matchType.equals("TEST") && playerSummary.getTestBowlSummary() != null)
			return true;
		if (matchType.equals("ODI") && playerSummary.getOdiBowlSummary() != null)
			return true;
		if (matchType.equals("T20I") && playerSummary.getT20iBowlSummary() != null)
			return true;
		if (matchType.equals("IPL") && playerSummary.getIplBowlSummary() != null)
			return true;
		return false;
	}

	private static void printRecordList(List<ODIPlayerSummary> cricketersList) {
		
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("\t MATS \t RUNS \t 50's \t 100s \t AVG. \t \t STR. \t \t HIGH \t WICK \t 5WIK \t AVG. \t \t STR. \t \t ECON  \t \t Coun \t \t NAME ");
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
		
		List<PlayerInfo> playersList = new ArrayList<PlayerInfo>();
		
		
		for (ODIPlayerSummary p : cricketersList) {
			PlayerBattingStat bat = p.getOdiBatSummary();
			PlayerBowlingStat bow = p.getOdiBowlSummary();
			PlayerInfo player = new PlayerInfo();
			
			if(bat!=null && bow!=null) {

				
			System.out.println("\t "+bat.getTotMatches()+" \t "+bat.getRunsScored()+" \t "+bat.getFifties()+
					" \t "+bat.getHundreds()+" \t "+bat.getBattingAvg()+" \t \t"+bat.getBattingStrikeRate()+
					" \t \t"+bat.getHighestScore()+" \t "+bow.getWickets()+" \t "+bow.getFiveWickets()+
					" \t "+bow.getBowlingAvg()+"\t \t "+bow.getBowlingStrikeRate()+"\t \t"+bow.getBowlingEconomyRate()+"\t \t "+p.getCountry()+"\t \t "+p.getPlayerName());
		
			player.setName(p.getPlayerName());
			player.setCountry(p.getCountry());
			player.setMat(bat.getTotMatches());
			player.setRuns(bat.getRunsScored());
			player.setFiftys(bat.getFifties());
			player.setHundrds(bat.getHundreds());
			player.setBatAvg(bat.getBattingAvg());
			player.setBatStr(bat.getBattingStrikeRate());
			player.setHigh(bat.getHighestScore());
			player.setWik(bow.getWickets());
			player.setWik5(bow.getFiveWickets());
			player.setBowAvg(bow.getBowlingAvg());
			player.setBowStr(bow.getBowlingStrikeRate());
			player.setEcon(bow.getBowlingEconomyRate());
			player.setBatIngs(bat.getTotInnings());
			player.setNotouts(bat.getNotOuts());
			player.setBowIngs(bow.getTotInnings());
			player.setBowBest(bow.getBestBowlingInn());
			
			playersList.add(player);
			}
		}
		
		if (playersList.size() > 0) {
			try {
				ExcelGenerator.playersExcelGenerator(playersList, "cricinfostas");
			} catch (Exception e) {
				System.out.println(" Excel Genration Failed .....");
				e.printStackTrace();
			}
		}

	}

	private static ODIPlayerSummary buildODIPlayerObject(List<Integer> testNumbers, List<Integer> odiNumbers,
			List<Integer> t20iNumbers, List<Integer> iplNumbers, String playerName, String country) {

		float odiBatAverage = 0;
		float odiStrikeRate = 0;
		float odiBowlEconRate = 0;
		float odiBowlAverage = 0;
		float odiBowlStrikeRate = 0;
		String odiBestBowlInn = "";
		String odiBestBowlMatch = "";

		PlayerBattingStat odiBattingStat = null;
		PlayerBowlingStat odiBowlingStat = null;

		if (!odiNumbers.isEmpty()) {
			odiBatAverage = odiNumbers.get(5) + (float) odiNumbers.get(6) / 100;
			odiStrikeRate = odiNumbers.get(8) + (float) odiNumbers.get(9) / 100;
			odiBatAverage = (odiNumbers.get(6) < 10)
					? correctAverageIfNeeded(odiNumbers.get(3), (odiNumbers.get(1) - odiNumbers.get(2)))
					: odiBatAverage;
			odiStrikeRate = (odiNumbers.get(9) < 10)
					? correctAverageIfNeeded(odiNumbers.get(3) * 100, odiNumbers.get(7))
					: odiStrikeRate;
					
					odiBatAverage =		(float)(Math.round( odiBatAverage * 100.0) / 100.0);	
					odiStrikeRate =		(float)(Math.round( odiStrikeRate * 100.0) / 100.0);
					
			odiBattingStat = new PlayerBattingStat(odiNumbers.get(0), odiNumbers.get(1), odiNumbers.get(2),
					odiNumbers.get(3), odiNumbers.get(4), odiBatAverage, odiNumbers.get(7), odiStrikeRate,
					odiNumbers.get(10), odiNumbers.get(11), odiNumbers.get(12), odiNumbers.get(13), odiNumbers.get(14));
			

			

			if (odiNumbers.size() > 20) {
				odiBowlEconRate = odiNumbers.get(24) + (float) odiNumbers.get(25) / 100;
				odiBowlAverage = odiNumbers.get(26) + (float) odiNumbers.get(27) / 100;
				odiBowlStrikeRate = odiNumbers.get(28) + (float) odiNumbers.get(29) / 100;
				odiBowlEconRate = (odiNumbers.get(25) < 10)
						? correctAverageIfNeeded(odiNumbers.get(18) * 6, odiNumbers.get(17))
						: odiBowlEconRate;
				odiBowlAverage = (odiNumbers.get(27) < 10)
						? correctAverageIfNeeded(odiNumbers.get(18), odiNumbers.get(19))
						: odiBowlAverage;
				odiBowlStrikeRate = (odiNumbers.get(29) < 10)
						? correctAverageIfNeeded(odiNumbers.get(17), odiNumbers.get(19))
						: odiBowlStrikeRate;

				odiBestBowlInn = ""+odiNumbers.get(21)+"-" + odiNumbers.get(20);
				odiBestBowlMatch = ""+odiNumbers.get(23)+"-" + odiNumbers.get(22);
				
				
				odiBowlEconRate =		(float)(Math.round( odiBowlEconRate * 100.0) / 100.0);	
				odiBowlAverage =		(float)(Math.round( odiBowlAverage * 100.0) / 100.0);	
				odiBowlStrikeRate =		(float)(Math.round( odiBowlStrikeRate * 100.0) / 100.0);
				
				
				odiBowlingStat = new PlayerBowlingStat(odiNumbers.get(15), odiNumbers.get(16), odiNumbers.get(17),
						odiNumbers.get(18), odiNumbers.get(19), odiBestBowlInn, odiBestBowlMatch, odiBowlEconRate,
						odiBowlAverage, odiBowlStrikeRate, odiNumbers.get(30), odiNumbers.get(31));
			} else {
				odiBowlingStat = null;
			}
		} else {
			odiBattingStat = null;
			odiBowlingStat = null;
		}

		return new ODIPlayerSummary(odiBattingStat, odiBowlingStat, playerName, country);
	}

	private static float correctAverageIfNeeded(Integer runs, int innings) {
		float calAverage = 0;
		calAverage = (innings != 0) ? (float) runs / innings : 0;
		float calRoundedAvg = (float) (Math.round(calAverage * 100.0) / 100.0);
		return calRoundedAvg;
	}
	/*
	 * private static void printRecord(List<Integer> testNumbers, List<Integer>
	 * odiNumbers, List<Integer> t20iNumbers, List<Integer> iplNumbers) {
	 * 
	 * System.out.println("****************");
	 * System.out.println("Test Numbers size = " + testNumbers.size());
	 * System.out.println("ODI Numbers size = " + odiNumbers.size());
	 * System.out.println("T20I Numbers size = " + t20iNumbers.size());
	 * System.out.println("IPL Numbers size = " + iplNumbers.size());
	 * System.out.println("****************");
	 * 
	 * if(!testNumbers.isEmpty()) System.out.println("\nTest Numbers"); for(Integer
	 * num : testNumbers) System.out.print(num + "\t");
	 * 
	 * if(!odiNumbers.isEmpty()) System.out.println("\nODI Numbers"); for(Integer
	 * num : odiNumbers) System.out.print(num + "\t");
	 * 
	 * if(!t20iNumbers.isEmpty()) System.out.println("\nT20I Numbers"); for(Integer
	 * num : t20iNumbers) System.out.print(num + "\t");
	 * 
	 * if(!iplNumbers.isEmpty()) System.out.println("\nIPL Numbers"); for(Integer
	 * num : iplNumbers) System.out.print(num + "\t"); System.out.println(); }
	 */
}
