package com.coopermor.prayermarkers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.inject.Inject;

import com.coopermor.prayermarkers.PrayerInfo;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;

import static com.coopermor.prayermarkers.Overlay.renderPrayerOverlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

class PrayerMarkersOverlay extends Overlay
{
	private final Client client;
	private final PrayerMarkersPlugin plugin;
	private final PrayerMarkersConfig config;

	@Inject
	private PrayerMarkersOverlay(Client client, PrayerMarkersPlugin plugin, PrayerMarkersConfig config)
	{
		this.client = client;
		this.plugin = plugin;
		this.config = config;

		setPosition(OverlayPosition.DYNAMIC);
		setPriority(Overlay.PRIORITY_HIGHEST);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		for (PrayerMarker marker : plugin.getMarkers())
		{
			if (!marker.isEnabled())
			{
				continue;
			}
			PrayerInfo info = marker.getPrayerInfo();
			PrayerInfo prayerInfo = PrayerInfo.valueOf(info.name());
			Color color = marker.getOveralyColor();

			renderPrayerOverlay(graphics, client, prayerInfo, color);
		}
		return null;
	}
}
