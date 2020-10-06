package com.stats.satya;

import java.util.Comparator;

public class  SortByMostTestWickets implements Comparator<PlayerSummary> {
	@Override
	public int compare(PlayerSummary arg0, PlayerSummary arg1) {
		if (arg0.getTestBowlSummary() == null && arg1.getTestBowlSummary() != null)
			return -1;
		if (arg0.getTestBowlSummary() == null && arg1.getTestBowlSummary() == null)
			return 0;
		if (arg0.getTestBowlSummary() != null && arg1.getTestBowlSummary() == null)
			return 1;

		return (int) (arg1.getTestBowlSummary().getWickets() - arg0.getTestBowlSummary().getWickets());
	}
}