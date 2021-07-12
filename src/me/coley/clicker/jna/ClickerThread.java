package me.coley.clicker.jna;

import java.util.Random;

import me.coley.clicker.Values;
import me.coley.clicker.ui.MainGUI;
import me.coley.clicker.util.NumberUtil;
import me.coley.simplejna.Mouse;
import me.coley.simplejna.Windows;

public class ClickerThread extends Thread {
	private final Random r = new Random();
	private final MainGUI gui;

	public ClickerThread(MainGUI clicker) {
		this.gui = clicker;
	}

	@Override
	public void run() {
		while (gui.clicker.getStatus()) {
			if (canClick()) {
				Mouse.mouseLeftClick(-1, -1);
			}
			try {
				/*
				double dev = gui.settings.getNumericSetting(Values.SET_DEV_DELAY).getCurrent();
				double mean = gui.settings.getNumericSetting(Values.SET_AVG_DELAY).getCurrent();
				int min = gui.settings.getNumericSetting(Values.SET_MIN_DELAY).getCurrent();
				int max = gui.settings.getNumericSetting(Values.SET_MAX_DELAY).getCurrent();
				 */

				// just hardcoding because the ui keeps breaking
				double dev = 32;
				double mean = 85;
				int min = 48;
				int max = 149;
				// Gaussian random sleep. Tends to sleep with times
				// around the mean. Times near the bounds (min/max) are
				// less common.
				long sleep = (long) NumberUtil.clamp(Math.round(r.nextGaussian() * dev + mean), min, max);
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Checks if a target window needs to be active.
	 * 
	 * @return
	 */
	private boolean canClick() {
		String target = gui.clicker.getTarget();
		if (gui.settings.getBooleanSetting(Values.SET_WINDOW_TARGET).getCurrent() && target != null) {
			if (Windows.getCurrentWindowTitle().equals(target)) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}
}
