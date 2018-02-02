package package_Music;

public class PitchException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public PitchException( ) {
		this("예외 메시지가 없습니다.");
	}
	public PitchException(String errorMessage) {
		super(errorMessage);
	}
}