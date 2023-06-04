package com.minimunch57.main;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;

import javax.swing.JOptionPane;

/**
 * 
 * @author Minimunch57
 *
 */
public class PropertiesManager {

	/** The <tt>CountListener</tt> responsible for keeping track of when the count changes. */
	private CountListener countListener;
	
	/** A <tt>String</tt> with the path to the properties file. */
	private String filePath;
	/** The <tt>Properties</tt> of the application. */
	private Properties props;
	/** A <tt>FileWriter</tt> responsible for writing the properties to the file. */
	private FileWriter writer;
	/** A <tt>FileReader</tt> responsible for reading the properties from the file. */
	private FileReader reader;
	/** An <code>int</code> that keeps track of the current count. */
	private int counterNum = 0;

	/**
	 * <ul>
	 * <p>	<b><i>PropertiesManager</i></b>
	 * <p>	<code>public PropertiesManager()</code>
	 * <p>	Creates a new <tt>PropertiesManager</tt>.
	 * </ul>
	 */
	public PropertiesManager() {
		filePath = System.getProperty("user.home") + "/AppData/Roaming/Minimunch57/UniCounter/UniCounter.properties";

		props = new Properties();

		createFile(false);
		loadProperties();
	}

	/**
	 * <ul>
	 * <p>	<b><i>createFile</i></b>
	 * <p>	<code>private void createFile(boolean force)</code>
	 * <p>	Creates the properties file if it is missing or the action is forced.
	 * <p>	Forcing the creation of the properties file resets all properties to the defaults.
	 * @param force - a <code>boolean</code> for whether or not to force the creation of the properties file.
	 * </ul>
	 */
	private void createFile(boolean force) {
		final File file = new File(filePath);
		if(!file.exists() || force) {
			try {
				//	Create file and missing directories
				file.getParentFile().mkdirs();
				file.createNewFile();

				//	Defaults
				props.setProperty("name", "Counter");
				props.setProperty("alwaysOnTop", Boolean.toString(true));
				props.setProperty("useNativeKL", Boolean.toString(true));

				//	Write defaults to the file.
				updateFile();
			} catch (Exception e) {}
		}
	}

