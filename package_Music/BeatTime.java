package package_Music;

import javax.sound.midi.MidiEvent;

public class BeatTime extends MusicElement implements Outputable {
	private long time;
	
	public BeatTime( ) { this(4L); }
	public BeatTime( long time ) { set( time ); }
	
	public void set( long time ) { this.time = time; }
	public long get( ) { return time; }

	@Override
	public MidiEvent event() {
		currentTime += 60.0 / currentTempo * time;
		return null;
	}
	@Override
	public String outputEvent(BeatTime time, Tempo tempo) {
		time.set(this.time);
		return "";
	}
}