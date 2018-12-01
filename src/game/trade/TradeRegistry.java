package game.trade;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

import game.resources.Resource;

public class TradeRegistry {
	private static final MultimapBuilder<Resource, Trade> MAP_BUILDER = MultimapBuilder.enumKeys(Resource.class)
			.treeSetValues(Trade.PRICE_COMPARATOR);
	private final Multimap<Resource, Trade> trades;

	public TradeRegistry() {
		trades = MAP_BUILDER.build();
	}
}
