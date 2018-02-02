package package_FrOH_Music;

import java.awt.*;
import java.awt.event.*;
import package_MyFunction.Language;
import javax.swing.*;

public class Help implements Program {
	private static int currentPage = 1;
	private static final String TITLE = COMMON_PROGRAM_NAME + " " + Language.langStr("Help", "����");
	private static JFrame currentOpenFrame = null;

	private static final String[] CONTENTS_ENGLISH = {
		// TODO 1 ������
		(COMMON_PROGRAM_NAME + " version " + VERSION + " " +
		"is a Java program that lets you to play/edit music.\n" +
		"This program includes a player and an editor.\n" +
		"\n" +
		"The producer is FrOH,\n" +
		"and if you have any questions about " + COMMON_PROGRAM_NAME + "\n" +
		"or have found a bug, \n" +
		"Please contact us at creationyun@hotmail.com.\n" +
		"\n" +
		"Contents\n" +
		"1. Player Usage\n" +
		"2. How to Use The Editor\n" +
		"   (1) Making a Single Note\n" +
		"   (2) Making Chords\n" +
		"   (3) Changing the Tempo\n" +
		"   (4) Adding Dynamics\n" +
		"   (5) Loading Lyrics\n" +
		"   (6) Commenting\n" +
		"   (7) Splitting a File\n"),
		// TODO 2 ������
		("1. Player Usage\n" +
		"\n" +
		"Player is a program that makes music playable.\n" +
		"If you do not know the music yet or are not familiar with it, \n" +
		"You do not need to know how to use the editor.\n" +
		"You can play FRM music created by someone else or\n" +
		"play the attached sample file with the program.\n" +
		"\n" +
		"Player usage is so simple that anyone can use it easily.\n" +
		"This program is written in Java, and\n" +
		"You will be uncomfortable because you are not familiar\n" +
		"with the window style you have not seen so often.\n" +
		"If you follow along well, this can be useful information.\n" +
		"\n" +
		"1. Select menu 1 (player) in the input window\n" +
		"2. Go to the directory where the FRM file is located and select the file\n" +
		"This will start playback.\n" +
		"Just select the attached FRM file and play it.\n"),
		// TODO 3 ������
		("2. How to Use The Editor\n" +
		"\n" +
		"The editor is only for those who are familiar with music.\n" +
		"If you still want to use it, please study music.\n" +
		"Otherwise, this content may not be understandable.\n" +
		"The editor can edit FRM files in one or split.\n" +
		"It consists of a total of 7 details, from creating a note to splitting a file.\n" +
		"From the 2nd table of contents, it is easy to open the editor and to see it by clicking information - help.\n" +
		"\n" +
		"(1) Making a Single Note\n" +
		"When you learn how to make a single note, you can apply it to make a chord.\n" +
		"\n" +
		"1. In the editor, find where octave, C(do), and natural are.\n" +
		"2. Put the number of octave. (number of middle octave is 5)\n" +
		"3. Select a note. (There is also the tonic sol-fa on the side)\n" +
		"4. If accidental marks or key signatures in your sheet music are affected by the note, \n" +
		"   just change the 'natural' part.\n" +
		"5. Click the note button.\n" +
		"6. In the time menu, input number of beat.\n" +
		"   1 beat is 1, 2 beats are 2, a half beat is 0.5 and a half of a half beat is 0.25.\n" +
		"7. On the time menu, click the add button.\n" +
		"8. Set the same note as when you added the note, and click the rest button.\n" +
		"When you add the commands, it will be the format \"n[octave][sound] e[beat] s[octave][sound]\".\n" +
		"n is a note command, e is a beat(time) command and s is a rest command.\n" +
		"\n" +
		"In addition, if there is a beat command after the rest command, it will be a rest mark.\n" +
		"The reason to put a rest command without a rest mark after the note and time commands is\n" +
		"to prevent the continuing sound.\n" +
		"If it continues, it may be a dissonance,\n" +
		"If you want to pedal, just put the rest commands at the same time.\n"),
		// TODO 4 ������
		("(2) Making Chords\n" +
		"\n" +
		"Making a chord is easy when you learn to make one note.\n" +
		"\n" +
		"1. Add two or more note commands. (If more than two notes are combined, it becomes a chord.)\n" +
		"2. Add a beat command for chords.\n" +
		"3. Add two or more rest commands.\n" +
		"** Likewise, if you want to get the pedaling effect,\n" +
		"   you can stop at the same time you release the pedal.\n" +
		"\n" +
		"In the above description, adding two or more notes or rest commands means\n" +
		"If you want to play the chord, do, mi, sol, and 4 beats,\n" +
		"you can add note commands, do, mi, sol, a beat command with 4 beats,\n" +
		"and rest commands, do, mi, sol.\n" +
		"The command to play a 5-octave chord is:\n" +
		"n5C n5E n5G e4 s5C s5E s5G\n"),
		// TODO 5 ������
		("(3) Changing the Tempo\n" +
		"The speed of the music is usually called 'the tempo.'\n" +
		"Tempo refers to the number of beats played per minute, and\n" +
		"the same word is called BPM (Beats Per Minute).\n" +
		"\n" +
		"The tempo can be adjusted by inserting it in the desired position.\n" +
		"You can add the tempo in the editor\n" +
		"by entering the number of tempo in the function and clicking the add button\n" +
		"The default tempo is 120.\n" +
		"(120 beats per minute, 0.5 seconds per beat)\n" +
		"\n" +
		"This is what happens when you add it to the editor.\n" +
		"t[tempo]"),
		// TODO 6 ������
		("(4) Adding Dynamics\n" +
		"Dynamics (Dementia) refers to the intensity of the sound.\n" +
		"From the weakest to the strongest,\n" +
		"Pianissimo, Piano, Meso Piano\n" +
		"Meso Forte, Forte, Fortissimo.\n" +
		"The Piano is usually marked on the sheet music by p,\n" +
		"Meso by m,\n" +
		"Forte by f.\n" +
		"The intensity of ~issimo is one step higher than that of none.\n" +
		COMMON_PROGRAM_NAME + " " + VERSION + " followed the same notation on the sheet music.\n" +
		"\n" +
		"There is a position to add dynamics in the editor.\n" +
		"pp is pianissimo - play very weakly\n" +
		"p is piano - play weakly\n" +
		"mp is meso piano - play little weakly\n" +
		"mf is meso forte - play little strongly\n" +
		"f is forte - play strongly\n" +
		"ff is fortissimo - play very strongly\n" +
		"The left parts have meaning of the right parts.\n" +
		"When you add it to the editor,\n" +
		"d[dynamic] is added.\n"),
		// TODO 7 ������
		("(5) Loading Lyrics\n" +
		"Lyrics refers to putting words in line with notes.\n" +
		"To make the lyrics appear at the same time you play the notes,\n" +
		"you can put the words in the note before or after the note command.\n" +
		"\n" +
		"If you see below, there is space to type letters, and\n" +
		"there is a Lyrics output and a commenting button.\n" +
		"If you want to insert the lyrics, press the Lyrics output button.\n" +
		"The commenting button is described on the next page.\n" +
		"\n" +
		"Whether placed before or after the note command,\n" +
		"you can click on the place where you want to insert the lyrics or words,\n" +
		"then enter the lyrics or words,\n" +
		"and press the lyrics button.\n" +
		"\n" +
		"If you add the lyrics command,\n" +
		"then o<content> is added.\n" +
		"TIP: Lyrics output can be used to output only lyrics,\n" +
		"     but it can help you write copyright marks, making dates, song titles, and more.\n" +
		"When you run the player, a lyric window pops up, and\n" +
		"displays the lyrics output in the lyrics window.\n"),
		// TODO 8 ������
		("(6) �ּ� �ޱ�\n" +
		"������ ��� Ŀ���� ����������\n" +
		"���� ������ ������ �ʿ䰡 �ֽ��ϴ�.\n" +
		"\n" +
		"�׷��� " + COMMON_PROGRAM_NAME + "�� �ּ��� �ް� ������ִ� ����� �߰��߽��ϴ�\n" +
		"�ּ� �ޱ�� �̿� ���� Ȱ���Ͻô� ���� �� �����ϴ�.\n" +
		"1. �߰��߰��� �ش� ������ �� �ޱ� (��) (**** 77 ���� ****)\n" +
		"2. �б� ����� ���� �����ϱ�\n" +
		"\n" +
		"�ּ��� �� ǥ(*)�� ���ʿ� ���� ���� �޾Ƽ� ������ �� �ֽ��ϴ�.\n" +
		"\n" +
		"�ּ��� �߰��� ������ ���� ��°� ���� ��ġ����\n" +
		"�ּ��� �� ������ �Է��� ����\n" +
		"�ּ��� ���� ��ġ�� ������ �Ŀ�\n" +
		"�ּ� �ޱ⸦ Ŭ���Ͻø� �˴ϴ�.\n" +
		"\n" +
		"�׷��� ������ ���� ���� �߰��˴ϴ�.\n" +
		"(�ּ�)\n"),
		// TODO 9 ������
		("(7) ���� ����\n" +
		"���� ������ 2�� �̻��� ���Ϸ� ������ ���� ���մϴ�.\n" +
		"2�� �̻��� ��Ʈ�� �ִ� ��� ���� ������ �Ͻô� ���� �����ϴ�.\n" +
		"\n" +
		"�����Ϳ� ���ø� ���� �޴����� ���� �����(���� ����)�̶�� �޴��� �ִµ�\n" +
		"�� �޴��� Ŭ���Ͻø� �ڵ����� ���� ������ �߰��˴ϴ�.\n" +
		"�׷��� ���� ������ �߰��Ǿ� ������ ���ҵ� ���Դϴ�.\n" +
		"\n" +
		"�׸��� ���ҵ� ������ �Դٰ��� �ϰ� �ʹٸ�\n" +
		"����(���� ����)�� (���� ����)�� �̿��Ͻø�\n" +
		"�ٸ� ���ҵ� ���Ϸ� �̵��� �� �ֽ��ϴ�.\n" +
		"\n" +
		"���: ���� ������ ���ҵ� ���Ͽ��� ��� \n" +
		"       \'���� ����\' ���·� ���� ��������ϴ�.\n" +
		"       ����� ����(����/���� ����)�� ���̴�\n" +
		"       ���ⰰ�� ��� �ٸ� ���ҵ� ������ �̵��� ���� ������\n" +
		"       �̸��� ������ �ٸ� ������ �����ؼ� �� ���� �ֽ��ϴ�.\n" +
		"       �׷��� ����(����/���� ����)���� ���\n" +
		"       ���ҵ� ������ �Դٰ��� �� �� �ۿ� �����ϴ�.\n" +
		"\n" +
		"       �׸��� ���� ��� ���� ���\n" +
		"       ���ҵȰų� �ȵȰų��� ������� �ϳ��� ���ϸ� ����ǰ�\n" +
		"       �ٸ� �̸����� �����ϱ⵵ �ϳ��� ���ϸ� �ٸ� �̸����� ������ �� �ֽ��ϴ�.\n")
	};

