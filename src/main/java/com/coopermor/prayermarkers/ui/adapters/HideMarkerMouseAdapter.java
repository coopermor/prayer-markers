package com.coopermor.prayermarkers.ui.adapters;

import com.coopermor.prayermarkers.PrayerMarker;
import com.coopermor.prayermarkers.ui.PrayerMarkersPanel;
import com.coopermor.prayermarkers.PrayerMarkersPlugin;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import net.runelite.client.util.ImageUtil;

public class HideMarkerMouseAdapter extends MouseAdapter
{
	private final JLabel hideMarker;
	private final PrayerMarker marker;
	private final PrayerMarkersPanel panel;
	private final PrayerMarkersPlugin plugin;
	private static final ImageIcon VISIBLE_HOVER_ICON;
	private static final ImageIcon INVISIBLE_HOVER_ICON;

	static
	{
		final BufferedImage visibleImg = ImageUtil.loadImageResource(PrayerMarkersPlugin.class, "visible_icon.png");
		VISIBLE_HOVER_ICON = new ImageIcon(ImageUtil.alphaOffset(visibleImg, -100));

		final BufferedImage invisibleImg = ImageUtil.loadImageResource(PrayerMarkersPlugin.class, "invisible_icon.png");
		INVISIBLE_HOVER_ICON = new ImageIcon(ImageUtil.alphaOffset(invisibleImg, -100));
	}

	public HideMarkerMouseAdapter(JLabel hideMarker, PrayerMarker marker, PrayerMarkersPanel panel, PrayerMarkersPlugin plugin)
	{
		this.hideMarker = hideMarker;
		this.marker = marker;
		this.panel = panel;
		this.plugin = plugin;
	}

	@Override
	public void mousePressed(MouseEvent mouseEvent)
	{
		marker.setVisible(!marker.isVisible());
		panel.updateVisibility();
		plugin.saveMarkers();
	}

	@Override
	public void mouseEntered(MouseEvent mouseEvent)
	{
		hideMarker.setIcon(marker.isVisible() ? VISIBLE_HOVER_ICON : INVISIBLE_HOVER_ICON);
	}

	@Override
	public void mouseExited(MouseEvent mouseEvent)
	{
		panel.updateVisibility();
	}
}