	/**
	 * <ul>
	 * <p>	<b><i>loadProperties</i></b>
	 * <p>	<code>private void loadProperties()</code>
	 * <p>	Reads from the properties file and loads the settings.
	 * <p>	If the properties cannot be read, then the user is presented with the option to reset the properties to their default settings.
	 * </ul>
	 */
	private void loadProperties() {
		props.clear();
		try {
			reader = new FileReader(filePath);
			props.load(reader);
			reader.close();
		} catch (Exception e) {}

		try {
			counterNum = Integer.parseInt(props.getProperty("count"));
		} catch(NumberFormatException nfe) {
			final int confirm = JOptionPane.showConfirmDialog(null, "There was an error loading the count!\nWould you like to reset the application?\n(Saying 'No' will close UniCounter.)", "Error", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
			if(confirm == 0) {
				createFile(true);
			}
			else {
				System.exit(0);
			}
		}
	}

	/**
	 * <ul>
	 * <p>	<b><i>updateFile</i></b>
	 * <p>	<code>public void updateFile()</code>
	 * <p>	Writes the current count and settings to the properties file, updating their values.
	 * </ul>
	 */
	public void updateFile() {
		try {
			writer = new FileWriter(filePath);
			props.setProperty("count", Integer.toString(counterNum));
			props.store(writer, "UniCounter Properties");
			writer.close();
		} catch (Exception e) {}
	}

	/**
	 * <ul>
	 * <p>	<b><i>incrementCounter</i></b>
	 * <p>	<code>public void incrementCounter()</code>
	 * <p>	Increments the counter.
	 * <p>	<i>This method is shorthand for <code>setCount(getCount() + 1)</code>.</i>
	 * </ul>
	 */
	public void incrementCounter() {
		setCount(getCount() + 1);
	}

	/**
	 * <ul>
	 * <p>	<b><i>decrementCounter</i></b>
	 * <p>	<code>public void decrementCounter()</code>
	 * <p>	Decrements the counter.
	 * <p>	<i>This method is shorthand for <code>setCount(getCount() - 1)</code>.</i>
	 * </ul>
	 */
	public void decrementCounter() {
		setCount(getCount() - 1);
	}

	/**
	 * <ul>
	 * <p>	<b><i>getCount</i></b>
	 * <p>	<code>public int getCount()</code>
	 * <p>	Gets the current count.
	 * @return an <code>int</code> for the current count.
	 * </ul>
	 */
	public int getCount() {
		return counterNum;
	}

	/**
	 * <ul>
	 * <p>	<b><i>setCount</i></b>
	 * <p>	<code>public void setCount(int newCount)</code>
	 * <p>	Sets the count to the passed <code>int</code>.
	 * @param newCount - an <code>int</code> for the new count.
	 * </ul>
	 */
	public void setCount(int newCount) {
		if(newCount >= 0) {
			counterNum = newCount;
			countListener.countChanged();
		}
	}

	/**
	 * <ul>
	 * <p>	<b><i>getName</i></b>
	 * <p>	<code>public String getName()</code>
	 * <p>	Gets the current counter name.
	 * @return a <tt>String</tt> for the current counter name.
	 * </ul>
	 */
	public String getName() {
		return props.getProperty("name");
	}

	/**
	 * <ul>
	 * <p>	<b><i>setName</i></b>
	 * <p>	<code>public void setName(String name)</code>
	 * <p>	Sets the counter name to the passed <tt>String</tt>.
	 * @param name - a <tt>String</tt> for the new counter name.
	 * </ul>
	 */
	public void setName(String name) {
		props.setProperty("name", name);
		updateFile();
	}

	/**
	 * <ul>
	 * <p>	<b><i>isNativeKLEnabled</i></b>
	 * <p>	<code>public boolean isNativeKLEnabled()</code>
	 * <p>	Returns a <code>boolean</code> for whether or not the native keyboard listener is enabled.
	 * @return <code>true</code> if the native keyboard listener is enabled; <code>false</code> otherwise.
	 * </ul>
	 */
	public boolean isNativeKLEnabled() {
		return Boolean.parseBoolean(props.getProperty("useNativeKL"));
	}

	/**
	 * <ul>
	 * <p>	<b><i>setNativeKLEnabled</i></b>
	 * <p>	<code>public void setNativeKLEnabled(boolean enabled)</code>
	 * <p>	Sets whether or not the native keyboard listener should be enabled.
	 * @param enabled - <code>true</code> if the native keyboard listener should be enabled; <code>false</code> otherwise.
	 * </ul>
	 */
	public void setNativeKLEnabled(boolean enabled) {
		props.setProperty("useNativeKL", Boolean.toString(enabled));
		updateFile();
	}

	/**
	 * <ul>
	 * <p>	<b><i>isAlwaysOnTop</i></b>
	 * <p>	<code>public boolean isAlwaysOnTop()</code>
	 * <p>	Returns a <code>boolean</code> for whether or not the window is to always be on top.
	 * @return <code>true</code> if the window is to always be on top; <code>false</code> otherwise.
	 * </ul>
	 */
	public boolean isAlwaysOnTop() {
		return Boolean.parseBoolean(props.getProperty("alwaysOnTop"));
	}

	/**
	 * <ul>
	 * <p>	<b><i>setAlwaysOnTop</i></b>
	 * <p>	<code>public void setAlwaysOnTop(boolean alwaysOnTop)</code>
	 * <p>	Sets whether or not the window is to always be on top.
	 * @param alwaysOnTop - <code>true</code> if the window is to always be on top; <code>false</code> otherwise.
	 * </ul>
	 */
	public void setAlwaysOnTop(boolean alwaysOnTop) {
		props.setProperty("alwaysOnTop", Boolean.toString(alwaysOnTop));
		updateFile();
	}

	/**
	 * <ul>
	 * <p>	<b><i>setCountListener</i></b>
	 * <p>	<code>public void setCountListener(CountListener cl)</code>
	 * <p>	Sets the <tt>CountListener</tt>.
	 * @param cl - the <tt>CountListener</tt> to use.
	 * </ul>
	 */
	public void setCountListener(CountListener cl) {
		countListener = cl;
	}
}