	private static final String[] CONTENTS_KOREAN = {
		// TODO 1 ������
		(COMMON_PROGRAM_NAME + " version " + VERSION + " ��(��)\n" +
		"������ ���/������ �� �ִ� Java ���α׷�����\n" +
		"�� ���α׷� �ȿ� �÷��̾�(�����), ������(������)�� ���ԵǾ� �ֽ��ϴ�.\n" +
		"\n" +
		"�����ڴ� FrOH�̸�\n" +
		COMMON_PROGRAM_NAME + "�� ���� �ñ��� ���� �ְų�\n" +
		"���׸� �߰��ϼ̴ٸ� creationyun@hotmail.com����\n" +
		"�̸����� �����ֽñ� �ٶ��ϴ�.\n" +
		"\n" +
		"����\n" +
		"1. �÷��̾� ����\n" +
		"2. ������ ����\n" +
		"   (1) �ϳ��� �� �����\n" +
		"   (2) ȭ�� �����\n" +
		"   (3) ������ �ٲٱ�\n" +
		"   (4) ������ �߰��ϱ�\n" +
		"   (5) ���� �ֱ�\n" +
		"   (6) �ּ� �ޱ�\n" +
		"   (7) ���� ����\n"),
		// TODO 2 ������
		("1. �÷��̾� ����\n" +
		"\n" +
		"�÷��̾�� ������ ����� �� �ְ� ������ִ� ���α׷��Դϴ�.\n" +
		"���� ������ ���� �𸣰ų� �ͼ����� ���� �е���\n" +
		"������ ������ �𸣼ŵ� �˴ϴ�.\n" +
		"�ٸ� ����� ���� FRM ������ ����ϰų�\n" +
		"���α׷��� ���� ÷�ε� ���� ������ ����Ͻø� �˴ϴ�.\n" +
		"\n" +
		"�÷��̾� ������ ���� �����ؼ�\n" +
		"������ ���� ����� �� ���� ���Դϴ�.\n" +
		"�� ���α׷��� Java�� ������� �ְ�\n" +
		"���� ���� ���� ������ ��Ÿ�Ϸ� ����\n" +
		"�ͼ����� �ʾƼ� ���� ������ �Ͻ� ���Դϴ�.\n" +
		"�� �����Ͻø� �� ������ ������ ������ �� �� �ֽ��ϴ�.\n" +
		"\n" +
		"1. �Է� â���� 1�� �޴�(�÷��̾�) ����\n" +
		"2. FRM ������ �ִ� ���͸��� ���� �� ������ ����\n" +
		"�̷��� �ϸ� ����� ���۵˴ϴ�.\n" +
		"���α׷��� ���� ÷�ε� FRM ������ �����ϼż� ����Ͻø� �˴ϴ�.\n"),
		// TODO 3 ������
		("2. ������ ����\n" +
		"\n" +
		"�����ʹ� ���ǿ� ���� �� �˰� �ִ� �е鸸 ����Ͻñ� �ٶ��ϴ�.\n" +
		"�׷��� ����ϰ� �ʹٸ� ������ �����ϼ���.\n" +
		"�׷��� ������ �� ������ ���ذ� ���� ���� �� �ֽ��ϴ�.\n" +
		"�����ʹ� FRM ������ �ϳ��� Ȥ�� �����Ͽ� ������ �� �ֽ��ϴ�.\n" +
		"�ϳ��� ���� ����⿡�� ���� ���ұ��� �� 7���� ���� ������ �̷���� �ֽ��ϴ�.\n" +
		"2�� �������ʹ� �����͸� �� �� ���� - ������ Ŭ���ؼ� ���ô� �� ���մϴ�.\n" +
		"\n" +
		"(1) �ϳ��� �� �����\n" +
		"�ϳ��� ���� ����� ���� ���� �����Ͽ� ȭ���� ���� �� �ֽ��ϴ�.\n" +
		"\n" +
		"1. �����Ϳ��� ��Ÿ��, C(��), ���ڸ�ǥ�� �ִ� ���� ã���ϴ�.\n" +
		"2. ��Ÿ�긦 �Է��մϴ�. (������ 5��Ÿ��)\n" +
		"3. ���� �����մϴ�. (���� ���̸��� ���� �ֽ��ϴ�.)\n" +
		"4. ���࿡ ����� �Ǻ��� �ִ� �ӽ�ǥ Ȥ�� ��ǥ�� �� ���� ������ �޴´ٸ�\n" +
		"   ���ڸ�ǥ �κ��� �ٲٽø� �˴ϴ�.\n" +
		"5. ��ǥ ��ư�� Ŭ���մϴ�.\n" +
		"6. ���� �޴����� ���ڸ� �Է��մϴ�.\n" +
		"   1���ڴ� 1, 2���ڴ� 2, �ݹ��ڴ� 0.5, ���� �ݹ��� 0.25�Դϴ�.\n" +
		"7. ���� �޴����� �߰� ��ư�� Ŭ���մϴ�.\n" +
		"8. ���� �߰����� ���� ���� ���� �����ϰ� ��ǥ ��ư�� Ŭ���մϴ�.\n" +
		"��ɾ �߰��ϰ� �Ǹ� \"n[��Ÿ��][��] e[����] s[��Ÿ��][��]\" �������� �Ǿ� ���� ���Դϴ�.\n" +
		"n�� ��ǥ ��ɾ�, e�� ���� ��ɾ�, s�� ��ǥ ��ɾ��Դϴ�.\n" +
		"\n" +
		"�߰��� ��ǥ ��ɾ� �ڿ� ���ڰ� �ִٸ� ��ǥ�� �˴ϴ�.\n" +
		"��ǥ�� ���� ��ɾ� ������ ��ǥ�� ���µ� ��ǥ ��ɾ �ִ� ������\n" +
		"��� ���� �̾����� �ʱ� ���ؼ��Դϴ�.\n" +
		"�̾����� �ȴٸ� ����ȭ���� �� �� ������,\n" +
		"��� ��� ȿ���� ������ ��ǥ ��ɾ �Ѳ����� ��� ���� ���� ������ �˴ϴ�.\n"),
		// TODO 4 ������
		("(2) ȭ�� �����\n" +
		"\n" +
		"ȭ�� ������ �ϳ��� �� ����⸦ ���ø� �����ϴ�.\n" +
		"\n" +
		"1. ��ǥ ��ɾ 2�� �̻� �߰��մϴ�. (2�� �̻��� ���� ���̸� ȭ���� ��.)\n" +
		"2. ȭ���� ���� ��ɾ �߰��մϴ�.\n" +
		"3. ��ǥ ��ɾ 2�� �̻� �߰��մϴ�.\n" +
		"** �̰͵� ���������� ��� ��� ȿ���� ��� �ʹٸ�\n" +
		"   ����� ���� ������ �Ѳ����� �����ص� �˴ϴ�.\n" +
		"\n" +
		"�� ������ ��ǥ�� ��ǥ ��ɾ 2�� �̻� �߰��϶�� ���� �ǹ̴�\n" +
		"ȭ���� ��, ��, �� 4���ڸ� �����Ѵ� �ϸ�\n" +
		"��ǥ ��ɾ�� ��, ��, �� �߰��ϰ� ���� ��ɾ�� 4���ڸ� �߰��ϰ�\n" +
		"��ǥ ��ɾ�� ��, ��, �� �߰��϶�� �ǹ��Դϴ�.\n" +
		"5��Ÿ���� ȭ���� �����Ѵٸ� ������ ���� �˴ϴ�.\n" +
		"n5C n5E n5G e4 s5C s5E s5G\n"),
		// TODO 5 ������
		("(3) ������ �߰��ϱ�\n" +
		"������� ���� ����(Tempo)��� �մϴ�.\n" +
		"������ 1�п� ���ֵǴ� ���� ���� ���ϸ�\n" +
		"���� ���� BPM(Beats Per Minute)�̶�� �մϴ�.\n" +
		"\n" +
		"������ ���ϴ� ��ġ�� �����ؼ� ������ �� �ֽ��ϴ�.\n" +
		"�����Ϳ��� ������ �߰��ϴ� ����� �ֽ��ϴ�.\n" +
		"�� ��ɿ��� ���� ���� �Է��ϰ� �߰� ��ư�� ������\n" +
		"������ �߰��� �� �ֽ��ϴ�.\n" +
		"�⺻���� ������ 120�Դϴ�.\n" +
		"(1�п� 120����, 1���ڿ� 0.5��)\n" +
		"\n" +
		"�����Ϳ� �߰��ϰ� �Ǹ� �̷��� �˴ϴ�.\n" +
		"t[����]"),
		// TODO 6 ������
		("(4) ������ �߰��ϱ�\n" +
		"�������� ���� ���⸦ ���ϸ�\n" +
		"���� ���� ���⿡�� ���� ���� ������ ������ ����\n" +
		"�ǾƴϽø�, �ǾƳ�, ���� �ǾƳ�\n" +
		"���� ������, ������, ����Ƽ�ø�\n" +
		"�� �˴ϴ�.\n" +
		"�Ǻ��� �ǾƳ븦 p�� ǥ���ϰ�\n" +
		"������ m���� ǥ���ϰ�\n" +
		"�����׸� f�� ǥ���մϴ�.\n" +
		"~�ø� �ٴ� ����� 1���� �� �ٴ� �����Դϴ�.\n" +
		COMMON_PROGRAM_NAME + " " + VERSION + "��(��) �Ǻ��� ǥ���ϴ� �״�θ� �������ϴ�.\n" +
		"\n" +
		"�����Ϳ��� ������ �߰��ϴ� ���� �ִµ�\n" +
		"pp�� �ǾƴϽø� - �ſ� ������\n" +
		"p�� �ǾƳ� - ������\n" +
		"mp�� ���� �ǾƳ� - ���� ������\n" +
		"mf�� ���� ������ - ���� ����\n" +
		"f�� ������ - ����\n" +
		"ff�� ����Ƽ�ø� - �ſ� ����\n" +
		"��� ���� ������ �ֽ��ϴ�.\n" +
		"�����Ϳ� �߰��ϰ� �Ǹ�\n" +
		"d[������]�� �߰��˴ϴ�.\n"),
		// TODO 7 ������
		("(5) ���� �ֱ�\n" +
		"����� ���� ���߾� ���� �ִ� ���� ���մϴ�.\n" +
		"���� ����ϴ� ���ÿ� ���簡 ��µǵ��� �Ϸ���\n" +
		"�� ���� ���� ���� �� ���ڳ� �ܾ\n" +
		"��ǥ ��ɾ� �տ� ���� ���� �ְ�\n" +
		"�ڿ� ���� ���� �ֽ��ϴ�.\n" +
		"\n" +
		"�ؿ� ���ø� ���ڸ� �Է��ϴ� ������ �ְ�\n" +
		"���� ��°� �ּ� �ޱ� ��ư�� �ֽ��ϴ�.\n" +
		"���縦 �������� ���� ��� ��ư�� ������ �ǰ���.\n" +
		"�ּ� �ޱ� ��ư�� ���� �������� ����Ǿ� �ֽ��ϴ�.\n" +
		"\n" +
		"��ǥ ��ɾ� �տ� �ֵ��� �ڿ� �ֵ��� ����\n" +
		"���� �� ���ڳ� �ܾ ���� ��ġ�� Ŭ���� ��\n" +
		"���� �� ���ڳ� �ܾ �Է� ��\n" +
		"���� ��� ��ư�� �����ø� �˴ϴ�.\n" +
		"\n" +
		"�߰��ϰ� �Ǹ�\n" +
		"o<����>�� �߰��˴ϴ�.\n" +
		"TIP: ���� ����� ���縸 ����ϴ� ���� �� ���� ������\n" +
		"     ���۱� ǥ�ó� ���� ��¥, �� ���� ���� ���� �� ������ �� �� �ֽ��ϴ�.\n" +
		"�÷��̾ �����ϸ� ���� â�� �ߴµ�\n" +
		"���� â�� ���� ��� ��ɾ ����� �ִ� ������ �մϴ�.\n"),
		// TODO 8 ������
		("(6) �ּ� �ޱ�\n" +
		"������ ��� Ŀ���� ����������\n" +
		"���� ������ ������ �ʿ䰡 �ֽ��ϴ�.\n" +
		"\n" +
		"�׷��� " + COMMON_PROGRAM_NAME + "�� �ּ��� �ް� ������ִ� ����� �߰��߽��ϴ�\n" +
		"�ּ� �ޱ�� �̿� ���� Ȱ���Ͻô� ���� �� �����ϴ�.\n" +
		"1. �߰��߰��� �ش� ������ �� �ޱ� (��) (**** 77 ���� ****)\n" +
		"2. �б� ����� ���� �����ϱ�\n" +
		"\n" +
		"�ּ��� �� ǥ(*)�� ���ʿ� ���� ���� �޾Ƽ� ������ �� �ֽ��ϴ�.\n" +
		"\n" +
		"�ּ��� �߰��� ������ ���� ��°� ���� ��ġ����\n" +
		"�ּ��� �� ������ �Է��� ����\n" +
		"�ּ��� ���� ��ġ�� ������ �Ŀ�\n" +
		"�ּ� �ޱ⸦ Ŭ���Ͻø� �˴ϴ�.\n" +
		"\n" +
		"�׷��� ������ ���� ���� �߰��˴ϴ�.\n" +
		"(�ּ�)\n"),
		// TODO 9 ������
		("(7) ���� ����\n" +
		"���� ������ 2�� �̻��� ���Ϸ� ������ ���� ���մϴ�.\n" +
		"2�� �̻��� ��Ʈ�� �ִ� ��� ���� ������ �Ͻô� ���� �����ϴ�.\n" +
		"\n" +
		"�����Ϳ� ���ø� ���� �޴����� ���� �����(���� ����)�̶�� �޴��� �ִµ�\n" +
		"�� �޴��� Ŭ���Ͻø� �ڵ����� ���� ������ �߰��˴ϴ�.\n" +
		"�׷��� ���� ������ �߰��Ǿ� ������ ���ҵ� ���Դϴ�.\n" +
		"\n" +
		"�׸��� ���ҵ� ������ �Դٰ��� �ϰ� �ʹٸ�\n" +
		"����(���� ����)�� (���� ����)�� �̿��Ͻø�\n" +
		"�ٸ� ���ҵ� ���Ϸ� �̵��� �� �ֽ��ϴ�.\n" +
		"\n" +
		"���: ���� ������ ���ҵ� ���Ͽ��� ��� \n" +
		"       \'���� ����\' ���·� ���� ��������ϴ�.\n" +
		"       ����� ����(����/���� ����)�� ���̴�\n" +
		"       ���ⰰ�� ��� �ٸ� ���ҵ� ������ �̵��� ���� ������\n" +
		"       �̸��� ������ �ٸ� ������ �����ؼ� �� ���� �ֽ��ϴ�.\n" +
		"       �׷��� ����(����/���� ����)���� ���\n" +
		"       ���ҵ� ������ �Դٰ��� �� �� �ۿ� �����ϴ�.\n" +
		"\n" +
		"       �׸��� ���� ��� ���� ���\n" +
		"       ���ҵȰų� �ȵȰų��� ������� �ϳ��� ���ϸ� ����ǰ�\n" +
		"       �ٸ� �̸����� �����ϱ⵵ �ϳ��� ���ϸ� �ٸ� �̸����� ������ �� �ֽ��ϴ�.\n")
	};
	private static void refresh(JTextArea area, JLabel showPage) {
		area.setText(Language.langStr(CONTENTS_ENGLISH[currentPage-1], CONTENTS_KOREAN[currentPage-1]));
		area.setCaretPosition(0);
		showPage.setText(currentPage + " / " + CONTENTS_ENGLISH.length + " Page");
	}

