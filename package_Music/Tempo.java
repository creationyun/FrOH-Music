package package_Music;

import javax.sound.midi.*;

public class Tempo extends MusicElement implements Outputable {
	private int tempo;
	
	public Tempo() { this(120); }
	public Tempo(int tempo) { setTempo(tempo); }
	
	public void setTempo(int tempo) { this.tempo = tempo; }
	public int getTempo() { return tempo; }
	
	@Override
	public MidiEvent event() {
		currentTempo = tempo;
		return null;
	}
	@Override
	public String outputEvent(BeatTime time, Tempo tempo) {
		tempo.setTempo(this.tempo);
		return "";
	}
}