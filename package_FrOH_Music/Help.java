package package_FrOH_Music;

import java.awt.*;
import java.awt.event.*;
import package_MyFunction.Language;
import javax.swing.*;

public class Help implements Program {
	private static int currentPage = 1;
	private static final String TITLE = COMMON_PROGRAM_NAME + " " + Language.langStr("Help", "도움말");
	private static JFrame currentOpenFrame = null;

	private static final String[] CONTENTS_ENGLISH = {
		// TODO 1 페이지
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
		// TODO 2 페이지
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
		// TODO 3 페이지
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
		// TODO 4 페이지
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
		// TODO 5 페이지
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
		// TODO 6 페이지
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
		// TODO 7 페이지
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
		// TODO 8 페이지
		("(6) 주석 달기\n" +
		"음악이 길고 커지고 복잡해지면\n" +
		"음악 파일을 설명할 필요가 있습니다.\n" +
		"\n" +
		"그래서 " + COMMON_PROGRAM_NAME + "에 주석을 달게 만들어주는 기능을 추가했습니다\n" +
		"주석 달기는 이와 같이 활용하시는 것이 더 좋습니다.\n" +
		"1. 중간중간에 해당 마디의 수 달기 (예) (**** 77 마디 ****)\n" +
		"2. 읽기 어려운 마디 설명하기\n" +
		"\n" +
		"주석은 별 표(*)를 양쪽에 여러 개를 달아서 강조할 수 있습니다.\n" +
		"\n" +
		"주석을 추가할 때에는 가사 출력과 같은 위치에서\n" +
		"주석을 달 내용을 입력한 다음\n" +
		"주석을 넣을 위치를 선택한 후에\n" +
		"주석 달기를 클릭하시면 됩니다.\n" +
		"\n" +
		"그러면 다음과 같은 것이 추가됩니다.\n" +
		"(주석)\n"),
		// TODO 9 페이지
		("(7) 파일 분할\n" +
		"파일 분할은 2개 이상의 파일로 나누는 것을 말합니다.\n" +
		"2개 이상의 파트가 있는 경우 파일 분할을 하시는 것이 좋습니다.\n" +
		"\n" +
		"에디터에 보시면 파일 메뉴에서 새로 만들기(다음 파일)이라는 메뉴가 있는데\n" +
		"이 메뉴를 클릭하시면 자동으로 다음 파일이 추가됩니다.\n" +
		"그래서 새로 파일이 추가되어 파일이 분할된 것입니다.\n" +
		"\n" +
		"그리고 분할된 파일을 왔다갔다 하고 싶다면\n" +
		"열기(이전 파일)과 (다음 파일)을 이용하시면\n" +
		"다른 분할된 파일로 이동할 수 있습니다.\n" +
		"\n" +
		"잠깐: 새로 만들기는 분할된 파일에서 벗어나 \n" +
		"       \'제목 없음\' 상태로 새로 만들어집니다.\n" +
		"       열기와 열기(이전/다음 파일)의 차이는\n" +
		"       열기같은 경우 다른 분할된 파일을 이동할 수도 있지만\n" +
		"       이름이 완전히 다른 파일을 선택해서 열 수도 있습니다.\n" +
		"       그러나 열기(이전/다음 파일)같은 경우\n" +
		"       분할된 파일을 왔다갔다 할 수 밖에 없습니다.\n" +
		"\n" +
		"       그리고 저장 기능 같은 경우\n" +
		"       분할된거나 안된거나에 상관없이 하나의 파일만 저장되고\n" +
		"       다른 이름으로 저장하기도 하나의 파일만 다른 이름으로 저장할 수 있습니다.\n")
	};