	public static void openHelp() {
		final JFrame frame = new JFrame(TITLE );
		frame.setSize(500, 500);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());

		final JTextArea helpArea = new JTextArea();
		JScrollPane helpAreaScroll = new JScrollPane(helpArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		helpArea.setEditable(false);
		frame.add(helpAreaScroll, "Center");

		JPanel functionBtns = new JPanel();
		functionBtns.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		final JLabel pageLabel = new JLabel();

		JButton prevBtn = new JButton("<<");
		prevBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (currentPage > 1) {
					currentPage--;
					refresh(helpArea, pageLabel);
				} else {
					JOptionPane.showMessageDialog(frame,
							Language.langStr("This is first page..", "ó�� �������Դϴ�."), TITLE,
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		JButton nextBtn = new JButton(">>");
		nextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (currentPage < CONTENTS_ENGLISH.length) {
					currentPage++;
					refresh(helpArea, pageLabel);
				} else {
					JOptionPane.showMessageDialog(frame,
							Language.langStr("This is last page..", "������ �������Դϴ�."), TITLE,
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		functionBtns.add(prevBtn);
		functionBtns.add(pageLabel);
		functionBtns.add(nextBtn);
		frame.add(functionBtns, "South");

		frame.addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent e) {  }
			public void windowClosing(WindowEvent e) {
				frame.setVisible(false);
				frame.dispose();
			}
			public void windowClosed(WindowEvent e) {  }
			public void windowIconified(WindowEvent e) {  }
			public void windowDeiconified(WindowEvent e) {  }
			public void windowActivated(WindowEvent e) {  }
			public void windowDeactivated(WindowEvent e) {  }
		});

		frame.setVisible(true);
		refresh(helpArea, pageLabel);

		currentOpenFrame = frame;
	}

	public static void openWait() {
		if (currentOpenFrame != null) {
			while (true) {
				try {
					if (currentOpenFrame.isVisible()) {
						Thread.sleep(500);
					} else {
						break;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
					break;
				}
			}
		}
	}
}
