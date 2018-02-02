package package_Music;

import java.util.*;
import javax.swing.*;

public class LyricThread implements Runnable {
	private ArrayList<Outputable> commands = new ArrayList<Outputable>();
	private static JTextArea lyricArea;

	public static void setLyricArea(JTextArea lyricArea) {
		LyricThread.lyricArea = lyricArea;
	}

	public void add(Outputable o) {
		commands.add(o);
	}

	@Override
	public synchronized void run() {
		Tempo currentTempo = new Tempo(120);
		String output = new String("");
		BeatTime time = new BeatTime(0L);
		try {
			for (int cmdIdx = 0; cmdIdx < commands.size(); cmdIdx++) {
				Outputable posCmd = commands.get(cmdIdx);
				output = posCmd.outputEvent(time, currentTempo);

				if (output != null && !output.equals("")) {
					lyricArea.setText(lyricArea.getText() + output);
					int length = lyricArea.getText().length();
					lyricArea.select(length-output.length(), length);
				}

				Thread.sleep( (long) (60. / currentTempo.getTempo() * ((double)time.get() / SequencePlayer.TICK) * 1000.0) );
				output = ""; time.set(0L);
			}
		} catch (InterruptedException xcp) {
		}
	}
}
