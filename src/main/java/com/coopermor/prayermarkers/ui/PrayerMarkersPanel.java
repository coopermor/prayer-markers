package com.coopermor.prayermarkers.ui;

import com.coopermor.prayermarkers.PrayerInfo;
import com.coopermor.prayermarkers.PrayerMarker;
import com.coopermor.prayermarkers.PrayerMarkersPlugin;
import com.coopermor.prayermarkers.ui.adapters.CancelMouseAdapter;
import com.coopermor.prayermarkers.ui.adapters.DeleteMarkerMouseAdapter;
import com.coopermor.prayermarkers.ui.adapters.NameInputDoubleClickMouseAdapter;
import com.coopermor.prayermarkers.ui.adapters.NameInputKeyAdapter;
import com.coopermor.prayermarkers.ui.adapters.PrayerMarkerColorMouseAdapter;
import com.coopermor.prayermarkers.ui.adapters.RenameMouseAdapter;
import com.coopermor.prayermarkers.ui.adapters.SaveMouseAdapter;
import com.coopermor.prayermarkers.ui.adapters.HideMarkerMouseAdapter;
import com.coopermor.prayermarkers.ui.listeners.ExpandMarkerActionListener;

import com.coopermor.prayermarkers.ui.listeners.PrayerSelectionActionListener;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.components.FlatTextField;
import net.runelite.client.ui.components.colorpicker.RuneliteColorPicker;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.SwingUtil;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class PrayerMarkersPanel extends JPanel
{
	public final PrayerMarkersPlugin plugin;

	private static final Border NAME_BOTTOM_BORDER = new CompoundBorder(
		BorderFactory.createMatteBorder(0, 0, 1, 0, ColorScheme.DARK_GRAY_COLOR),
		BorderFactory.createLineBorder(ColorScheme.DARKER_GRAY_COLOR));

	private static final ImageIcon
		BORDER_COLOR_ICON,
		VISIBLE_ICON,
		INVISIBLE_ICON,
		DELETE_ICON,
		COLLAPSE_ICON,
		COLLAPSE_HOVER_ICON,
		EXPAND_ICON,
		EXPAND_HOVER_ICON;

	public final JLabel
		prayerMarkerColor = new JLabel(),
		hideMarker        = new JLabel(),
		deleteMarker      = new JLabel();

	private final JLabel
		save   = new JLabel("Save"),
		cancel = new JLabel("Cancel"),
		rename = new JLabel("Rename");

	public final JPanel markerContainer = new JPanel(new BorderLayout());
	private final FlatTextField nameInput = new FlatTextField();
	private final PrayerMarker marker;
	private final JButton expandMarker;

	static
	{
		final BufferedImage borderImg = ImageUtil.loadImageResource(PrayerMarkersPlugin.class, "border_color_icon.png");
		BORDER_COLOR_ICON = new ImageIcon(borderImg);

		final BufferedImage visibleImg = ImageUtil.loadImageResource(PrayerMarkersPlugin.class, "visible_icon.png");
		VISIBLE_ICON = new ImageIcon(visibleImg);

		final BufferedImage invisibleImg = ImageUtil.loadImageResource(PrayerMarkersPlugin.class, "invisible_icon.png");
		INVISIBLE_ICON = new ImageIcon(invisibleImg);

		final BufferedImage deleteImg = ImageUtil.loadImageResource(PrayerMarkersPlugin.class, "delete_icon.png");
		DELETE_ICON = new ImageIcon(deleteImg);

		BufferedImage retractIcon = ImageUtil.loadImageResource(PrayerMarkersPlugin.class, "arrow_right.png");
		retractIcon = ImageUtil.luminanceOffset(retractIcon, -121);
		EXPAND_ICON = new ImageIcon(retractIcon);
		EXPAND_HOVER_ICON = new ImageIcon(ImageUtil.alphaOffset(retractIcon, -100));

		final BufferedImage expandIcon = ImageUtil.rotateImage(retractIcon, Math.PI / 2);
		COLLAPSE_ICON = new ImageIcon(expandIcon);
		COLLAPSE_HOVER_ICON = new ImageIcon(ImageUtil.alphaOffset(expandIcon, -100));
	}

	public PrayerMarkersPanel(PrayerMarkersPlugin plugin, PrayerMarker marker)
	{
		this.plugin = plugin;
		this.marker = marker;

		Map<String, PrayerInfo> prayerMap = new HashMap<>();
		JComboBox<String> prayerSelection = new JComboBox<>();

		for (PrayerInfo prayer : PrayerInfo.values())
		{
			String name = prayer.getDisplayName();
			prayerSelection.addItem(name);
			prayerMap.put(name, prayer);
		}

		setLayout(new BorderLayout());
		setBackground(ColorScheme.DARKER_GRAY_COLOR);
		setBorder(new EmptyBorder(0, 0, 0, 0));

		JPanel nameWrapper = new JPanel(new BorderLayout());
		nameWrapper.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		nameWrapper.setBorder(NAME_BOTTOM_BORDER);

		JPanel nameActions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 3, 3));
		nameActions.setBackground(ColorScheme.DARKER_GRAY_COLOR);

		save.setVisible(false);
		save.setFont(FontManager.getRunescapeSmallFont());
		save.setForeground(ColorScheme.PROGRESS_COMPLETE_COLOR);
		save.setBorder(new EmptyBorder(3, 0, 0, 3));
		save.addMouseListener(new SaveMouseAdapter(save, this::save));

		cancel.setVisible(false);
		cancel.setFont(FontManager.getRunescapeSmallFont());
		cancel.setForeground(ColorScheme.PROGRESS_ERROR_COLOR);
		cancel.setBorder(new EmptyBorder(3, 0, 0, 3));
		cancel.addMouseListener(new CancelMouseAdapter(cancel, this::cancel));

		rename.setFont(FontManager.getRunescapeSmallFont());
		rename.setForeground(ColorScheme.LIGHT_GRAY_COLOR.darker());
		rename.addMouseListener(new RenameMouseAdapter(rename, nameInput, this));

		nameInput.setText(marker.getDisplayName());
		nameInput.setBorder(null);
		nameInput.setEditable(false);
		nameInput.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		nameInput.setPreferredSize(new Dimension(0, 24));
		nameInput.getTextField().setForeground(Color.WHITE);
		nameInput.getTextField().setBorder(new EmptyBorder(0, 5, 0, 0));
		nameInput.addKeyListener(new NameInputKeyAdapter(this));
		nameInput.getTextField().addMouseListener(new NameInputDoubleClickMouseAdapter(this));

		markerContainer.setBorder(new EmptyBorder(5, 0, 5, 0));
		markerContainer.setBackground(ColorScheme.DARKER_GRAY_COLOR);

		JPanel leftActionsPrayer = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
		leftActionsPrayer.setBackground(ColorScheme.DARKER_GRAY_COLOR);

		JPanel rightActionsPrayer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
		rightActionsPrayer.setBackground(ColorScheme.DARKER_GRAY_COLOR);

		prayerMarkerColor.setToolTipText("edit prayer marker color");
		prayerMarkerColor.setForeground(marker.getOverlayColor() == null ? Color.red : marker.getOverlayColor());
		prayerMarkerColor.addMouseListener(new PrayerMarkerColorMouseAdapter(prayerMarkerColor, this));
		leftActionsPrayer.add(prayerMarkerColor);

		prayerSelection.setSelectedItem(marker.getPrayerInfo().getDisplayName());
		prayerSelection.addActionListener(new PrayerSelectionActionListener(prayerSelection, prayerMap, marker));
		rightActionsPrayer.add(prayerSelection);

		expandMarker = new JButton(marker.isCollapsed() ? COLLAPSE_ICON : EXPAND_ICON);
		expandMarker.setRolloverIcon(marker.isCollapsed() ? COLLAPSE_HOVER_ICON : EXPAND_HOVER_ICON);
		expandMarker.setPreferredSize(new Dimension(15, 0));
		expandMarker.setBorder(new EmptyBorder(0, 6, 1, 0));
		expandMarker.setToolTipText((marker.isCollapsed() ? "Expand" : "Collapse") + " marker");
		expandMarker.addActionListener(new ExpandMarkerActionListener(plugin, marker, this));
		SwingUtil.removeButtonDecorations(expandMarker);

		hideMarker.setToolTipText((marker.isVisible() ? "Hide" : "Show") + " marker");
		hideMarker.addMouseListener(new HideMarkerMouseAdapter(hideMarker, marker, this, plugin));

		deleteMarker.setIcon(DELETE_ICON);
		deleteMarker.setToolTipText("Delete marker");
		deleteMarker.addMouseListener(new DeleteMarkerMouseAdapter(deleteMarker, marker, plugin, this));

		nameActions.add(rename);
		nameActions.add(cancel);
		nameActions.add(save);
		nameActions.add(hideMarker);
		nameActions.add(deleteMarker);

		nameWrapper.add(expandMarker, BorderLayout.WEST);
		nameWrapper.add(nameInput, BorderLayout.CENTER);
		nameWrapper.add(nameActions, BorderLayout.EAST);

		JPanel markerWrapper = new JPanel();
		markerWrapper.setLayout(new BoxLayout(markerWrapper, BoxLayout.Y_AXIS));
		markerWrapper.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		markerWrapper.add(nameWrapper);
		markerWrapper.add(markerContainer);

		markerContainer.setLayout(new BorderLayout());
		markerContainer.add(leftActionsPrayer, BorderLayout.WEST);
		markerContainer.add(rightActionsPrayer, BorderLayout.EAST);

		add(markerWrapper);

		updateCollapsed();
		updateVisibility();
		updateColorIndicators();
	}

	public void openColorPickerPrayer()
	{
		Color color = marker.getOverlayColor() == null ? Color.red : marker.getOverlayColor();
		RuneliteColorPicker colourPicker = getColorPicker(color);
		colourPicker.setOnColorChange(c ->
		{
			marker.setOverlayColor(c);
			prayerMarkerColor.setBorder(new MatteBorder(0, 0, 3, 0, marker.getOverlayColor()));
			prayerMarkerColor.setIcon(BORDER_COLOR_ICON);
			updateColorIndicators();
		});
		colourPicker.setVisible(true);
	}

	private RuneliteColorPicker getColorPicker(Color color)
	{
		RuneliteColorPicker colorPicker = plugin.getColourPickerManager().create(
				SwingUtilities.windowForComponent(this),
				color,
				marker.getDisplayName() + " - prayer marker colour",
				false);
		colorPicker.setLocationRelativeTo(this);
		colorPicker.setOnClose(c -> plugin.saveMarkers());
		return colorPicker;
	}

	public void save()
	{
		marker.setDisplayName(nameInput.getText());
		plugin.saveMarkers();

		nameInput.setEditable(false);
		updateNameActions(false);
		requestFocusInWindow();
	}

	public void cancel()
	{
		nameInput.setEditable(false);
		nameInput.setText(marker.getDisplayName());
		updateNameActions(false);
		requestFocusInWindow();
	}

	private void updateColorIndicators()
	{
		prayerMarkerColor.setBorder(new MatteBorder(0, 0, 3, 0, marker.getOverlayColor()));
		prayerMarkerColor.setIcon(BORDER_COLOR_ICON);
	}

	public void updateVisibility()
	{
		hideMarker.setIcon(marker.isVisible() ? VISIBLE_ICON : INVISIBLE_ICON);
	}

	public void updateCollapsed()
	{
		final boolean open = !marker.isCollapsed();
		rename.setVisible(open);
		markerContainer.setVisible(open);
		expandMarker.setIcon(open ? COLLAPSE_ICON : EXPAND_ICON);
		expandMarker.setRolloverIcon(open ? COLLAPSE_HOVER_ICON : EXPAND_HOVER_ICON);
		expandMarker.setToolTipText((open ? "Collapse" : "Expand") + " marker");
	}

	public void updateNameActions(boolean saveAndCancel)
	{
		save.setVisible(saveAndCancel);
		cancel.setVisible(saveAndCancel);
		rename.setVisible(!saveAndCancel);
		expandMarker.setVisible(!saveAndCancel);
		hideMarker.setVisible(!saveAndCancel);
		deleteMarker.setVisible(!saveAndCancel);

		if (saveAndCancel)
		{
			nameInput.getTextField().requestFocusInWindow();
			nameInput.getTextField().selectAll();

			String newName = nameInput.getText();
			if (newName != null && !newName.trim().isEmpty())
			{
				marker.setDisplayName(newName.trim());
			}
		}
	}
}
