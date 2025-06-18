package com.coopermor.prayermarkers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.inject.Inject;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;

import static com.coopermor.prayermarkers.Overlay.renderPrayerOverlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

class PrayerMarkersOverlay extends Overlay
{
	private final Client client;
	private final PrayerMarkersPlugin plugin;

	@Inject
	private PrayerMarkersOverlay(Client client, PrayerMarkersPlugin plugin)
	{
		this.client = client;
		this.plugin = plugin;

		setPosition(OverlayPosition.DYNAMIC);
		setPriority(Overlay.PRIORITY_HIGHEST);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		for (PrayerMarker marker : plugin.getMarkers())
		{
			if (!marker.isEnabled() || !marker.isVisible())
			{
				continue;
			}
			PrayerInfo info = marker.getPrayerInfo();
			PrayerInfo prayerInfo = PrayerInfo.valueOf(info.name());
			Color color = marker.getOverlayColor();
			float strokeThickness = marker.getBorderWidth();

			renderPrayerOverlay(graphics, client, prayerInfo, color, strokeThickness);
		}
		return null;
	}
}
