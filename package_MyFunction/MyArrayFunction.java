package package_MyFunction;

public class MyArrayFunction {
	public static boolean isValueIncluded(Object[] array, Object value) {
		for (int arrayIdx = 0; arrayIdx < array.length; arrayIdx++) {
			if (array[arrayIdx].equals(value)) {
				return true;
			}
		}
		return false;
	}
}
