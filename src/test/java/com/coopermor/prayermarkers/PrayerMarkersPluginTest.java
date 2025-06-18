package com.coopermor.prayermarkers;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;


public class PrayerMarkersPluginTest {
	public static void main(String[] args) throws Exception {

		OptionParser parser = new OptionParser();
		parser.accepts("developer-mode");
		OptionSet options = parser.parse(args);
		if (options.has("developer-mode")) {
			PrayerMarkersDelegate.clientDeveloperMode = true;
		}

		ExternalPluginManager.loadBuiltin(PrayerMarkersPlugin.class);
		RuneLite.main(args);
	}
}