package com.coopermor.prayermarkers;

import java.awt.Color;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public
class PrayerMarker
{
	PrayerInfo prayerInfo;
	String displayName;
	Color overlayColor;
	boolean enabled;
	boolean visible;
	boolean collapsed;
	float borderWidth;

	public PrayerMarker(PrayerInfo prayerInfo, String displayName, Color overlayColor, float borderWidth)
	{
		this.prayerInfo = prayerInfo;
		this.displayName = displayName;
		this.overlayColor = overlayColor;
		this.enabled = true;
		this.visible = true;
		this.collapsed = false;
		this.borderWidth = borderWidth;
	}

	public static boolean isInvalid(PrayerMarker marker)
	{
		return marker == null
		|| marker.prayerInfo == null
		|| marker.displayName == null
		|| marker.overlayColor == null
		|| marker.borderWidth <= 0.0005;
	}
}
