package package_Music;

import javax.sound.midi.*;

public class Dynamics extends MusicElement {
	private int dynamicNumber;
	
	public Dynamics( ) { this(DEFAULT); }
	public Dynamics( int dynamicNumber ) { set(dynamicNumber); }
	
	public void set(int dynamicNumber) {
		if (dynamicNumber >= PIANISSIMO && dynamicNumber <= FORTISSIMO)
			this.dynamicNumber = dynamicNumber;
	}
	public int get( ) { return dynamicNumber; }
	
	public static final int PIANISSIMO = 0x1F;
	public static final int PIANO = 0x2F;
	public static final int MEZZO_PIANO = 0x3F;
	public static final int DEFAULT = 0x4F;
	public static final int MEZZO_FORTE = 0x5F;
	public static final int FORTE = 0x6F;
	public static final int FORTISSIMO = 0x7F;

	public MidiEvent event( ) {
		currentDynamics = dynamicNumber;
		return null;
	}
}