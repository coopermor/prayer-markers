package com.coopermor.prayermarkers.ui.adapters;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import com.coopermor.prayermarkers.ui.PrayerMarkersPanel;
import com.coopermor.prayermarkers.PrayerMarkersPlugin;
import net.runelite.client.util.ImageUtil;

public class PrayerMarkerColorMouseAdapter extends MouseAdapter
{
	private final JLabel prayerMarkerColorLabel;
	private final PrayerMarkersPanel panel;
	private static final ImageIcon BORDER_COLOR_ICON;
	private static final ImageIcon BORDER_COLOR_HOVER_ICON;

	static
	{
		BufferedImage borderImg = ImageUtil.loadImageResource(PrayerMarkersPlugin.class, "border_color_icon.png");
		BufferedImage borderImgHover = ImageUtil.luminanceOffset(borderImg, -150);

		BORDER_COLOR_ICON = new ImageIcon(borderImg);
		BORDER_COLOR_HOVER_ICON = new ImageIcon(borderImgHover);
	}
	public PrayerMarkerColorMouseAdapter(JLabel prayerMarkerColorLabel, PrayerMarkersPanel panel)
	{
		this.prayerMarkerColorLabel = prayerMarkerColorLabel;
		this.panel = panel;
	}

	@Override
	public void mousePressed(MouseEvent mouseEvent)
	{
		panel.openColorPickerPrayer();
	}

	@Override
	public void mouseEntered(MouseEvent mouseEvent)
	{
		prayerMarkerColorLabel.setIcon(BORDER_COLOR_HOVER_ICON);
	}

	@Override
	public void mouseExited(MouseEvent mouseEvent)
	{
		prayerMarkerColorLabel.setIcon(BORDER_COLOR_ICON);
	}
}
