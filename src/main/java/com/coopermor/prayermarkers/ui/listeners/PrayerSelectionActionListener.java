package com.coopermor.prayermarkers.ui.listeners;

import com.coopermor.prayermarkers.PrayerMarker;
import com.coopermor.prayermarkers.PrayerInfo;

import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class PrayerSelectionActionListener implements ActionListener
{
	private final JComboBox<String> prayerSelection;
	private final Map<String, PrayerInfo> prayerMap;
	private final PrayerMarker marker;

	public PrayerSelectionActionListener(JComboBox<String> prayerSelection, Map<String, PrayerInfo> prayerMap, PrayerMarker marker)
	{
		this.prayerSelection = prayerSelection;
		this.prayerMap = prayerMap;
		this.marker = marker;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String selectedName = (String) prayerSelection.getSelectedItem();
		if (selectedName != null)
		{
			PrayerInfo selectedPrayer = prayerMap.get(selectedName);
			if (selectedPrayer != null)
			{
				marker.setPrayerInfo(selectedPrayer);
			}
		}
	}
}
