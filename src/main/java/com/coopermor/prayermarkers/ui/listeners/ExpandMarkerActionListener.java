package com.coopermor.prayermarkers.ui.listeners;

import com.coopermor.prayermarkers.PrayerMarker;
import com.coopermor.prayermarkers.ui.PrayerMarkersPanel;
import com.coopermor.prayermarkers.PrayerMarkersPlugin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExpandMarkerActionListener implements ActionListener
{
	private final PrayerMarkersPlugin plugin;
	private final PrayerMarker marker;
	private final PrayerMarkersPanel panel;

	public ExpandMarkerActionListener(PrayerMarkersPlugin plugin, PrayerMarker marker, PrayerMarkersPanel panel)
	{
		this.plugin = plugin;
		this.marker = marker;
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent)
	{
		boolean open = panel.markerContainer.isVisible();
		marker.setCollapsed(open);
		panel.updateCollapsed();
		plugin.saveMarkers();
	}
}
