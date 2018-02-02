package package_FrOH_Music;

import java.io.*;

import package_Music.*;
import package_MyFunction.*;

public class Player implements Program {
	/* TODO: Player의 루틴들 */
	
	/* 빈 공간 테이블 */
	private static final char[] emptyCharTable = {
		' ', '\n', '\t'
	};
	
	/* CommandException을 던지는 루틴 */
	private static void throwCmdXcp(int errCode, int pos) throws CommandException {
		throw new CommandException(errCode, pos);
	}
	
	/* pos가 str을 넘으면 true, 아니면 false를 반환하는 루틴 */
	private static boolean isOverPos(String str, int pos) {
		return str.length() <= pos;
	}
	
	/* ch가 spaceTable을 통해 빈 문자인지 아닌지 검출 */
	private static boolean isSpace(char ch) {
		boolean result = false;
		for (int spacesIdx = 0; spacesIdx < emptyCharTable.length; spacesIdx++) {
			if (ch == emptyCharTable[spacesIdx]) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	/* 해당 위치가 빈 문자이면 true, 아니면 false를 반환하는 루틴 */
	private static boolean isPosSpace(String str, int pos) {
		return isSpace(str.charAt(pos));
	}
	
	
	public static Music[] getMusic() {
		Music[] music = null;
		MyFileInput[] musicInput = null;
		
		/***** TODO 파일 다이얼로그 부분 *****/
		File file = MyFileDialog.showFileDialog(null, EXT, COMMON_PROGRAM_NAME+" File", MyFileDialog.OPEN);
		if (file == null) return null;
		
		/***** TODO music 파일의 공통 이름을 찾기 *****/
		String commonFile = file.getPath();
		commonFile = commonFile.substring(0, commonFile.lastIndexOf('.'));
		commonFile = commonFile.substring(0, commonFile.lastIndexOf('.'));
		
		/***** TODO music, musicInput 배열의 초기화 *****/
		int musicCount = 0;
		for (int i = 0; true; i++) {
			File tmp = new File(commonFile+"."+i+"."+EXT);
			if (!tmp.canRead()) {
				musicCount = i;
				break;
			}
		}
		music = new Music[musicCount];
		musicInput = new MyFileInput[musicCount];
		for (int i = 0; i < musicCount; i++) {
			music[i] = new Music();
			musicInput[i] = new MyFileInput( new File(commonFile+"."+i+"."+EXT) );
			musicInput[i].input();
		}
		
		/***** TODO 파일 입력 *****/
		try {
			for (int fileInIdx = 0; fileInIdx < musicCount; fileInIdx++) {
				Music posMusic = music[fileInIdx];
				MyFileInput posMusicInput = musicInput[fileInIdx];
				String posAllContent = posMusicInput.getComments();
				int contentPos = 0;
				
				// allSplitCmt에 있는 모든 명령을 posMusic에 넣기
				inputFromContentLoop:
					while (true) {
						try {
							if (isOverPos(posAllContent, contentPos)) {
								break inputFromContentLoop;
							}
							contentPos = addCommand(contentPos, posAllContent, posMusic);
							
						} catch (IndexOutOfBoundsException xcp) {
							throwCmdXcp(CommandException.WRONG, contentPos);
						} catch (NumberFormatException xcp) {
							throwCmdXcp(CommandException.WRONG, contentPos);
						} catch (PitchException xcp) {
							throwCmdXcp(CommandException.WRONG, contentPos);
						}
					}
				}
			//inputFromContentLoop 끝
		} catch (CommandException xcp) {
			xcp.printStackTrace();
			return null;
		}
		return music;
	}
	
	
	
	/* TODO: 명령 추가 루틴 */
	private static int addCommand(int pos, String posAllContent, Music posMusic) throws PitchException, CommandException
	{
		int contentPos = pos;
		
		CmdKindSwitch:
			switch (posAllContent.charAt(contentPos)) {
			case 't': // TODO tempo
				contentPos++;
				String tempoStr = "";
				for ( ; !isOverPos(posAllContent, contentPos) && 
				!isPosSpace(posAllContent, contentPos); contentPos++) {
					tempoStr += posAllContent.charAt(contentPos);
				}
				int tempo = Integer.parseInt(tempoStr);
				posMusic.add(new Tempo(tempo));
				break CmdKindSwitch;
				
			case 'n': // TODO note
				contentPos++;
				String noteStr = "";
				for ( ; !isOverPos(posAllContent, contentPos) && 
				!isPosSpace(posAllContent, contentPos); contentPos++) {
					noteStr += posAllContent.charAt(contentPos);
				}
				int noteOctave = noteStr.charAt(0) - '0';
				String notePitchName = noteStr.substring(1);
				posMusic.add(new Note(Pitch.pitchStringToNumber(noteOctave, notePitchName)));
				break CmdKindSwitch;
				
			case 'e': // TODO time
				contentPos++;
				String timeStr = "";
				for ( ; !isOverPos(posAllContent, contentPos) && 
				!isPosSpace(posAllContent, contentPos); contentPos++) {
					timeStr += posAllContent.charAt(contentPos);
				}
				long time = (long) (Double.parseDouble(timeStr) * (double) SequencePlayer.TICK);
				posMusic.add(new BeatTime(time));
				break CmdKindSwitch;
				
			case 's': // TODO stop
				contentPos++;
				String restStr = "";
				for ( ; !isOverPos(posAllContent, contentPos) && 
				!isPosSpace(posAllContent, contentPos); contentPos++) {
					restStr += posAllContent.charAt(contentPos);
				}
				int restOctave = restStr.charAt(0) - '0';
				String restPitchName = restStr.substring(1);
				posMusic.add(new Rest(Pitch.pitchStringToNumber(restOctave, restPitchName)));
				break CmdKindSwitch;
				
			case 'd': // TODO dynamics
				contentPos++;
				String dynamicsStr = "";
				for ( ; 
				!isOverPos(posAllContent, contentPos) && 
				!isPosSpace(posAllContent, contentPos); 
				contentPos++) {
					dynamicsStr += posAllContent.charAt(contentPos);
				}
				Dynamics dynamics = null;
				
				if (dynamicsStr.equals("pp")) {
					dynamics = new Dynamics(Dynamics.PIANISSIMO);
				} else if (dynamicsStr.equals("p")) {
					dynamics = new Dynamics(Dynamics.PIANO);
				} else if (dynamicsStr.equals("mp")) {
					dynamics = new Dynamics(Dynamics.MEZZO_PIANO);
				} else if (dynamicsStr.equals("mf")) {
					dynamics = new Dynamics(Dynamics.MEZZO_FORTE);
				} else if (dynamicsStr.equals("f")) {
					dynamics = new Dynamics(Dynamics.FORTE);
				} else if (dynamicsStr.equals("ff")) {
					dynamics = new Dynamics(Dynamics.FORTISSIMO);
				} else {
					throwCmdXcp(CommandException.WRONG, contentPos);
				}
				posMusic.add(dynamics);
				break CmdKindSwitch;
				
			case 'o': // TODO output
				String lyric = "";
				if (posAllContent.charAt(++contentPos) != '<') {
					throwCmdXcp(CommandException.WRONG, contentPos);
				}
				
				for ( contentPos++; posAllContent.charAt(contentPos) != '>'; contentPos++) {
					lyric += posAllContent.charAt(contentPos);
				}
				posMusic.add(new Lyric(lyric));
				contentPos++;
				break CmdKindSwitch;
				
			case '(': // TODO comment(주석)
				while (posAllContent.charAt(contentPos) != ')') {
					contentPos++;
				}
				contentPos++;
				break CmdKindSwitch;
				
			default:
				if (!isPosSpace(posAllContent, contentPos))
					throwCmdXcp(CommandException.INVALID, contentPos);
				else
					contentPos++;
			}
		//CmdKindSwitch 끝
		
		return contentPos;
	}
}

