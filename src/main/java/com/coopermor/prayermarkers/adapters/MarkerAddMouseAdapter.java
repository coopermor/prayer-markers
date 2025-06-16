package com.coopermor.prayermarkers.adapters;

import com.coopermor.prayermarkers.PrayerMarkersPlugin;
import net.runelite.client.util.ImageUtil;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class MarkerAddMouseAdapter extends MouseAdapter
{
	private static final BufferedImage addIcon = ImageUtil.loadImageResource(PrayerMarkersPlugin.class, "add_icon.png");
	private static final ImageIcon ADD_ICON = new ImageIcon(addIcon);
	private static final ImageIcon ADD_HOVER_ICON = new ImageIcon(ImageUtil.alphaOffset(addIcon, 0.53f));
	private final JLabel markerAdd;
	private final Runnable onClick;

	public MarkerAddMouseAdapter(JLabel markerAdd, Runnable onClick)
	{
		this.markerAdd = markerAdd;
		this.onClick = onClick;
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		onClick.run();
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		markerAdd.setIcon(ADD_HOVER_ICON);
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		markerAdd.setIcon(ADD_ICON);
	}
}
