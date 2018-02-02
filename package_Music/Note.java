package package_Music;

import javax.sound.midi.*;

public class Note extends Pitch {
	public Note( ) throws PitchException { super( ); }
	public Note( int pitchNumber ) throws PitchException { super( pitchNumber ); }
	
	public MidiEvent event() {
		return makeEvent(PLAY_SOUND, DEFAULT_CHANNEL, getPitchNum( ), 
				currentDynamics, currentTime);
	}
}