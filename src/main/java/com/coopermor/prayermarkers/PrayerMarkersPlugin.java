package com.coopermor.prayermarkers;

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

	@Getter(AccessLevel.PACKAGE)
	private final Collection<PrayerMarker> markers = new ArrayList<>();

	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private PrayerMarkersConfig config;

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
		pluginPanel = new PrayerMarkersPluginPanel(client, this, config);
		pluginPanel.rebuild();

		final BufferedImage icon = ImageUtil.loadImageResource(getClass(), ICON_FILE);

		navigationButton = NavigationButton.builder()
			.tooltip(PLUGIN_NAME)
			.icon(icon)
			.priority(5)
			.panel(pluginPanel)
			.build();

		clientToolbar.addNavigation(navigationButton);

		overlayManager.add(prayerMarkersOverlay);

		if (PrayerMarkerBootstrap.developerMode)
		{
			setupDebugMarkers();
		}

	}

	@Override
	protected void shutDown() throws Exception
	{
		clientToolbar.removeNavigation(navigationButton);
		pluginPanel = null;
		navigationButton = null;
		overlayManager.remove(prayerMarkersOverlay);
	}

	private void loadMarkers()
	{
		markers.clear();

		markers.addAll(getPrayerMarkers());
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

	private void saveMarkers(Collection<PrayerMarker> markers)
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
				true,
				"Marker 1",
				Color.RED
		);

		markers.add(testMarker);
	}
}
