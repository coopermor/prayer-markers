package com.coopermor.prayermarkers.ui.adapters;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.coopermor.prayermarkers.ui.PrayerMarkersPanel;

public class NameInputKeyAdapter extends KeyAdapter
{
	private final PrayerMarkersPanel panel;

	public NameInputKeyAdapter(PrayerMarkersPanel panel)
	{
		this.panel = panel;
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			panel.save();
		}
		else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			panel.cancel();
		}
	}
}
