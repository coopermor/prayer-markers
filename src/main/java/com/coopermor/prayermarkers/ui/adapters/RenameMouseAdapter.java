package com.coopermor.prayermarkers.ui.adapters;

import com.coopermor.prayermarkers.ui.PrayerMarkersPanel;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.components.FlatTextField;

import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RenameMouseAdapter extends MouseAdapter
{
private final JLabel renameLabel;
private final FlatTextField nameInput;
private final PrayerMarkersPanel panel;

	public RenameMouseAdapter(JLabel renameLabel, FlatTextField nameInput, PrayerMarkersPanel panel)
	{
		this.renameLabel = renameLabel;
		this.nameInput = nameInput;
		this.panel = panel;
	}

	@Override
	public void mousePressed(MouseEvent mouseEvent)
	{
		nameInput.setEditable(true);
		panel.updateNameActions(true);
	}

	@Override
	public void mouseEntered(MouseEvent mouseEvent)
	{
		renameLabel.setForeground(ColorScheme.LIGHT_GRAY_COLOR.darker().darker());
	}

	@Override
	public void mouseExited(MouseEvent mouseEvent)
	{
		renameLabel.setForeground(ColorScheme.LIGHT_GRAY_COLOR.darker());
	}
}


