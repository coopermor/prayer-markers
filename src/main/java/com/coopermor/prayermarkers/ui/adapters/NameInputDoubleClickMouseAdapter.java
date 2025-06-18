package com.coopermor.prayermarkers.ui.adapters;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.coopermor.prayermarkers.ui.PrayerMarkersPanel;

public class NameInputDoubleClickMouseAdapter extends MouseAdapter
{
	private final PrayerMarkersPanel panel;

	public NameInputDoubleClickMouseAdapter(PrayerMarkersPanel panel)
	{
		this.panel = panel;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1)
		{
			panel.updateCollapsed();
			panel.plugin.saveMarkers();
		}
	}
}
