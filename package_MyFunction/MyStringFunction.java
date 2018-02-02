package package_MyFunction;

public class MyStringFunction {
	public static String insert(String str, int pos, String insertString) {
		if (pos >= 0) return str.substring(0, pos) + insertString + str.substring(pos);
		else return null;
	}
}
