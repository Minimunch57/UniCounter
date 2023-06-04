package com.minimunch57.main;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

/**
 * 
 * @author Minimunch57
 *
 */
public class NativeKeyboardListener implements NativeKeyListener {

	/** A <code>boolean</code> for whether or not the NativeKeyboardListener is enabled. */
	private boolean enabled = true;
	/** A <code>boolean</code> for whether or not the CONTROL key is pressed. */
	private boolean ctrlPressed = false;

	/**
	 * <ul>
	 * <p>	<b><i>NativeKeyboardListener</i></b>
	 * <p>	<code>public NativeKeyboardListener()</code>
	 * <p>	Creates a new <tt>NativeKeyboardListener</tt>.
	 * </ul>
	 */
	public NativeKeyboardListener() {
		//	Disable Logging
		final Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);

		//	Register NativeKeyboardListener
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException nhe) {
			nhe.printStackTrace();
		}
		GlobalScreen.addNativeKeyListener(this);
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent nke) {
		//	Nothing Until Overridden
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent nke) {
		//	Nothing Until Overridden
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent nke) {
		//	Do Nothing
	}

	/**
	 * <ul>
	 * <p>	<b><i>isEnabled</i></b>
	 * <p>	<code>public boolean isEnabled()</code>
	 * <p>	Returns a <code>boolean</code> for whether or not this listener is enabled.
	 * @return <code>true</code> if this listener is enabled; <code>false</code> otherwise.
	 * </ul>
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * <ul>
	 * <p>	<b><i>setEnabled</i></b>
	 * <p>	<code>public void setEnabled(boolean isEnabled)</code>
	 * <p>	Sets the <code>boolean</code> responsible for keeping track of whether or not this listener is enabled.
	 * @param isEnabled - a <code>boolean</code> for whether or not this listener is enabled.
	 * </ul>
	 */
	public void setEnabled(boolean isEnabled) {
		enabled = isEnabled;
	}

	/**
	 * <ul>
	 * <p>	<b><i>isCTRLPressed</i></b>
	 * <p>	<code>public boolean isCTRLPressed()</code>
	 * <p>	Returns a <code>boolean</code> for whether or not the <b>CONTROL</b> key is pressed.
	 * @return <code>true</code> if the <b>CONTROL</b> key is pressed; <code>false</code> otherwise.
	 * </ul>
	 */
	public boolean isCTRLPressed() {
		return ctrlPressed;
	}

	/**
	 * <ul>
	 * <p>	<b><i>setCTRLPressed</i></b>
	 * <p>	<code>public void setCTRLPressed(boolean isPressed)</code>
	 * <p>	Sets whether or not the <b>CONTROL</b> key is pressed.
	 * @param isPressed - a <code>boolean</code> for whether or not the <b>CONTROL</b> key is pressed.
	 * </ul>
	 */
	public void setCTRLPressed(boolean isPressed) {
		ctrlPressed = isPressed;
	}
}
