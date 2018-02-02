package package_MyFunction;

public class Language {
	private static int currentLanguage = Language.ENGLISH; // English
	
	public static void setCurrentLanguage(int language) {
		if (language >= MIN_RANGE_LANGUAGE && language <= MAX_RANGE_LANGUAGE) {
			currentLanguage = language;
		}
	}
	
	public static String langStr(String english, String korean) {
		String result = "";
		
		ChooseLanguage:
			switch (currentLanguage) {
			case ENGLISH:
				result = english;
				break ChooseLanguage;
				
			case KOREAN:
				result = korean;
				break ChooseLanguage;
			}
		
		return result;
	}
	
	public static final int ENGLISH = 1;
	public static final int KOREAN = 2;
	
	public static final int MIN_RANGE_LANGUAGE = ENGLISH;
	public static final int MAX_RANGE_LANGUAGE = KOREAN;
}
