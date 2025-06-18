package com.coopermor.prayermarkers.ui.adapters;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFormattedTextField;

public class BorderWidthKeyAdapter extends KeyAdapter
{
	private final JFormattedTextField field;

	public BorderWidthKeyAdapter(JFormattedTextField field)
	{
		this.field = field;
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		switch (e.getKeyCode())
		{
			case KeyEvent.VK_ENTER:
			case KeyEvent.VK_ESCAPE:
				field.transferFocus();
				break;
		}
	}
}
