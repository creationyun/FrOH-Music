package package_FrOH_Music;

import javax.swing.*;

import package_Music.*;
import package_MyFunction.Language;

/********** 
 * ���α׷� �̸�: FrOH Music
 * ���� ����: 3.0
 * RbOH Music
 * 2.0����: 2011/01/08 ~ 2011/01/22 ����/����
 * 2.1����: 2011/01/23 ~ 2011/02/19 ����/����
 * FrOH Music
 * 3.0����: 
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
			"6. Korean (�ѱ���)");
			
	public static final String KOREAN_MENU = 
			("�޴��� �����ϼ���.\n" +
			"1. �÷��̾�\n" +
			"2. ������\n" +
			"3. ����\n" +
			"4. ����\n" +
			"---- ���\n" +
			"5. ���� (English)\n" +
			"6. �ѱ���");
	
	/***** ���� �޼��� *****/
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
						Language.langStr("You have choosed the wrong menu.", "�߸��� �޴��� �����ϼ̽��ϴ�."),
						COMMON_PROGRAM_NAME, 
						JOptionPane.ERROR_MESSAGE);
				
			}
		}
	}
}