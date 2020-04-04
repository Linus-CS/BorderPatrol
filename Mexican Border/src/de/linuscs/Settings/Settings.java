package de.linuscs.Settings;

import java.io.FileOutputStream;
import java.util.prefs.Preferences;

public class Settings {

	public Preferences pref = Preferences.userNodeForPackage(Settings.class);
	public static Settings instance;
	
	public Settings(String name) {
		instance = this;
		
		try {
			pref.exportNode(new FileOutputStream(name + ".xml"));
			System.out.println("Created " + name + ".xml");
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void addSetting(String category, String input) {
		pref.put(category, input);
	}
	
	public String getSetting(String key, String def) {
		return pref.get(key, def);
	}
	
	public String getSetting(String key) {
		return pref.get(key, "1");
	}
}