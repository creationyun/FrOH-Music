package package_Music;

import javax.sound.midi.*;

public class Lyric extends MusicElement implements Outputable {
	private String text;
	
	public Lyric( ) { this(""); }
	public Lyric( String text ) { set( text ); }
	
	public void set( String text ) { this.text = text; }
	public String get( ) { return text; }
	
	@Override
	public MidiEvent event( ) {
		return null;
	}
	@Override
	public String outputEvent(BeatTime time, Tempo tempo) {
		return new String(text);
	}
}