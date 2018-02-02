package package_Music;

import java.awt.BorderLayout;
import java.awt.event.WindowListener;
import javax.swing.*;
import package_MyFunction.Language;

public class LyricWindow {
	public static final String FRAME_TITLE_ENGLISH = "Lyrics";
	public static final String FRAME_TITLE_KOREAN = "°¡»ç";
	
	private static JFrame frame = new JFrame();
	private static JTextArea lyricArea = new JTextArea();
	
	private static JPanel playTime = new JPanel();
	
	private static JLabel currentMinute = new JLabel();
	private static JLabel currentSecond = new JLabel();
	private static JLabel playMinute = new JLabel();
	private static JLabel playSecond = new JLabel();
	
	private static JScrollPane lyricAreaScroll = new JScrollPane(lyricArea,
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
	);
	
	static {
		frame.setSize(500, 500);
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);
		
		frame.add(lyricAreaScroll, "Center");
		
		playTime.add(currentMinute);
		playTime.add(new JLabel(":"));
		playTime.add(currentSecond);
		
		playTime.add(new JLabel("/"));
		
		playTime.add(playMinute);
		playTime.add(new JLabel(":"));
		playTime.add(playSecond);
		
		frame.add(playTime, "South");
		
		lyricArea.setEditable(false);
	}
	
	public static void lyricAreaInit() {
		lyricArea.setText("");
	}
	
	public static void init() {
		currentMinute.setText("");
		currentSecond.setText("");
		playMinute.setText("");
		playSecond.setText("");
	}
	
	static JTextArea getLyricArea() {
		return lyricArea;
	}
	
	static JLabel getCurrentMinute() { return currentMinute; }
	static JLabel getCurrentSecond() { return currentSecond; }
	static JLabel getPlayMinute() { return playMinute; }
	static JLabel getPlaySecond() { return playSecond; }
	
	public static void openWindow() {
		lyricAreaInit();
		frame.setTitle(Language.langStr(FRAME_TITLE_ENGLISH, FRAME_TITLE_KOREAN));
		frame.setVisible(true);
	}
	public static void closeWindow() {
		frame.setVisible(false);
	}
	public static void disposeWindow() {
		frame.dispose();
	}
	public static boolean isWindowOpened() {
		return frame.isVisible();
	}
	public static void addWindowListener(WindowListener windowListener) {
		frame.addWindowListener(windowListener);
	}
}
