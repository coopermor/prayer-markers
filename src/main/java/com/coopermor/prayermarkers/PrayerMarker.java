package com.coopermor.prayermarkers;

import java.awt.Color;
import lombok.Value;

@Value
class PrayerMarker
{
	private PrayerInfo prayerInfo;
	private boolean enabled;
	private String displayName;
	Color overalyColor;

	public static boolean isInvalid(PrayerMarker marker)
	{
		return marker == null
		|| marker.prayerInfo == null
		|| marker.displayName == null
		|| marker.overalyColor == null;
	}
}
