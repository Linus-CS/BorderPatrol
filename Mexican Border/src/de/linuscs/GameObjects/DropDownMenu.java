package de.linuscs.GameObjects;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import de.linuscs.Game.GameObject;
import de.linuscs.Settings.Settings;

public class DropDownMenu extends GameObject {

	private ArrayList<BoxOption> options = new ArrayList<BoxOption>();

	private float MenuX;
	private float MenuY;
	private float MenuWidth;
	private float MenuHeight;

	private float x;
	private float y;
	private float width;
	private float height;

	private boolean dropDown;
	private String setting;

	private BoxOption choice;

	public DropDownMenu(int MenuX, int MenuY, int width, int height, String setting) {
		this.MenuX = (float) MenuX;
		this.MenuY = (float) MenuY;
		this.MenuWidth = (float) width;
		this.MenuHeight = (float) height;

		this.x = (float) MenuX;
		this.y = (float) MenuY;
		this.width = (float) width;
		this.height = (float) height;
		
		this.setting = setting;
	}

	@Override
	public void init() {
		// if there is no input add input null
		if (options.size() == 0) {
			options.add(new BoxOption("Null", Math.round(x), calculateY(), Math.round(width), Math.round(height)));
		}
		for (BoxOption option : options) {
			option.init();
		}
		dropDown = false;

		choice = new BoxOption(Settings.instance.getSetting("resolution"), Math.round(MenuX), Math.round(MenuY),
				Math.round(MenuWidth), Math.round(MenuHeight));
		choice.init();
	}

	@Override
	public void update() {
		// make sure to still drop down while hovering over options
		if (dropDown) {
			MenuHeight = this.height * options.size();
		} else {
			MenuY = this.y;
			MenuHeight = this.height;
		}

		choice.setName(Settings.instance.getSetting("resolution"));
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// execute onMoving in all option boxes
		for (BoxOption boxOption : options) {
			boxOption.onMoving(e);
		}

		// drop down if you hover over menu
		if (e.getX() < this.MenuX || e.getX() > this.MenuX + this.MenuWidth || e.getY() < this.MenuY
				|| e.getY() > this.MenuY + this.MenuHeight) {
			dropDown = false;
			return;
		}
		dropDown = true;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for (BoxOption option : options) {
			option.onClick(e);
			if (option.isChoice()) {
			 	Settings.instance.addSetting(setting, option.getName());
				option.setChoice(false);
			}
		}
	}

	@Override
	public void render(Graphics g) {
		// render all boxes if drop down is active else only render first one
		if (dropDown) {
			for (BoxOption option : options) {
				option.render(g);
			}
		} else {
			// render a new BoxOption with the correct resolution
			choice.render(g);
		}
	}

	private int calculateY() {
		float newY;
		newY = this.y + (this.height * options.size());
		return Math.round(newY);
	}

	// add OptionBox
	public void addOption(String addOption) {
		options.add(new BoxOption(addOption, Math.round(x), calculateY(), Math.round(width), Math.round(height)));
	}

	public void removeOption(String removeOption) {
		for (BoxOption option : options) {
			if (option.getName() == removeOption) {
				options.remove(option);
			}
		}
	}

	// remove a OptionBox
	public void removeOption(int index) {
		options.remove(index);
		if (!(index == options.size() - 1)) {
			for (int i = index + 1; i < options.size() - 1; i++) {
				options.set(i - 1, options.get(i));
				options.remove(i);
			}
		}
	}

	public void setBaseColor(Color color) {
		for (BoxOption option : options) {
			option.setBaseColor(color);
		}
	}

	public void setHoverColor(Color color) {
		for (BoxOption option : options) {
			option.setHoverColor(color);
		}
	}

	public void setTextColor(Color color) {
		for (BoxOption option : options) {
			option.setTextColor(color);
		}
	}
}
