package com.coopermor.prayermarkers;

import java.awt.Color;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
class PrayerMarker
{
	PrayerInfo prayerInfo;
	String displayName;
	Color overalyColor;
	boolean enabled;
	boolean visible;
	boolean collapsed;
	float strokeThickness;
	public PrayerMarker(PrayerInfo prayerInfo, String displayName, Color overlayColor, float strokeThickness)
	{
		this.prayerInfo = prayerInfo;
		this.displayName = displayName;
		this.overalyColor = overlayColor;
		this.enabled = true;
		this.visible = true;
		this.collapsed = false;
		this.strokeThickness = strokeThickness;
	}
	public static boolean isInvalid(PrayerMarker marker)
	{
		return marker == null
		|| marker.prayerInfo == null
		|| marker.displayName == null
		|| marker.overalyColor == null
		|| marker.strokeThickness <= 0.0005;
	}
}
