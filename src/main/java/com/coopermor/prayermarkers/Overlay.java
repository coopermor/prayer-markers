package com.coopermor.prayermarkers;

import net.runelite.api.Client;
import net.runelite.api.VarClientInt;
import net.runelite.api.widgets.Widget;
import net.runelite.client.ui.overlay.OverlayUtil;

import java.awt.*;

public class Overlay
{
	public static Rectangle renderPrayerOverlay(Graphics2D graphics, Client client, PrayerInfo prayer, Color color, float borderWidth)
	{
		Widget prayerIconWidget = client.getWidget(PrayerInfo.getPrayerWidgetId(prayer));
		if (prayerIconWidget == null || !prayerWidgetVisibilityChecks(client, prayerIconWidget))
		{
			return null;
		}
		Rectangle prayerIconBounds = prayerIconWidget.getBounds();
		Stroke stroke = new BasicStroke(borderWidth);
		Color clear = new Color(0, 0, 0, 0);
		OverlayUtil.renderPolygon(graphics, rectangleToPolygon(prayerIconBounds), color, clear, stroke);
		return prayerIconBounds;
	}

	private static Polygon rectangleToPolygon(Rectangle rectangle)
	{
		int[] x_points = {rectangle.x, rectangle.x + rectangle.width, rectangle.x + rectangle.width, rectangle.x};
		int[] y_points = {rectangle.y, rectangle.y, rectangle.y + rectangle.height, rectangle.y + rectangle.height};

		return new Polygon(x_points, y_points, 4);
	}

	private static boolean prayerWidgetVisibilityChecks(Client client, Widget prayerWidget)
	{
		// Check if the inventory tab is on Prayer
		if (client.getVarcIntValue(VarClientInt.INVENTORY_TAB) != 5)
		{
			return false;
		}

		// Interfaces like the bank and shops still has VarClientInt.INVENTORY_TAB set to 5
		// but are forced away from the prayer screen. So we check the prayer widget is visible
		Widget prayerBook = client.getWidget(35454976);
		if (prayerBook == null || prayerBook.isHidden())
		{
			return false;
		}

		// Check if the prayer filtering interface widget is showing
		Widget prayerFilters = client.getWidget(35454982);
		if (prayerFilters != null)
		{
			// 1141 is white and 1150 is red, red means toggled
			Widget filterSprite = prayerFilters.getChild(0);
			if (filterSprite != null && filterSprite.getSpriteId() == 1150)
			{
				return false;
			}
		}

		// Check if the prayer icons are hidden through prayer filtering
		if (prayerWidget != null)
		{
			return !prayerWidget.isHidden();
		}
		return true;
	}
}