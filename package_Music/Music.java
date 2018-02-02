package package_Music;

import java.util.*;

import javax.sound.midi.*;

public class Music {
	private ArrayList<MusicElement> elements = new ArrayList<MusicElement>( );

	private static SequencePlayer[] player;
	private static Thread[] threads;
	private static LyricThread[] lyricThreads;
	private static Thread playRunThread;

	public static void play(Music[] music) {
		player = new SequencePlayer[music.length];
		threads = new Thread[music.length];
		lyricThreads = new LyricThread[music.length];

		SequencePlayer.init();
		LyricWindow.init();

		for (int initIdx = 0; initIdx < lyricThreads.length; initIdx++) {
			player[initIdx] = new SequencePlayer();
			lyricThreads[initIdx] = new LyricThread();
			threads[initIdx] = new Thread(lyricThreads[initIdx], "Lyric Thread " + initIdx);
		}

		for (int musicIdx = 0; musicIdx < music.length; musicIdx++) {
			Music posMusic = music[musicIdx];
			SequencePlayer posSequence = player[musicIdx];
			LyricThread posLyricThread = lyricThreads[musicIdx];
			for (int musicInTrack = 0; musicInTrack < posMusic.size(); musicInTrack++) {
				MidiEvent midiEvent = null;
				MusicElement posMusicElement = posMusic.get(musicInTrack);
				midiEvent = posMusicElement.event();

				if (posMusicElement instanceof Outputable) {
					posLyricThread.add((Outputable) posMusicElement);
				}

				if (midiEvent != null) {
					posSequence.add(midiEvent);
				}
			}
			MusicElement.init();
		}

		PlayThread playThread = new PlayThread();

		playThread.setCurrentMinute(LyricWindow.getCurrentMinute());
		playThread.setCurrentSecond(LyricWindow.getCurrentSecond());
		playThread.setPlayMinute(LyricWindow.getPlayMinute());
		playThread.setPlaySecond(LyricWindow.getPlaySecond());

		playThread.init();
		playThread.calculatePlayTime(music);

		playThread.setPlayTime();

		playRunThread = new Thread(playThread, "Play Time Calculate");

		SequencePlayer.play();

		LyricWindow.openWindow();
		LyricWindow.lyricAreaInit();

		LyricThread.setLyricArea(LyricWindow.getLyricArea());

		for (int lyricThreadIdx = 0; lyricThreadIdx < lyricThreads.length; lyricThreadIdx++) {
			threads[lyricThreadIdx].start();
		}

		playRunThread.start();

		while (true) {
			try {
				if (SequencePlayer.isRunning() && LyricWindow.isWindowOpened()) {
					Thread.sleep(500);
				} else {
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}

		for (int lyricThreadIdx = 0; lyricThreadIdx < lyricThreads.length; lyricThreadIdx++) {
			threads[lyricThreadIdx].interrupt();
		}

		playRunThread.interrupt();

		LyricWindow.closeWindow();
		LyricWindow.disposeWindow();
		SequencePlayer.stop();
	}
	public void add(MusicElement me) { elements.add(me); }
	public MusicElement get(int index) { return elements.get(index); }
	public int size() { return elements.size(); }
	public void remove(int index) { elements.remove(index); }
	public void remove(MusicElement me) { elements.remove(me); }
}
