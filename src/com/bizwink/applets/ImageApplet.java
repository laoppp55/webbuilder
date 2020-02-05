package com.bizwink.applets;

import javax.swing.*;

public class ImageApplet extends JApplet {

	public void init() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Object o = LookAndFeel.makeIcon(
							ImageApplet.this.getClass(),
							"lem.jpg");
				UIDefaults.LazyValue v = (UIDefaults.LazyValue)o;
				Icon icon = (Icon)v.createValue(null);
				getContentPane().add(new JLabel(icon));
				validate();
			}
		});
	}
}
