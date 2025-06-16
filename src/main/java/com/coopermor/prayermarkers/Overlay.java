package com.coopermor.prayermarkers;

import net.runelite.api.Client;
import net.runelite.api.VarClientInt;
import net.runelite.api.widgets.Widget;
import net.runelite.client.ui.overlay.OverlayUtil;

import java.awt.*;

public class Overlay
{
	public static Rectangle renderPrayerOverlay(Graphics2D graphics, Client client, PrayerInfo prayer, Color color)
	{
		Widget widget = client.getWidget(PrayerInfo.getPrayerWidgetId(prayer));

		if (widget == null || client.getVarcIntValue(VarClientInt.INVENTORY_TAB) != 5)
		{
			return null;
		}

		Rectangle bounds = widget.getBounds();
		OverlayUtil.renderPolygon(graphics, rectangleToPolygon(bounds), color);
		return bounds;
	}

	private static Polygon rectangleToPolygon(Rectangle rectangle)
	{
		int[] x_points = {rectangle.x, rectangle.x + rectangle.width, rectangle.x + rectangle.width, rectangle.x};
		int[] y_points = {rectangle.y, rectangle.y, rectangle.y + rectangle.height, rectangle.y + rectangle.height};

		return new Polygon(x_points, y_points, 4);
	}
}
