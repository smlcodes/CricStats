package com.stats.satya;

import java.util.Comparator;

public class SortByMostTestCenturies implements Comparator<PlayerSummary> {
	@Override
	public int compare(PlayerSummary arg0, PlayerSummary arg1) {
		if (arg0.getTestBatSummary() == null && arg1.getTestBatSummary() != null)
			return -1;
		if (arg0.getTestBatSummary() == null && arg1.getTestBatSummary() == null)
			return 0;
		if (arg0.getTestBatSummary() != null && arg1.getTestBatSummary() == null)
			return 1;

		return (int) (arg1.getTestBatSummary().getHundreds() - arg0.getTestBatSummary().getHundreds());
	}
}
