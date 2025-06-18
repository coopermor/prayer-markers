package com.coopermor.prayermarkers.ui.adapters;

import com.coopermor.prayermarkers.PrayerMarker;
import com.coopermor.prayermarkers.PrayerMarkersPlugin;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import net.runelite.client.util.ImageUtil;

public class DeleteMarkerMouseAdapter extends MouseAdapter
{
	private final JLabel deleteMarker;
	private final PrayerMarker marker;
	private final PrayerMarkersPlugin plugin;
	private final Component parentComponent;
	private static final ImageIcon DELETE_ICON, DELETE_HOVER_ICON;

	static
	{
		final BufferedImage deleteImg = ImageUtil.loadImageResource(PrayerMarkersPlugin.class, "delete_icon.png");
		DELETE_ICON = new ImageIcon(deleteImg);
		DELETE_HOVER_ICON = new ImageIcon(ImageUtil.alphaOffset(deleteImg, -100));
	}

	public DeleteMarkerMouseAdapter(JLabel deleteMarker, PrayerMarker marker, PrayerMarkersPlugin plugin, Component parentComponent)
	{
		this.deleteMarker = deleteMarker;
		this.marker = marker;
		this.plugin = plugin;
		this.parentComponent = parentComponent;
	}

	@Override
	public void mousePressed(MouseEvent mouseEvent)
	{
		int confirm = JOptionPane.showConfirmDialog(
				parentComponent,
				"Are you sure you want to permanently delete this prayer marker?",
				"Warning",
				JOptionPane.OK_CANCEL_OPTION
		);

		if (confirm == JOptionPane.OK_OPTION)
		{
			plugin.removeMarker(marker);
		}
	}

	@Override
	public void mouseEntered(MouseEvent mouseEvent)
	{
		deleteMarker.setIcon(DELETE_HOVER_ICON);
	}

	@Override
	public void mouseExited(MouseEvent mouseEvent)
	{
		deleteMarker.setIcon(DELETE_ICON);
	}
}