	private static final String[] CONTENTS_KOREAN = {
		// TODO 1 페이지
		(COMMON_PROGRAM_NAME + " version " + VERSION + " 는(은)\n" +
		"음악을 재생/편집할 수 있는 Java 프로그램으로\n" +
		"이 프로그램 안에 플레이어(재생기), 에디터(편집기)가 포함되어 있습니다.\n" +
		"\n" +
		"제작자는 FrOH이며\n" +
		COMMON_PROGRAM_NAME + "에 대해 궁금한 점이 있거나\n" +
		"버그를 발견하셨다면 creationyun@hotmail.com으로\n" +
		"이메일을 보내주시기 바랍니다.\n" +
		"\n" +
		"목차\n" +
		"1. 플레이어 사용법\n" +
		"2. 에디터 사용법\n" +
		"   (1) 하나의 음 만들기\n" +
		"   (2) 화음 만들기\n" +
		"   (3) 빠르기 바꾸기\n" +
		"   (4) 셈여림 추가하기\n" +
		"   (5) 가사 넣기\n" +
		"   (6) 주석 달기\n" +
		"   (7) 파일 분할\n"),
		// TODO 2 페이지
		("1. 플레이어 사용법\n" +
		"\n" +
		"플레이어는 음악을 재생할 수 있게 만들어주는 프로그램입니다.\n" +
		"아직 음악을 전혀 모르거나 익숙하지 않은 분들은\n" +
		"에디터 사용법을 모르셔도 됩니다.\n" +
		"다른 사람이 만든 FRM 음악을 재생하거나\n" +
		"프로그램과 같이 첨부된 샘플 파일을 재생하시면 됩니다.\n" +
		"\n" +
		"플레이어 사용법이 워낙 간단해서\n" +
		"누구나 쉽게 사용할 수 있을 것입니다.\n" +
		"이 프로그램은 Java로 만들어져 있고\n" +
		"자주 보지 못한 윈도우 스타일로 인해\n" +
		"익숙하지 않아서 많이 불편해 하실 것입니다.\n" +
		"잘 따라하시면 이 내용은 유용한 정보가 될 수 있습니다.\n" +
		"\n" +
		"1. 입력 창에서 1번 메뉴(플레이어) 선택\n" +
		"2. FRM 파일이 있는 디렉터리로 가서 그 파일을 선택\n" +
		"이렇게 하면 재생이 시작됩니다.\n" +
		"프로그램과 같이 첨부된 FRM 파일을 선택하셔서 재생하시면 됩니다.\n"),
		// TODO 3 페이지
		("2. 에디터 사용법\n" +
		"\n" +
		"에디터는 음악에 대해 잘 알고 있는 분들만 사용하시기 바랍니다.\n" +
		"그래도 사용하고 싶다면 음악을 공부하세요.\n" +
		"그렇지 않으면 이 내용은 이해가 가지 않을 수 있습니다.\n" +
		"에디터는 FRM 파일을 하나만 혹은 분할하여 편집할 수 있습니다.\n" +
		"하나의 음을 만들기에서 파일 분할까지 총 7개의 세부 목차로 이루어져 있습니다.\n" +
		"2번 목차부터는 에디터를 연 후 정보 - 도움말을 클릭해서 보시는 게 편합니다.\n" +
		"\n" +
		"(1) 하나의 음 만들기\n" +
		"하나의 음을 만드는 법을 배우면 응용하여 화음을 만들 수 있습니다.\n" +
		"\n" +
		"1. 에디터에서 옥타브, C(도), 제자리표가 있는 곳을 찾습니다.\n" +
		"2. 옥타브를 입력합니다. (가온은 5옥타브)\n" +
		"3. 음을 선택합니다. (옆에 계이름도 같이 있습니다.)\n" +
		"4. 만약에 당신의 악보에 있는 임시표 혹은 조표가 그 음에 영향을 받는다면\n" +
		"   제자리표 부분을 바꾸시면 됩니다.\n" +
		"5. 음표 버튼을 클릭합니다.\n" +
		"6. 박자 메뉴에서 박자를 입력합니다.\n" +
		"   1박자는 1, 2박자는 2, 반박자는 0.5, 반의 반박은 0.25입니다.\n" +
		"7. 박자 메뉴에서 추가 버튼을 클릭합니다.\n" +
		"8. 음을 추가했을 때와 같은 음을 설정하고 쉼표 버튼을 클릭합니다.\n" +
		"명령어를 추가하게 되면 \"n[옥타브][음] e[박자] s[옥타브][음]\" 형식으로 되어 있을 것입니다.\n" +
		"n은 음표 명령어, e는 박자 명령어, s는 쉼표 명령어입니다.\n" +
		"\n" +
		"추가로 쉼표 명령어 뒤에 박자가 있다면 쉼표가 됩니다.\n" +
		"음표와 박자 명령어 다음에 쉼표가 없는데 쉼표 명령어를 넣는 이유는\n" +
		"계속 음이 이어지지 않기 위해서입니다.\n" +
		"이어지게 된다면 불협화음이 될 수 있지만,\n" +
		"페달 밟는 효과를 내려면 쉼표 명령어를 한꺼번에 페달 떼는 곳에 모으면 됩니다.\n"),
		// TODO 4 페이지
		("(2) 화음 만들기\n" +
		"\n" +
		"화음 만들기는 하나의 음 만들기를 배우시면 쉽습니다.\n" +
		"\n" +
		"1. 음표 명령어를 2개 이상 추가합니다. (2개 이상의 음이 모이면 화음이 됨.)\n" +
		"2. 화음의 박자 명령어를 추가합니다.\n" +
		"3. 쉼표 명령어를 2개 이상 추가합니다.\n" +
		"** 이것도 마찬가지로 페달 밟는 효과를 얻고 싶다면\n" +
		"   페달을 떼는 곳에서 한꺼번에 중지해도 됩니다.\n" +
		"\n" +
		"위 설명에서 음표나 쉼표 명령어를 2개 이상 추가하라는 말의 의미는\n" +
		"화음이 도, 미, 솔 4박자를 연주한다 하면\n" +
		"음표 명령어로 도, 미, 솔 추가하고 박자 명령어로 4박자를 추가하고\n" +
		"쉼표 명령어로 도, 미, 솔 추가하라는 의미입니다.\n" +
		"5옥타브의 화음을 연주한다면 다음과 같이 됩니다.\n" +
		"n5C n5E n5G e4 s5C s5E s5G\n"),
		// TODO 5 페이지
		("(3) 빠르기 추가하기\n" +
		"빠르기는 보통 템포(Tempo)라고 합니다.\n" +
		"템포는 1분에 연주되는 박자 수를 말하며\n" +
		"같은 말로 BPM(Beats Per Minute)이라고 합니다.\n" +
		"\n" +
		"템포는 원하는 위치에 삽입해서 조절할 수 있습니다.\n" +
		"에디터에서 템포를 추가하는 기능이 있습니다.\n" +
		"그 기능에서 템포 수를 입력하고 추가 버튼을 누르면\n" +
		"템포를 추가할 수 있습니다.\n" +
		"기본적인 템포는 120입니다.\n" +
		"(1분에 120박자, 1박자에 0.5초)\n" +
		"\n" +
		"에디터에 추가하게 되면 이렇게 됩니다.\n" +
		"t[템포]"),
		// TODO 6 페이지
		("(4) 셈여림 추가하기\n" +
		"셈여림은 음의 세기를 말하며\n" +
		"가장 약한 세기에서 강한 세기 순서로 나열해 보면\n" +
		"피아니시모, 피아노, 메조 피아노\n" +
		"메조 포르테, 포르테, 포르티시모\n" +
		"가 됩니다.\n" +
		"악보에 피아노를 p로 표시하고\n" +
		"메조를 m으로 표시하고\n" +
		"포르테를 f로 표시합니다.\n" +
		"~시모가 붙는 세기는 1개가 더 붙는 세기입니다.\n" +
		COMMON_PROGRAM_NAME + " " + VERSION + "는(은) 악보에 표기하는 그대로를 따랐습니다.\n" +
		"\n" +
		"에디터에서 셈여림 추가하는 곳이 있는데\n" +
		"pp는 피아니시모 - 매우 여리게\n" +
		"p는 피아노 - 여리게\n" +
		"mp는 메조 피아노 - 조금 여리게\n" +
		"mf는 메조 포르테 - 조금 세게\n" +
		"f는 포르테 - 세게\n" +
		"ff는 포르티시모 - 매우 세게\n" +
		"라는 뜻을 가지고 있습니다.\n" +
		"에디터에 추가하게 되면\n" +
		"d[셈여림]이 추가됩니다.\n"),
		// TODO 7 페이지
		("(5) 가사 넣기\n" +
		"가사는 음에 맞추어 말을 넣는 것을 말합니다.\n" +
		"음을 재생하는 동시에 가사가 출력되도록 하려면\n" +
		"그 음에 들어가는 가사 한 글자나 단어를\n" +
		"음표 명령어 앞에 넣을 수도 있고\n" +
		"뒤에 넣을 수도 있습니다.\n" +
		"\n" +
		"밑에 보시면 글자를 입력하는 공간이 있고\n" +
		"가사 출력과 주석 달기 버튼이 있습니다.\n" +
		"가사를 넣으려면 가사 출력 버튼을 누르면 되겠죠.\n" +
		"주석 달기 버튼은 다음 페이지에 설명되어 있습니다.\n" +
		"\n" +
		"음표 명령어 앞에 넣든지 뒤에 넣든지 간에\n" +
		"가사 한 글자나 단어를 넣을 위치를 클릭한 후\n" +
		"가사 한 글자나 단어를 입력 후\n" +
		"가사 출력 버튼을 누르시면 됩니다.\n" +
		"\n" +
		"추가하게 되면\n" +
		"o<내용>이 추가됩니다.\n" +
		"TIP: 가사 출력은 가사만 출력하는 곳에 쓸 수도 있지만\n" +
		"     저작권 표시나 만든 날짜, 곡 제목 등을 쓰는 데 도움을 줄 수 있습니다.\n" +
		"플레이어를 실행하면 가사 창이 뜨는데\n" +
		"가사 창에 가사 출력 명령어를 출력해 주는 역할을 합니다.\n"),
		// TODO 8 페이지
		("(6) 주석 달기\n" +
		"음악이 길고 커지고 복잡해지면\n" +
		"음악 파일을 설명할 필요가 있습니다.\n" +
		"\n" +
		"그래서 " + COMMON_PROGRAM_NAME + "에 주석을 달게 만들어주는 기능을 추가했습니다\n" +
		"주석 달기는 이와 같이 활용하시는 것이 더 좋습니다.\n" +
		"1. 중간중간에 해당 마디의 수 달기 (예) (**** 77 마디 ****)\n" +
		"2. 읽기 어려운 마디 설명하기\n" +
		"\n" +
		"주석은 별 표(*)를 양쪽에 여러 개를 달아서 강조할 수 있습니다.\n" +
		"\n" +
		"주석을 추가할 때에는 가사 출력과 같은 위치에서\n" +
		"주석을 달 내용을 입력한 다음\n" +
		"주석을 넣을 위치를 선택한 후에\n" +
		"주석 달기를 클릭하시면 됩니다.\n" +
		"\n" +
		"그러면 다음과 같은 것이 추가됩니다.\n" +
		"(주석)\n"),
		// TODO 9 페이지
		("(7) 파일 분할\n" +
		"파일 분할은 2개 이상의 파일로 나누는 것을 말합니다.\n" +
		"2개 이상의 파트가 있는 경우 파일 분할을 하시는 것이 좋습니다.\n" +
		"\n" +
		"에디터에 보시면 파일 메뉴에서 새로 만들기(다음 파일)이라는 메뉴가 있는데\n" +
		"이 메뉴를 클릭하시면 자동으로 다음 파일이 추가됩니다.\n" +
		"그래서 새로 파일이 추가되어 파일이 분할된 것입니다.\n" +
		"\n" +
		"그리고 분할된 파일을 왔다갔다 하고 싶다면\n" +
		"열기(이전 파일)과 (다음 파일)을 이용하시면\n" +
		"다른 분할된 파일로 이동할 수 있습니다.\n" +
		"\n" +
		"잠깐: 새로 만들기는 분할된 파일에서 벗어나 \n" +
		"       \'제목 없음\' 상태로 새로 만들어집니다.\n" +
		"       열기와 열기(이전/다음 파일)의 차이는\n" +
		"       열기같은 경우 다른 분할된 파일을 이동할 수도 있지만\n" +
		"       이름이 완전히 다른 파일을 선택해서 열 수도 있습니다.\n" +
		"       그러나 열기(이전/다음 파일)같은 경우\n" +
		"       분할된 파일을 왔다갔다 할 수 밖에 없습니다.\n" +
		"\n" +
		"       그리고 저장 기능 같은 경우\n" +
		"       분할된거나 안된거나에 상관없이 하나의 파일만 저장되고\n" +
		"       다른 이름으로 저장하기도 하나의 파일만 다른 이름으로 저장할 수 있습니다.\n")
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
							Language.langStr("This is first page..", "처음 페이지입니다."), TITLE,
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
							Language.langStr("This is last page..", "마지막 페이지입니다."), TITLE,
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
