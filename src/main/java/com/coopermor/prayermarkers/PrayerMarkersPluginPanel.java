package com.coopermor.prayermarkers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import com.coopermor.prayermarkers.adapters.MarkerAddMouseAdapter;
import net.runelite.api.Client;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.PluginErrorPanel;
import net.runelite.client.util.ImageUtil;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import java.awt.image.BufferedImage;

class PrayerMarkersPluginPanel extends PluginPanel
{
	private static final ImageIcon ADD_ICON;
	private final PluginErrorPanel errorPanel = new PluginErrorPanel();
	private final Client client;
	private final PrayerMarkersPlugin plugin;
	private final PrayerMarkersConfig config;
	private final JPanel markerView = new JPanel();

	static
	{
		final BufferedImage addIcon = ImageUtil.loadImageResource(PrayerMarkersPlugin.class, "add_icon.png");
		ADD_ICON = new ImageIcon(addIcon);

		final BufferedImage visibleImg = ImageUtil.loadImageResource(PrayerMarkersPlugin.class, "visible_icon.png");
		final BufferedImage invisibleImg = ImageUtil.loadImageResource(PrayerMarkersPlugin.class, "invisible_icon.png");
	}
	public PrayerMarkersPluginPanel(Client client, PrayerMarkersPlugin plugin, PrayerMarkersConfig config)
	{
		this.client = client;
		this.plugin = plugin;
		this.config = config;

		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel northPanel = new JPanel(new BorderLayout());
		northPanel.setBorder(new EmptyBorder(1, 0, 10, 0));

		JPanel titlePanel = new JPanel(new BorderLayout());
		JLabel markerAdd = new JLabel(ADD_ICON);
		JPanel markerButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 7, 3));
		JPanel centerPanel = new JPanel(new BorderLayout());

		titlePanel.setBorder(new EmptyBorder(1, 3, 10, 7));
		add(titlePanel, BorderLayout.NORTH);

		markerAdd.setToolTipText("Add new prayer marker");
		markerAdd.addMouseListener(new MarkerAddMouseAdapter(markerAdd, plugin, this::addMarker));

		centerPanel.setBackground(ColorScheme.DARK_GRAY_COLOR);

		JLabel title = new JLabel();
		title.setText("Prayer Markers");
		title.setForeground(Color.WHITE);

		titlePanel.add(title, BorderLayout.WEST);
		titlePanel.add(markerButtons, BorderLayout.EAST);

		northPanel.add(titlePanel, BorderLayout.NORTH);

		markerView.setLayout(new BoxLayout(markerView, BoxLayout.Y_AXIS));
		markerView.setBackground(ColorScheme.DARK_GRAY_COLOR);
		markerView.add(errorPanel);
		setupErrorPanel(true);

		markerButtons.add(markerAdd);

		centerPanel.add(markerView, BorderLayout.NORTH);
		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
	}

	public void rebuild()
	{
		markerView.removeAll();

		for (PrayerMarker marker : plugin.getMarkers())
		{
			markerView.add(new PrayerMarkerPanel(plugin, config, marker));
			markerView.add(Box.createRigidArea(new Dimension(0, 10)));
		}
		repaint();
		revalidate();
	}

	private void addMarker()
	{
		setupErrorPanel(false);
	}

	private void setupErrorPanel(boolean enabled)
	{
		PluginErrorPanel errorPanel = this.errorPanel;
		assert errorPanel != null : "prayerMarkerErrorPanel = null";
		errorPanel.setVisible(enabled);
		if (enabled)
		{
			errorPanel.setContent("Prayer Markers", "Click the '+' button to add a prayer marker to the prayer tab.");
		}
	}
}
