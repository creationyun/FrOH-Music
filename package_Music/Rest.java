package package_Music;

import javax.sound.midi.*;

public class Rest extends Pitch {
	public Rest( ) throws PitchException { super( ); }
	public Rest( int pitchNumber ) throws PitchException { super( pitchNumber ); }

	public MidiEvent event() {
		return makeEvent(STOP_SOUND, DEFAULT_CHANNEL, getPitchNum( ), 
				currentDynamics, currentTime);
	}
}