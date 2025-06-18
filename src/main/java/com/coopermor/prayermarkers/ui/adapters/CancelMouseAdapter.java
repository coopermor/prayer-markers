package com.coopermor.prayermarkers.ui.adapters;

import net.runelite.client.ui.ColorScheme;

import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CancelMouseAdapter extends MouseAdapter
{
	private final JLabel cancelLabel;
	private final Runnable onCancel;

	public CancelMouseAdapter(JLabel cancelLabel, Runnable onCancel)
	{
		this.cancelLabel = cancelLabel;
		this.onCancel = onCancel;
	}

	@Override
	public void mousePressed(MouseEvent mouseEvent)
	{
		onCancel.run();
	}

	@Override
	public void mouseEntered(MouseEvent mouseEvent)
	{
		cancelLabel.setForeground(ColorScheme.PROGRESS_ERROR_COLOR.darker());
	}

	@Override
	public void mouseExited(MouseEvent mouseEvent)
	{
		cancelLabel.setForeground(ColorScheme.PROGRESS_ERROR_COLOR);
	}
}
