package package_Music;

import javax.sound.midi.*;

public class SequencePlayer {
	private static Sequencer player;
	private static Sequence seq;
	private Track track;
	
	public static final int TICK = 12000;
	
	public SequencePlayer() {
		track = seq.createTrack();
	}
	
	public void add(MidiEvent e) {
		track.add(e);
	}
	public MidiEvent getMidiEvent(int index) {
		return track.get(index);
	}
	public int getSize() {
		return track.size();
	}
	
	public static void init() {
		try {
			player = MidiSystem.getSequencer();
			player.open();
			seq = new Sequence(Sequence.PPQ, TICK / 2);
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
	}
	
	public static void play() {
		try {
			player.setSequence(seq);
			player.start();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
	}
	
	public static void stop() {
		player.stop( );
		player.close( );
	}
	
	public static boolean isRunning() {
		return player.isRunning();
	}
}
