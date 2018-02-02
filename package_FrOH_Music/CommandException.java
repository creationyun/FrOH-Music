package package_FrOH_Music;

public class CommandException extends Exception {
	private static final long serialVersionUID = 1L;
	private final int ERROR_CODE;
	public static final String[] ERROR_CONTENTS = {
		"Can not find the error information.",
		"Command is invalid.",
		"Command is wrong."
	};
	public static final int UNKNOWN = 0;
	public static final int INVALID = 1;
	public static final int WRONG = 2;
	
	public CommandException() { this(0); }
	public CommandException(int errCode) {
		super(ERROR_CONTENTS[errCode]);
		ERROR_CODE = errCode;
	}
	public CommandException(int errCode, int pos) {
		super(ERROR_CONTENTS[errCode] + " Commands are: " + pos);
		ERROR_CODE = errCode;
	}
	
	public int getErrCode( ) { return ERROR_CODE; }
}