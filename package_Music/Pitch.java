package package_Music;

public abstract class Pitch extends MusicElement {
	private int pitchNumber;
	
	public Pitch( ) throws PitchException { this(60); }
	public Pitch( int pitchNumber ) throws PitchException { setPitchNum(pitchNumber); }
	
	public void setPitchNum(int pitchNumber) throws PitchException {
		if (pitchNumber >= 21 && pitchNumber <= 108)
			this.pitchNumber = pitchNumber;
		else
			throw new PitchException( "잘못된 음의 코드입니다: " + pitchNumber );
	}
	public int getPitchNum( ) { return pitchNumber; }
	public static int pitchStringToNumber( int octave, String pitchName ) throws PitchException {
		int result = octave * 12;
		
		switch (pitchName.length( )) {
		case 2:
			/* 임시표가 포함되어 있을 경우 */
			char accidental = pitchName.charAt(1);
			if (accidental == '#') {
				result++;
			} else if (accidental == 'b') {
				result--;
			} else {
				throw new PitchException( "2번째 문자(" + accidental + ")가 임시표가 아닙니다." );
			}
		case 1:
			/* 임시표가 포함되어 있지 않을 경우와
			 * 임시표가 포함되어도 같이 수행되는 문장임 (뒤에 break가 없기 때문에) */
			char basicPitchName = pitchName.charAt(0);
			
			switch (basicPitchName) {
			case 'C': case 'c': result += 0; break;
			case 'D': case 'd': result += 2; break;
			case 'E': case 'e': result += 4; break;
			case 'F': case 'f': result += 5; break;
			case 'G': case 'g': result += 7; break;
			case 'A': case 'a': result += 9; break;
			case 'B': case 'b': result += 11; break;
			default:
				throw new PitchException( "1번째 문자(" + basicPitchName + ")가 기본 음이름이 아닙니다." );
			}
			break;
		default:
			throw new PitchException( "음이름의 길이(" + pitchName.length( ) + ")가 잘못되었습니다." );
		}
		
		if (result >= 21 && result <= 108)
			return result;
		else
			throw new PitchException( "잘못된 음의 코드입니다: " + result );
	}
}