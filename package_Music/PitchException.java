package package_Music;

public class PitchException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public PitchException( ) {
		this("���� �޽����� �����ϴ�.");
	}
	public PitchException(String errorMessage) {
		super(errorMessage);
	}
}