package com.minimunch57.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

import com.minimunch57.main.CountListener;
import com.minimunch57.main.KeyboardListener;
import com.minimunch57.main.NativeKeyboardListener;
import com.minimunch57.main.PropertiesManager;

/**
 * 
 * @author Minimunch57
 *
 */
public class GUI extends JFrame {
	
	/** The <tt>ImageIcon</tt> for the app icon. */
	final ImageIcon iconApp = new ImageIcon(GUI.class.getResource("/com/minimunch57/resources/iconApp.png"));
	/** The <tt>ImageIcon</tt> for the exit icon. */
	final ImageIcon iconExit = new ImageIcon(GUI.class.getResource("/com/minimunch57/resources/iconExit.png"));

    /** The serial ID for this <tt>GUI</tt>. */
    private static final long serialVersionUID = 1L;
    /** A <tt>JPanel</tt> for this <tt>GUI</tt>'s content pane. */
    private JPanel contentPane;
    /** A <tt>JLabel</tt> that shows the current counter name. */
    private JLabel lblTitle;
    /** A <tt>JLabel</tt> that shows the current counter number. */
    private JLabel lblNum;

    /** The <tt>PropertiesManager</tt> used to manage the properties file. */
    private PropertiesManager propsManager;
    /** The <tt>KeyboardListener</tt> used to listen for specific keyboard inputs. */
    private KeyboardListener keyboardListener;
    /** The <tt>KeyboardListener</tt> used to listen for all keyboard inputs. */
    private NativeKeyboardListener nativeKeyboardListener;
    
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    final GUI frame = new GUI();
                    frame.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    /**
     * <ul>
     * <p>	<b><i>GUI</i></b>
     * <p>	<code>public GUI()</code>
     * <p>	Creates a new <tt>GUI</tt>.
     * </ul>
     */
    public GUI() {
        this.setIconImage(iconApp.getImage());
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setTitle("UniCounter");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(100, 100, 450, 300);

        contentPane = new JPanel();
        contentPane.setFocusable(false);
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        lblTitle = new JLabel("Counter");
        lblTitle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				if(me.getClickCount() == 2) {
					final String input = JOptionPane.showInputDialog(null, "What would you like the new name to be?\n\nEx: Cool Counter");
					if(input != null) {
						final String newName = input.replaceAll("[ ]+", " ").trim();
						propsManager.setName(newName);
						SwingUtilities.invokeLater(() -> {
							lblTitle.setText(newName);
						});
					}
				}
			}        	
        });
        lblTitle.setFocusable(false);
        lblTitle.setVerticalAlignment(3);
        lblTitle.setFont(new Font("Agency FB", 0, 70));
        lblTitle.setHorizontalAlignment(0);
        lblTitle.setBounds(0, 0, 444, 100);
        contentPane.add(lblTitle);

        lblNum = new JLabel("");
        lblNum.setFocusable(false);
        lblNum.setVerticalAlignment(1);
        lblNum.setHorizontalAlignment(0);
        lblNum.setFont(new Font("Agency FB", 1, 80));
        lblNum.setBounds(0, 110, 444, 139);
        contentPane.add(lblNum);

        // Creates and sets the keyboard listeners.
        setKeyboardListener();
        setNativeKeyboardListener();
        
        // Setup the properties manager.
        setupPropertiesManager();
    }
    
    /**
     * <ul>
     * <p>	<b><i>setupPropertiesManager</i></b>
     * <p>	<code>private void setupPropertiesManager()</code>
     * <p>	Sets up the <tt>PropertiesManager</tt>, including registering its count listener.
     * </ul>
     */
    private void setupPropertiesManager() {
    	propsManager = new PropertiesManager();
    	propsManager.setCountListener(new CountListener() {
			@Override
			public void countChanged() {
				SwingUtilities.invokeLater(() -> {
					lblNum.setText(Integer.toString(propsManager.getCount()));
				});
				propsManager.updateFile();
			}
        });
    	
    	//	Setup Based on Current Properties
    	propsManager.setCount(propsManager.getCount());
    	nativeKeyboardListener.setEnabled(propsManager.isNativeKLEnabled());
    	SwingUtilities.invokeLater(() -> {
    		this.setAlwaysOnTop(propsManager.isAlwaysOnTop());
    		lblTitle.setText(propsManager.getName());
    	});
    }

    /**
     * <ul>
     * <p>	<b><i>setKeyboardListener</i></b>
     * <p>	<code>private void setKeyboardListener()</code>
     * <p>	Sets the standard <tt>KeyboardListener</tt> for this <tt>GUI</tt>.
     * </ul>
     */
    private void setKeyboardListener() {
        keyboardListener = new KeyboardListener() {
            @Override
            public void keyPressed(KeyEvent ke) {
                readStandardKeyPress(ke.getKeyCode());
            }
        };
        this.addKeyListener(keyboardListener);
    }
    
    /**
     * <ul>
     * <p>	<b><i>setNativeKeyboardListener</i></b>
     * <p>	<code>private void setNativeKeyboardListener()</code>
     * <p>	Sets the <tt>NativeKeyboardListener</tt> for this <tt>GUI</tt>.
     * </ul>
     */
    private void setNativeKeyboardListener() {
        nativeKeyboardListener = new NativeKeyboardListener() {
        	@Override
            public void nativeKeyPressed(NativeKeyEvent nke) {
        		if(isEnabled()) {
        			if (nke.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
        				setCTRLPressed(true);
        			}
        		}
            }
        	
        	@Override
            public void nativeKeyReleased(NativeKeyEvent nke) {
        		if(isEnabled()) {
        			if (nke.getKeyCode() == NativeKeyEvent.VC_CONTROL) {
        				setCTRLPressed(false);
        			}
        			else if (isCTRLPressed() && (nke.getKeyCode() == NativeKeyEvent.VC_UP || nke.getKeyCode() == NativeKeyEvent.VC_RIGHT || nke.getKeyCode() == NativeKeyEvent.VC_SPACE)) {
        				propsManager.incrementCounter();
        			}
        			else if (isCTRLPressed() && (nke.getKeyCode() == NativeKeyEvent.VC_DOWN || nke.getKeyCode() == NativeKeyEvent.VC_LEFT) ) {
        				propsManager.decrementCounter();
        			}
        		}
            }
        };
    }

    /**
     * <ul>
     * <p>	<b><i>readStandardKeyPress</i></b>
     * <p>	<code>private void readStandardKeyPress(int keyCode)</code>
     * <p>	Performs actions based on the passed key code received from the standard keyboard listener.
     * @param keyCode - an <code>int</code> representing the key pressed.
     * </ul>
     */
    private void readStandardKeyPress(int keyCode) {
        if(keyCode == KeyEvent.VK_I) {
            JOptionPane.showMessageDialog(null, "UniCounter v1.0\nCreated by Minimunch57", "UniCounter Info", JOptionPane.INFORMATION_MESSAGE);
        }
        else if(keyCode == KeyEvent.VK_G) {
        	propsManager.setNativeKLEnabled(!nativeKeyboardListener.isEnabled());
        	nativeKeyboardListener.setEnabled(!nativeKeyboardListener.isEnabled());
        	JOptionPane.showMessageDialog(null, "Global Keyboard Inputs Enabled: " + Boolean.toString(nativeKeyboardListener.isEnabled()).toUpperCase());
        }
        else if(keyCode == KeyEvent.VK_H) {
        	final boolean alwaysOnTop = this.isAlwaysOnTop();
        	SwingUtilities.invokeLater(() -> {
        		this.setAlwaysOnTop(!alwaysOnTop);
        	});
        	propsManager.setAlwaysOnTop(!alwaysOnTop);
        	JOptionPane.showMessageDialog(null, "Always On Top Enabled: " + Boolean.toString(!alwaysOnTop).toUpperCase());
        }
        else if(keyCode == KeyEvent.VK_0) {
        	final int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to reset the count to zero?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        	if (confirm == 0) {
        		propsManager.setCount(0);
        	}
        }
        else if(keyCode == KeyEvent.VK_ESCAPE) {
            final int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to close UniCounter?", "Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, iconExit);
            if (confirm == 0) {
                System.exit(0);
            }
        }
        else if(!nativeKeyboardListener.isCTRLPressed()) {
        	if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_SPACE) {
        		propsManager.incrementCounter();
        	}
        	else if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_LEFT) {
        		propsManager.decrementCounter();
        	}
        }
    }
}
