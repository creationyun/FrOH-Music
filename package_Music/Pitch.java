package package_Music;

public abstract class Pitch extends MusicElement {
	private int pitchNumber;
	
	public Pitch( ) throws PitchException { this(60); }
	public Pitch( int pitchNumber ) throws PitchException { setPitchNum(pitchNumber); }
	
	public void setPitchNum(int pitchNumber) throws PitchException {
		if (pitchNumber >= 21 && pitchNumber <= 108)
			this.pitchNumber = pitchNumber;
		else
			throw new PitchException( "�߸��� ���� �ڵ��Դϴ�: " + pitchNumber );
	}
	public int getPitchNum( ) { return pitchNumber; }
	public static int pitchStringToNumber( int octave, String pitchName ) throws PitchException {
		int result = octave * 12;
		
		switch (pitchName.length( )) {
		case 2:
			/* �ӽ�ǥ�� ���ԵǾ� ���� ��� */
			char accidental = pitchName.charAt(1);
			if (accidental == '#') {
				result++;
			} else if (accidental == 'b') {
				result--;
			} else {
				throw new PitchException( "2��° ����(" + accidental + ")�� �ӽ�ǥ�� �ƴմϴ�." );
			}
		case 1:
			/* �ӽ�ǥ�� ���ԵǾ� ���� ���� ����
			 * �ӽ�ǥ�� ���ԵǾ ���� ����Ǵ� ������ (�ڿ� break�� ���� ������) */
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
				throw new PitchException( "1��° ����(" + basicPitchName + ")�� �⺻ ���̸��� �ƴմϴ�." );
			}
			break;
		default:
			throw new PitchException( "���̸��� ����(" + pitchName.length( ) + ")�� �߸��Ǿ����ϴ�." );
		}
		
		if (result >= 21 && result <= 108)
			return result;
		else
			throw new PitchException( "�߸��� ���� �ڵ��Դϴ�: " + result );
	}
}