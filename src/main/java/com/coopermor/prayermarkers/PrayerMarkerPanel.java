package com.coopermor.prayermarkers;

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
import javax.swing.JOptionPane;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class PrayerMarkerPanel extends JPanel
{
private static final Border NAME_BOTTOM_BORDER = new CompoundBorder(
		BorderFactory.createMatteBorder(0, 0, 1, 0, ColorScheme.DARK_GRAY_COLOR),
		BorderFactory.createLineBorder(ColorScheme.DARKER_GRAY_COLOR));

	private static final ImageIcon BORDER_COLOR_ICON;
	private static final ImageIcon BORDER_COLOR_HOVER_ICON;
	private static final ImageIcon NO_BORDER_COLOR_ICON;
	private static final ImageIcon NO_BORDER_COLOR_HOVER_ICON;
	private static final ImageIcon VISIBLE_ICON;
	private static final ImageIcon VISIBLE_HOVER_ICON;
	private static final ImageIcon INVISIBLE_ICON;
	private static final ImageIcon INVISIBLE_HOVER_ICON;
	private static final ImageIcon DELETE_ICON;
	private static final ImageIcon DELETE_HOVER_ICON;
	private static final ImageIcon COLLAPSE_ICON;
	private static final ImageIcon COLLAPSE_HOVER_ICON;
	private static final ImageIcon EXPAND_ICON;
	private static final ImageIcon EXPAND_HOVER_ICON;
	private final PrayerMarkersPlugin plugin;
	private final PrayerMarkersConfig config;
	private final PrayerMarker marker;
	private final JPanel containerPrayer = new JPanel(new BorderLayout());
	private final JLabel colorPrayer = new JLabel();
	private final JLabel visibilityMarker = new JLabel();
	private final JLabel deleteLabel = new JLabel();
	private final JButton expandToggle;
	private final FlatTextField nameInput = new FlatTextField();
	private final JLabel save = new JLabel("Save");
	private final JLabel cancel = new JLabel("Cancel");
	private final JLabel rename = new JLabel("Rename");

	static
	{
		final BufferedImage borderImg = ImageUtil.loadImageResource(PrayerMarkersPlugin.class, "border_color_icon.png");
		final BufferedImage borderImgHover = ImageUtil.luminanceOffset(borderImg, -150);
		BORDER_COLOR_ICON = new ImageIcon(borderImg);
		BORDER_COLOR_HOVER_ICON = new ImageIcon(borderImgHover);

		NO_BORDER_COLOR_ICON = new ImageIcon(borderImgHover);
		NO_BORDER_COLOR_HOVER_ICON = new ImageIcon(ImageUtil.alphaOffset(borderImgHover, -100));

		final BufferedImage visibleImg = ImageUtil.loadImageResource(PrayerMarkersPlugin.class, "visible_icon.png");
		VISIBLE_ICON = new ImageIcon(visibleImg);
		VISIBLE_HOVER_ICON = new ImageIcon(ImageUtil.alphaOffset(visibleImg, -100));

		final BufferedImage invisibleImg = ImageUtil.loadImageResource(PrayerMarkersPlugin.class, "invisible_icon.png");
		INVISIBLE_ICON = new ImageIcon(invisibleImg);
		INVISIBLE_HOVER_ICON = new ImageIcon(ImageUtil.alphaOffset(invisibleImg, -100));

		final BufferedImage deleteImg = ImageUtil.loadImageResource(PrayerMarkersPlugin.class, "delete_icon.png");
		DELETE_ICON = new ImageIcon(deleteImg);
		DELETE_HOVER_ICON = new ImageIcon(ImageUtil.alphaOffset(deleteImg, -100));

		BufferedImage retractIcon = ImageUtil.loadImageResource(PrayerMarkersPlugin.class, "arrow_right.png");
		retractIcon = ImageUtil.luminanceOffset(retractIcon, -121);
		EXPAND_ICON = new ImageIcon(retractIcon);
		EXPAND_HOVER_ICON = new ImageIcon(ImageUtil.alphaOffset(retractIcon, -100));
		final BufferedImage expandIcon = ImageUtil.rotateImage(retractIcon, Math.PI / 2);
		COLLAPSE_ICON = new ImageIcon(expandIcon);
		COLLAPSE_HOVER_ICON = new ImageIcon(ImageUtil.alphaOffset(expandIcon, -100));
	}

	PrayerMarkerPanel(PrayerMarkersPlugin plugin, PrayerMarkersConfig config, PrayerMarker marker)
	{
		this.plugin = plugin;
		this.config = config;
		this.marker = marker;

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

		save.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent mouseEvent)
			{
				save();
			}

			@Override
			public void mouseEntered(MouseEvent mouseEvent)
			{
				save.setForeground(ColorScheme.PROGRESS_COMPLETE_COLOR.darker());
			}

			@Override
			public void mouseExited(MouseEvent mouseEvent)
			{
				save.setForeground(ColorScheme.PROGRESS_COMPLETE_COLOR);
			}
		});

		cancel.setVisible(false);
		cancel.setFont(FontManager.getRunescapeSmallFont());
		cancel.setForeground(ColorScheme.PROGRESS_ERROR_COLOR);
		cancel.setBorder(new EmptyBorder(3, 0, 0, 3));
		cancel.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent mouseEvent)
			{
				cancel();
			}

			@Override
			public void mouseEntered(MouseEvent mouseEvent)
			{
				cancel.setForeground(ColorScheme.PROGRESS_ERROR_COLOR.darker());
			}

			@Override
			public void mouseExited(MouseEvent mouseEvent)
			{
				cancel.setForeground(ColorScheme.PROGRESS_ERROR_COLOR);
			}
		});

		rename.setFont(FontManager.getRunescapeSmallFont());
		rename.setForeground(ColorScheme.LIGHT_GRAY_COLOR.darker());
		rename.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent mouseEvent)
			{
				nameInput.setEditable(true);
				updateNameActions(true);
			}

			@Override
			public void mouseEntered(MouseEvent mouseEvent)
			{
				rename.setForeground(ColorScheme.LIGHT_GRAY_COLOR.darker().darker());
			}

			@Override
			public void mouseExited(MouseEvent mouseEvent)
			{
				rename.setForeground(ColorScheme.LIGHT_GRAY_COLOR.darker());
			}
		});

		nameInput.setText(marker.getDisplayName());
		nameInput.setBorder(null);
		nameInput.setEditable(false);
		nameInput.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		nameInput.setPreferredSize(new Dimension(0, 24));
		nameInput.getTextField().setForeground(Color.WHITE);
		nameInput.getTextField().setBorder(new EmptyBorder(0, 5, 0, 0));
		nameInput.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					save();
				}
				else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
				{
					cancel();
				}
			}
		});
		nameInput.getTextField().addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1)
				{
					final boolean open = containerPrayer.isVisible();

					updateCollapsed();
					plugin.saveMarkers();
				}
			}
		});
		containerPrayer.setBorder(new EmptyBorder(5, 0, 5, 0));
		containerPrayer.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		JPanel leftActionsPrayer = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
		leftActionsPrayer.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		colorPrayer.setToolTipText("edit prayer marker color");
		colorPrayer.setForeground(marker.getOveralyColor() == null ? Color.red : marker.getOveralyColor());
		colorPrayer.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent mouseEvent)
			{
				openColorPickerPrayer();
			}

			@Override
			public void mouseEntered(MouseEvent mouseEvent)
			{
				colorPrayer.setIcon(BORDER_COLOR_HOVER_ICON);
			}

			@Override
			public void mouseExited(MouseEvent mouseEvent)
			{
				colorPrayer.setIcon(BORDER_COLOR_ICON);
			}
		});
		leftActionsPrayer.add(colorPrayer);

		Map<String, PrayerInfo> prayerMap = new HashMap<>();
		JComboBox<String> comboBox = new JComboBox<>();

		for (PrayerInfo prayer : PrayerInfo.values())
		{
			String name = prayer.getDisplayName();
			comboBox.addItem(name);
			prayerMap.put(name, prayer);
		}

		comboBox.addActionListener(e ->
		{
			String selectedName = (String) comboBox.getSelectedItem();
			if (selectedName != null)
			{
				PrayerInfo selectedPrayer = prayerMap.get(selectedName);
				if (selectedPrayer != null)
				{
					marker.setPrayerInfo(selectedPrayer);
				}
			}
		});
		comboBox.setSelectedItem(marker.getPrayerInfo().getDisplayName());

		JPanel rightActionsPrayer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
		rightActionsPrayer.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		rightActionsPrayer.add(comboBox);
		expandToggle = new JButton(marker.isCollapsed() ? COLLAPSE_ICON : EXPAND_ICON);
		expandToggle.setRolloverIcon(marker.isCollapsed() ? COLLAPSE_HOVER_ICON : EXPAND_HOVER_ICON);
		expandToggle.setPreferredSize(new Dimension(15, 0));
		expandToggle.setBorder(new EmptyBorder(0, 6, 1, 0));
		expandToggle.setToolTipText((marker.isCollapsed() ? "Expand" : "Collapse") + " marker");
		SwingUtil.removeButtonDecorations(expandToggle);
		expandToggle.addActionListener(actionEvent ->
		{
			final boolean open = containerPrayer.isVisible();
			marker.setCollapsed(open);
			updateCollapsed();
			plugin.saveMarkers();
		});

		visibilityMarker.setToolTipText((marker.isVisible() ? "Hide" : "Show") + " marker");
		visibilityMarker.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent mouseEvent)
			{
				marker.setVisible(!marker.isVisible());
				updateVisibility();
				plugin.saveMarkers();
			}

			@Override
			public void mouseEntered(MouseEvent mouseEvent)
			{
				visibilityMarker.setIcon(marker.isVisible() ? VISIBLE_HOVER_ICON : INVISIBLE_HOVER_ICON);
			}

			@Override
			public void mouseExited(MouseEvent mouseEvent)
			{
				updateVisibility();
			}
		});

		deleteLabel.setIcon(DELETE_ICON);
		deleteLabel.setToolTipText("Delete marker");
		deleteLabel.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent mouseEvent)
			{
				int confirm = JOptionPane.showConfirmDialog(PrayerMarkerPanel.this,
						"Are you sure you want to permanently delete this prayer marker?",
						"Warning", JOptionPane.OK_CANCEL_OPTION);

				if (confirm == 0)
				{
					plugin.removeMarker(marker);
				}
			}

			@Override
			public void mouseEntered(MouseEvent mouseEvent)
			{
				deleteLabel.setIcon(DELETE_HOVER_ICON);
			}

			@Override
			public void mouseExited(MouseEvent mouseEvent)
			{
				deleteLabel.setIcon(DELETE_ICON);
			}
		});
		nameActions.add(rename);
		nameActions.add(cancel);
		nameActions.add(save);
		nameActions.add(visibilityMarker);
		nameActions.add(deleteLabel);

		nameWrapper.add(expandToggle, BorderLayout.WEST);
		nameWrapper.add(nameInput, BorderLayout.CENTER);
		nameWrapper.add(nameActions, BorderLayout.EAST);

		containerPrayer.add(leftActionsPrayer, BorderLayout.WEST);
		containerPrayer.add(rightActionsPrayer, BorderLayout.EAST);

		JPanel markerContainer = new JPanel();
		markerContainer.setLayout(new BoxLayout(markerContainer, BoxLayout.Y_AXIS));
		markerContainer.setBackground(ColorScheme.DARKER_GRAY_COLOR);

		markerContainer.add(nameWrapper);
		markerContainer.add(containerPrayer);

		add(markerContainer);
		updateCollapsed();
		updateVisibility();
		updateColorIndicators();
	}
	private void openColorPickerPrayer()
	{
		Color color = marker.getOveralyColor() == null ? Color.red : marker.getOveralyColor();
		RuneliteColorPicker colourPicker = getColorPicker(color, " - prayer marker colour");
		colourPicker.setOnColorChange(c ->
		{
			marker.setOveralyColor(c);
			colorPrayer.setBorder(new MatteBorder(0, 0, 3, 0, marker.getOveralyColor()));
			colorPrayer.setIcon(BORDER_COLOR_ICON);
			updateColorIndicators();
		});
		colourPicker.setVisible(true);
	}
	private RuneliteColorPicker getColorPicker(Color color, String text)
	{
		RuneliteColorPicker colorPicker = plugin.getColourPickerManager().create(
				SwingUtilities.windowForComponent(this),
				color,
				marker.getDisplayName() + text,
				false);
		colorPicker.setLocationRelativeTo(this);
		colorPicker.setOnClose(c -> plugin.saveMarkers());
		return colorPicker;
	}
	private void save()
	{
		marker.setDisplayName(nameInput.getText());
		plugin.saveMarkers();

		nameInput.setEditable(false);
		updateNameActions(false);
		requestFocusInWindow();
	}
	private void cancel()
	{
		nameInput.setEditable(false);
		nameInput.setText(marker.getDisplayName());
		updateNameActions(false);
		requestFocusInWindow();
	}
	private void updateColorIndicators()
	{
		colorPrayer.setBorder(new MatteBorder(0, 0, 3, 0, marker.getOveralyColor()));
		colorPrayer.setIcon(BORDER_COLOR_ICON);
	}
	private void updateVisibility()
	{
		visibilityMarker.setIcon(marker.isVisible() ? VISIBLE_ICON : INVISIBLE_ICON);
	}
	private void updateCollapsed()
	{
		final boolean open = !marker.isCollapsed();
		rename.setVisible(open);
		containerPrayer.setVisible(open);
		expandToggle.setIcon(open ? COLLAPSE_ICON : EXPAND_ICON);
		expandToggle.setRolloverIcon(open ? COLLAPSE_HOVER_ICON : EXPAND_HOVER_ICON);
		expandToggle.setToolTipText((open ? "Collapse" : "Expand") + " marker");
	}
	private void updateNameActions(boolean saveAndCancel)
	{
		save.setVisible(saveAndCancel);
		cancel.setVisible(saveAndCancel);
		rename.setVisible(!saveAndCancel);
		expandToggle.setVisible(!saveAndCancel);
		visibilityMarker.setVisible(!saveAndCancel);
		deleteLabel.setVisible(!saveAndCancel);

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
