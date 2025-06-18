package com.coopermor.prayermarkers.ui.adapters;

import net.runelite.client.ui.ColorScheme;

import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SaveMouseAdapter extends MouseAdapter
{
	private final JLabel saveLabel;
	private final Runnable onSave;

	public SaveMouseAdapter(JLabel saveLabel, Runnable onSave)
	{
		this.saveLabel = saveLabel;
		this.onSave = onSave;
	}

	@Override
	public void mousePressed(MouseEvent mouseEvent)
	{
		onSave.run();
	}

	@Override
	public void mouseEntered(MouseEvent mouseEvent)
	{
		saveLabel.setForeground(ColorScheme.PROGRESS_COMPLETE_COLOR.darker());
	}

	@Override
	public void mouseExited(MouseEvent mouseEvent)
	{
		saveLabel.setForeground(ColorScheme.PROGRESS_COMPLETE_COLOR);
	}
}
