package com.coopermor.prayermarkers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import net.runelite.api.Client;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.util.ImageUtil;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import java.awt.image.BufferedImage;

class PrayerMarkersPluginPanel extends PluginPanel {
    private static final ImageIcon ADD_ICON;
	private static final ImageIcon ADD_HOVER_ICON;

    private final JLabel title = new JLabel();
    private final JLabel markerAdd = new JLabel(ADD_ICON);

    private final Client client;
	private final PrayerMarkersPlugin plugin;
	private final PrayerMarkersConfig config;

    static
    {
        final BufferedImage addIcon = ImageUtil.loadImageResource(PrayerMarkersPlugin.class, "add_icon.png");
        ADD_ICON = new ImageIcon(addIcon);
        ADD_HOVER_ICON = new ImageIcon(ImageUtil.alphaOffset(addIcon, 0.53f));

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

        JPanel titlePanel = new JPanel(new BorderLayout());
		titlePanel.setBorder(new EmptyBorder(1, 3, 10, 7));

        title.setText("Prayer Markers");
		title.setForeground(Color.WHITE);

        titlePanel.add(title, BorderLayout.WEST);

        markerAdd.setToolTipText("Add new prayer marker");
        markerAdd.addMouseListener(new MouseAdapter(){
            @Override
			public void mousePressed(MouseEvent mouseEvent)
			{
				addMarker();
			}

			@Override
			public void mouseEntered(MouseEvent mouseEvent)
			{
				markerAdd.setIcon(ADD_HOVER_ICON);
			}

			@Override
			public void mouseExited(MouseEvent mouseEvent)
			{
				markerAdd.setIcon(ADD_ICON);
			}
        });

        add(titlePanel, BorderLayout.NORTH);
    }

    public void rebuild()
    {
        repaint();
        revalidate();
    }

    private void addMarker()
    {
        
    }
}
