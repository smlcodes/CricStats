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

public class SingleThreadedStats {
	public static void main(String[] args) {
		String[] cricketProfiles = new String[150];
		String cricBuzzProfileString = "https://www.cricbuzz.com/profiles/";
		for (int i = 0; i < 150; i++) {
			cricketProfiles[i] = cricBuzzProfileString + (25 + i);
		}
		long startTime = System.currentTimeMillis();
		List<PlayerSummary> cricketersList = readURLsAndBuildPlayersList(cricketProfiles);
		long endTime = System.currentTimeMillis();
		System.out.println("\nTotal time taken (to build the list) = " + (endTime - startTime) + " ms\n");

		startTime = System.currentTimeMillis();
		printRecordList(cricketersList);
		//printSomeStats(cricketersList);
		endTime = System.currentTimeMillis();
		System.out.println("\nTotal time taken (to print data and stats) = " + (endTime - startTime) + " ms\n");
	}

	static List<PlayerSummary> readURLsAndBuildPlayersList(String[] cricketProfiles) {
		List<Integer> testNumbers = new ArrayList<>();
		List<Integer> odiNumbers = new ArrayList<>();
		List<Integer> t20iNumbers = new ArrayList<>();
		List<Integer> iplNumbers = new ArrayList<>();
		List<PlayerSummary> cricketersList = new ArrayList<>();

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
				while ((inputLine = buffer.readLine()) != null) {
					String[] words = inputLine.split("[ !<>=\"/,.]");
					int posStart = 0;
					int posEnd = 0;
					String matchType = "";

					for (int x = 0; x < words.length; x++) {
						if (words[x].isEmpty() || words[x] == null || words[x].equals(""))
							continue;
						//						System.out.print(x + "->" + words[x] + " ");
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
				PlayerSummary playerSummary = buildPlayerObject(testNumbers, odiNumbers, t20iNumbers, iplNumbers,
						playerName);
				cricketersList.add(playerSummary);
				buffer.close();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
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

	private static void printRecordList(List<PlayerSummary> cricketersList) {
		for (PlayerSummary playerSummary : cricketersList) {
			System.out.println(playerSummary);
		}
	}

	private static PlayerSummary buildPlayerObject(List<Integer> testNumbers, List<Integer> odiNumbers,
			List<Integer> t20iNumbers, List<Integer> iplNumbers, String playerName) {

		float testBatAverage = 0;
		float testStrikeRate = 0;
		float testBowlEconRate = 0;
		float testBowlAverage = 0;
		float testBowlStrikeRate = 0;
		String testBestBowlInn = "";
		String testBestBowlMatch = "";

		PlayerBattingStat testBattingStat = null;
		PlayerBowlingStat testBowlingStat = null;

		if (!testNumbers.isEmpty()) {
			testBatAverage = testNumbers.get(5) + (float) testNumbers.get(6) / 100;
			testStrikeRate = testNumbers.get(8) + (float) testNumbers.get(9) / 100;
			testBatAverage = (testNumbers.get(6) < 10)
					? correctAverageIfNeeded(testNumbers.get(3), (testNumbers.get(1) - testNumbers.get(2)))
							: testBatAverage;
					testStrikeRate = (testNumbers.get(9) < 10)
							? correctAverageIfNeeded(testNumbers.get(3) * 100, testNumbers.get(7))
									: testStrikeRate;

							testBattingStat = new PlayerBattingStat(testNumbers.get(0), testNumbers.get(1), testNumbers.get(2),
									testNumbers.get(3), testNumbers.get(4), testBatAverage, testNumbers.get(7), testStrikeRate,
									testNumbers.get(10), testNumbers.get(11), testNumbers.get(12), testNumbers.get(13),
									testNumbers.get(14));

							if (testNumbers.size() > 30) {
								testBowlEconRate = testNumbers.get(24) + (float) testNumbers.get(25) / 100;
								testBowlAverage = testNumbers.get(26) + (float) testNumbers.get(27) / 100;
								testBowlStrikeRate = testNumbers.get(28) + (float) testNumbers.get(29) / 100;
								testBowlEconRate = (testNumbers.get(25) < 10)
										? correctAverageIfNeeded(testNumbers.get(18) * 6, testNumbers.get(17))
												: testBowlEconRate;
										testBowlAverage = (testNumbers.get(27) < 10)
												? correctAverageIfNeeded(testNumbers.get(18), testNumbers.get(19))
														: testBowlAverage;
												testBowlStrikeRate = (testNumbers.get(29) < 10)
														? correctAverageIfNeeded(testNumbers.get(17), testNumbers.get(19))
																: testBowlStrikeRate;

														testBestBowlInn = "" + testNumbers.get(20) + "/" + testNumbers.get(21);
														testBestBowlMatch = "" + testNumbers.get(22) + "/" + testNumbers.get(23);
														testBowlingStat = new PlayerBowlingStat(testNumbers.get(15), testNumbers.get(16), testNumbers.get(17),
																testNumbers.get(18), testNumbers.get(19), testBestBowlInn, testBestBowlMatch, testBowlEconRate,
																testBowlAverage, testBowlStrikeRate, testNumbers.get(30), testNumbers.get(31));
							} else {
								testBowlingStat = null;
							}
		} else {
			testBattingStat = null;
			testBowlingStat = null;
		}

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
							odiBattingStat = new PlayerBattingStat(odiNumbers.get(0), odiNumbers.get(1), odiNumbers.get(2),
									odiNumbers.get(3), odiNumbers.get(4), odiBatAverage, odiNumbers.get(7), odiStrikeRate,
									odiNumbers.get(10), odiNumbers.get(11), odiNumbers.get(12), odiNumbers.get(13), odiNumbers.get(14));

							if (odiNumbers.size() > 30) {
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

														odiBestBowlInn = "" + odiNumbers.get(20) + "/" + odiNumbers.get(21);
														odiBestBowlMatch = "" + odiNumbers.get(22) + "/" + odiNumbers.get(23);
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

		float t20iBatAverage = 0;
		float t20iStrikeRate = 0;
		float t20iBowlEconRate = 0;
		float t20iBowlAverage = 0;
		float t20iBowlStrikeRate = 0;
		String t20iBestBowlInn = "";
		String t20iBestBowlMatch = "";

		PlayerBattingStat t20iBattingStat = null;
		PlayerBowlingStat t20iBowlingStat = null;
		if (!t20iNumbers.isEmpty()) {
			t20iBatAverage = t20iNumbers.get(5) + (float) t20iNumbers.get(6) / 100;
			t20iStrikeRate = t20iNumbers.get(8) + (float) t20iNumbers.get(9) / 100;
			t20iBatAverage = (t20iNumbers.get(6) < 10)
					? correctAverageIfNeeded(t20iNumbers.get(3), (t20iNumbers.get(1) - t20iNumbers.get(2)))
							: t20iBatAverage;
					t20iStrikeRate = (t20iNumbers.get(9) < 10)
							? correctAverageIfNeeded(t20iNumbers.get(3) * 100, t20iNumbers.get(7))
									: t20iStrikeRate;

							t20iBattingStat = new PlayerBattingStat(t20iNumbers.get(0), t20iNumbers.get(1), t20iNumbers.get(2),
									t20iNumbers.get(3), t20iNumbers.get(4), t20iBatAverage, t20iNumbers.get(7), t20iStrikeRate,
									t20iNumbers.get(10), t20iNumbers.get(11), t20iNumbers.get(12), t20iNumbers.get(13),
									t20iNumbers.get(14));
							if (t20iNumbers.size() > 30) {
								t20iBowlEconRate = t20iNumbers.get(24) + (float) t20iNumbers.get(25) / 100;
								t20iBowlAverage = t20iNumbers.get(26) + (float) t20iNumbers.get(27) / 100;
								t20iBowlStrikeRate = t20iNumbers.get(28) + (float) t20iNumbers.get(29) / 100;
								t20iBowlEconRate = (t20iNumbers.get(25) < 10)
										? correctAverageIfNeeded(t20iNumbers.get(18) * 6, t20iNumbers.get(17))
												: t20iBowlEconRate;
										t20iBowlAverage = (t20iNumbers.get(27) < 10)
												? correctAverageIfNeeded(t20iNumbers.get(18), t20iNumbers.get(19))
														: t20iBowlAverage;
												t20iBowlStrikeRate = (t20iNumbers.get(29) < 10)
														? correctAverageIfNeeded(t20iNumbers.get(17), t20iNumbers.get(19))
																: t20iBowlStrikeRate;

														t20iBestBowlInn = "" + t20iNumbers.get(20) + "/" + t20iNumbers.get(21);
														t20iBestBowlMatch = "" + t20iNumbers.get(22) + "/" + t20iNumbers.get(23);
														t20iBowlingStat = new PlayerBowlingStat(t20iNumbers.get(15), t20iNumbers.get(16), t20iNumbers.get(17),
																t20iNumbers.get(18), t20iNumbers.get(19), t20iBestBowlInn, t20iBestBowlMatch, t20iBowlEconRate,
																t20iBowlAverage, t20iBowlStrikeRate, t20iNumbers.get(30), t20iNumbers.get(31));
							} else {
								t20iBowlingStat = null;
							}
		} else {
			t20iBattingStat = null;
			t20iBowlingStat = null;
		}

		float iplBatAverage = 0;
		float iplStrikeRate = 0;
		float iplBowlEconRate = 0;
		float iplBowlAverage = 0;
		float iplBowlStrikeRate = 0;
		String iplBestBowlInn = "";
		String iplBestBowlMatch = "";

		PlayerBattingStat iplBattingStat = null;
		PlayerBowlingStat iplBowlingStat = null;

		if (!iplNumbers.isEmpty()) {
			iplBatAverage = iplNumbers.get(5) + (float) iplNumbers.get(6) / 100;
			iplStrikeRate = iplNumbers.get(8) + (float) iplNumbers.get(9) / 100;
			iplBatAverage = (iplNumbers.get(6) < 10)
					? correctAverageIfNeeded(iplNumbers.get(3), (iplNumbers.get(1) - iplNumbers.get(2)))
							: iplBatAverage;
					iplStrikeRate = (iplNumbers.get(9) < 10)
							? correctAverageIfNeeded(iplNumbers.get(3) * 100, iplNumbers.get(7))
									: iplStrikeRate;
							iplBattingStat = new PlayerBattingStat(iplNumbers.get(0), iplNumbers.get(1), iplNumbers.get(2),
									iplNumbers.get(3), iplNumbers.get(4), iplBatAverage, iplNumbers.get(7), iplStrikeRate,
									iplNumbers.get(10), iplNumbers.get(11), iplNumbers.get(12), iplNumbers.get(13), iplNumbers.get(14));

							if (iplNumbers.size() > 30) {
								iplBowlEconRate = iplNumbers.get(24) + (float) iplNumbers.get(25) / 100;
								iplBowlAverage = iplNumbers.get(26) + (float) iplNumbers.get(27) / 100;
								iplBowlStrikeRate = iplNumbers.get(28) + (float) iplNumbers.get(29) / 100;
								iplBowlEconRate = (iplNumbers.get(25) < 10)
										? correctAverageIfNeeded(iplNumbers.get(18) * 6, iplNumbers.get(17))
												: iplBowlEconRate;
										iplBowlAverage = (iplNumbers.get(27) < 10)
												? correctAverageIfNeeded(iplNumbers.get(18), iplNumbers.get(19))
														: iplBowlAverage;
												iplBowlStrikeRate = (iplNumbers.get(29) < 10)
														? correctAverageIfNeeded(iplNumbers.get(17), iplNumbers.get(19))
																: iplBowlStrikeRate;

														iplBestBowlInn = "" + iplNumbers.get(20) + "/" + iplNumbers.get(21);
														iplBestBowlMatch = "" + iplNumbers.get(22) + "/" + iplNumbers.get(23);
														iplBowlingStat = new PlayerBowlingStat(iplNumbers.get(15), iplNumbers.get(16), iplNumbers.get(17),
																iplNumbers.get(18), iplNumbers.get(19), iplBestBowlInn, iplBestBowlMatch, iplBowlEconRate,
																iplBowlAverage, iplBowlStrikeRate, iplNumbers.get(30), iplNumbers.get(31));
							} else {
								iplBowlingStat = null;
							}
		} else {
			iplBattingStat = null;
			iplBowlingStat = null;
		}
		return new PlayerSummary(testBattingStat, odiBattingStat, t20iBattingStat, iplBattingStat, testBowlingStat,
				odiBowlingStat, t20iBowlingStat, iplBowlingStat, playerName);
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
