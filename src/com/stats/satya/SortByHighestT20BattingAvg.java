package com.stats.satya;

import java.util.Comparator;

public class SortByHighestT20BattingAvg implements Comparator<PlayerSummary> {
	@Override
	public int compare(PlayerSummary arg0, PlayerSummary arg1) {
		if (arg0.getT20iBatSummary() == null && arg1.getT20iBatSummary() != null)
			return -1;
		if (arg0.getT20iBatSummary() == null && arg1.getT20iBatSummary() == null)
			return 0;
		if (arg0.getT20iBatSummary() != null && arg1.getT20iBatSummary() == null)
			return 1;

		return (arg1.getT20iBatSummary().getBattingAvg() < arg0.getT20iBatSummary().getBattingAvg()) ? -1
				: (arg1.getT20iBatSummary().getBattingAvg() > arg0.getT20iBatSummary().getBattingAvg()) ? 1 : 0;
	}
}
