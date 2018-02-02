package package_Music;

import javax.sound.midi.*;

public abstract class MusicElement {
	public static long currentTime = 0L;
	public static int currentDynamics = Dynamics.DEFAULT;
	public static int currentTempo = 120;
	
	public static final int PLAY_SOUND = 144;
	public static final int STOP_SOUND = 128;
	public static final int DEFAULT_CHANNEL = 1;
	
	public abstract MidiEvent event( );
	public static void init() {
		currentTime = 0L;
		currentDynamics = Dynamics.DEFAULT; //Mezzo forte
		currentTempo = 120;
	}
	
	public static MidiEvent makeEvent(int comd, int chan, int note, int dynamic, long tick){
		MidiEvent event = null;
		try {
			ShortMessage sm = new ShortMessage( );
			sm.setMessage(comd, chan, note, dynamic);
			event = new MidiEvent(sm, tick);
		} catch (Exception e) {
			e.printStackTrace( );
		}
		return event;
	}
}