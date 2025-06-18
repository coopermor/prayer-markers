package com.coopermor.prayermarkers;

import com.coopermor.prayermarkers.ui.PrayerMarkersPluginPanel;
import com.google.common.base.Strings;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.inject.Provides;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.swing.JOptionPane;

import lombok.AccessLevel;
import lombok.extern.slf4j.Slf4j;
import lombok.Getter;
import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.components.colorpicker.ColorPickerManager;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.ImageUtil;

@Slf4j
@PluginDescriptor(
	name = "Prayer Markers"
)
public class PrayerMarkersPlugin extends Plugin
{
	public static final String CONFIG_GROUP = "prayermarkers";
	private static final String CONFIG_KEY = "markers";
	private static final String PLUGIN_NAME = "Prayer Markers";
	private static final String ICON_FILE = "panel_icon.png";

	@Getter(AccessLevel.PUBLIC)
	Collection<PrayerMarker> markers = new ArrayList<>();

	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private ClientToolbar clientToolbar;

	@Inject
	private ConfigManager configManager;

	@Inject
	private PrayerMarkersOverlay prayerMarkersOverlay;

	@Inject
	private Gson gson;

	@Getter
	@Inject
	private ColorPickerManager colourPickerManager;

	private PrayerMarkersPluginPanel pluginPanel;
	private NavigationButton navigationButton;

	@Override
	protected void startUp() throws Exception
	{
		pluginPanel = new PrayerMarkersPluginPanel(this);

		final BufferedImage icon = ImageUtil.loadImageResource(getClass(), ICON_FILE);

		navigationButton = NavigationButton.builder()
			.tooltip(PLUGIN_NAME)
			.icon(icon)
			.priority(5)
			.panel(pluginPanel)
			.build();

		clientToolbar.addNavigation(navigationButton);

		overlayManager.add(prayerMarkersOverlay);

		if (PrayerMarkersDelegate.clientDeveloperMode)
		{
			setupDebugMarkers();
		}
		else
		{
			loadMarkers();
		}
		pluginPanel.rebuild();
	}

	@Override
	protected void shutDown() throws Exception
	{
		clientToolbar.removeNavigation(navigationButton);
		pluginPanel = null;
		navigationButton = null;
		overlayManager.remove(prayerMarkersOverlay);
	}

	public PrayerMarker addMarker(PrayerInfo prayerInfo, boolean enabled, String name, Color color, float borderWidth)
	{
		final PrayerMarker newMarker = new PrayerMarker(prayerInfo, name, color, borderWidth);
		assert markers != null : "ArrayList<PrayerMarker> markers = null";
		if (!markers.contains(newMarker))
		{
			markers.add(newMarker);
			saveMarkers();
			pluginPanel.rebuild();
		}
		return newMarker;
	}
	public void removeMarker(PrayerMarker prayerMarker)
	{
		int before = markers.size();
		markers.remove(prayerMarker);
		saveMarkers();
		loadMarkers();
		int after = markers.size();
		assert before - 1 == after : "Expected marker count to decrease by 1. Before: " + before + ", After: " + after;
		saveMarkers();
		pluginPanel.rebuild();
	}
	private void loadMarkers()
	{
		markers.clear();
		Collection<PrayerMarker> savedMarkers = getPrayerMarkers();
		assert savedMarkers != null : "getPrayerMarkers() = null";
		markers.addAll(savedMarkers);
	}

	private Collection<PrayerMarker> getPrayerMarkers()
	{
		String json = configManager.getConfiguration(CONFIG_GROUP, CONFIG_KEY);

		if (Strings.isNullOrEmpty(json))
		{
			return Collections.emptyList();
		}

		try
		{
			//CHECKSTYLE:OFF
			List<PrayerMarker> fromConfig = gson.fromJson(json, new TypeToken<List<PrayerMarker>>(){}.getType());
			//CHECKSTYLE:ON
			fromConfig.removeIf(PrayerMarker::isInvalid);
			return fromConfig;
		}
		catch (IllegalStateException | JsonSyntaxException ignore)
		{
			JOptionPane.showConfirmDialog(pluginPanel,
				"The prayer markers configuration is invalid.",
				"Warning", JOptionPane.OK_CANCEL_OPTION);
			return null;
		}
	}

	public void saveMarkers()
	{
		if (markers == null || markers.isEmpty())
		{
			configManager.unsetConfiguration(CONFIG_GROUP, CONFIG_KEY);
			return;
		}
		String json = gson.toJson(markers);
		configManager.setConfiguration(CONFIG_GROUP, CONFIG_KEY, json);
	}

	@Provides
	PrayerMarkersConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(PrayerMarkersConfig.class);
	}

	private void setupDebugMarkers()
	{
		PrayerMarker testMarker = new PrayerMarker(
				PrayerInfo.SMITE,
				"Marker 1",
				Color.RED,
				3.0F
		);

		markers.add(testMarker);
	}
}
