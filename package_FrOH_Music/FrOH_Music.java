package package_FrOH_Music;

import javax.swing.*;

import package_Music.*;
import package_MyFunction.Language;

/********** 
 * 프로그램 이름: FrOH Music
 * 현재 버전: 3.0
 * RbOH Music
 * 2.0버전: 2011/01/08 ~ 2011/01/22 개발/배포
 * 2.1버전: 2011/01/23 ~ 2011/02/19 개발/배포
 * FrOH Music
 * 3.0버전: 
**********/


public class FrOH_Music implements Program {
	public static final String ENGLISH_MENU = 
			("Choose the menu.\n" +
			"1. Player\n" +
			"2. Editor\n" +
			"3. Help\n" +
			"4. Quit\n" +
			"---- Languages\n" +
			"5. English\n" +
			"6. Korean (한국어)");
			
	public static final String KOREAN_MENU = 
			("메뉴를 선택하세요.\n" +
			"1. 플레이어\n" +
			"2. 에디터\n" +
			"3. 도움말\n" +
			"4. 종료\n" +
			"---- 언어\n" +
			"5. 영어 (English)\n" +
			"6. 한국어");
	
	/***** 메인 메서드 *****/
	public static void main(String args[]) {
		while (true) {
			String message = JOptionPane.showInputDialog(null, 
					Language.langStr(ENGLISH_MENU, KOREAN_MENU), 
					COMMON_PROGRAM_NAME, 
					JOptionPane.QUESTION_MESSAGE);
			
			if (message == null) {
				break;
				
			} else if (message.equals("1")) {
				Music[] music = Player.getMusic();
				if (music != null) {
					Music.play(music);
				}
				
			} else if (message.equals("2")) {
				EditorWindow editorWindow = new EditorWindow();
				editorWindow.openWindow();
				editorWindow.openWait();
				
			} else if (message.equals("3")) {
				Help.openHelp();
				Help.openWait();
				
			} else if (message.equals("4")) {
				break;
				
			} else if (message.equals("5")) {
				Language.setCurrentLanguage(Language.ENGLISH);
				
			} else if (message.equals("6")) {
				Language.setCurrentLanguage(Language.KOREAN);
				
			} else {
				JOptionPane.showMessageDialog(null, 
						Language.langStr("You have choosed the wrong menu.", "잘못된 메뉴를 선택하셨습니다."),
						COMMON_PROGRAM_NAME, 
						JOptionPane.ERROR_MESSAGE);
				
			}
		}
	}
}