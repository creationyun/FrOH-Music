package package_FrOH_Music;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

import package_Music.*;
import package_MyFunction.*;

public class EditorWindow implements Program {
	private JFrame window = new JFrame();
	
	private static final String PROGRAM_TITLE = COMMON_PROGRAM_NAME + " Editor";
	
	private static final FlowLayout defaultPanelLayout = new FlowLayout(FlowLayout.LEFT, 0, 1);
	
	private final JPanel panNorth = new JPanel();
	private final JPanel panFile = new JPanel();
	private final JPanel panEdit = new JPanel();
	private final JPanel panHelp = new JPanel();
	
	private final JPanel panSouth = new JPanel();
	private final JPanel panFunction = new JPanel();
	
	private final JLabel cols = new JLabel();
	
	private final JButton btnNew = new JButton(
			Language.langStr("New", "새로 만들기"));
	private final JButton btnNextNew = new JButton(
			Language.langStr("New (Next File)", "새로 만들기 (다음 파일)"));
	private final JButton btnOpen = new JButton(
			Language.langStr("Open", "열기"));
	private final JButton btnPrevOpen = new JButton(
			Language.langStr("Open (Previous File)", "열기 (이전 파일)"));
	private final JButton btnNextOpen = new JButton(
			Language.langStr("Open (Next File)", "열기 (다음 파일)"));
	private final JButton btnSave = new JButton(
			Language.langStr("Save", "저장"));
	private final JButton btnSaveAs = new JButton(
			Language.langStr("Save As", "다른 이름으로 저장"));
	
	private final JButton[] fileBtns = {
			btnNew,
			btnNextNew,
			btnOpen,
			btnPrevOpen,
			btnNextOpen,
			btnSave,
			btnSaveAs,
	};
	
	private final JButton btnFind = new JButton(
			Language.langStr("Find", "찾기"));
	private final JButton btnReplace = new JButton(
			Language.langStr("Replace", "바꾸기"));
	
	private final JButton[] editBtns = {
			btnFind, 
			btnReplace, 
	};
	
	private final JButton btnHelp = new JButton(
			Language.langStr("Help", "도움말"));
	private final JButton btnAbout = new JButton(
			Language.langStr("About", "정보"));
	private final JButton btnExit = new JButton(
			Language.langStr("Return to menu", "메뉴로 돌아가기"));
	
	private final JButton[] helpBtns = {
			btnHelp,
			btnAbout,
			btnExit,
	};
	
	private final JTextArea content = new JTextArea("");
	
	private final String UNTITLED = Language.langStr("Untitled", "제목 없음");
	private File savedFile = null;
	private boolean changed = false;
	private int currentFileNum = 0;
	
	private String savedContent = content.getText( );
	
	private Thread editorEvent = new Thread(new EditorEvent( ), "Editor Event");
	
	
	
	
	
	
	{
		window.setSize(800, 800);
		
		window.setLayout(new BorderLayout());
		
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		addPanNorth();
		addContent();
		addMenuBtnsAction();
		
		/* TODO: 기능 추가 패널 */
		panSouth.setLayout(new BorderLayout());
		panFunction.setLayout(new GridLayout(2, 3));
		
		/* 모든 기능을 panFunction으로 */
		panFunction.add(pitchFunction());
		panFunction.add(timeNTempoFunction());
		panFunction.add(outputNCommentFunction());
		panFunction.add(dynamicsFunction());
		
		panSouth.add(panFunction, "Center");
		panSouth.add(panCols(), "South");
		
		window.add(panSouth, "South");
		addWindowEvent();
		
		refreshTitle();
	}
	
	
	
	
	/* TODO: 윈도우의 옵션 */
	/* 윈도우 창을 열기. */
	public void openWindow() {
		editorEvent.start();
		window.setVisible(true);
	}
	
	/* 윈도우 창이 사라질 때까지 기다리기. */
	public void openWait() {
		while (true) {
			try {
				if (window.isVisible()) {
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
	
	/* 윈도우 창을 닫기. */
	public void closeWindow() {
		window.setVisible(false);
		editorEvent.interrupt();
		window.dispose();
	}
	
	
	
	
	/* TODO: 파일 제어 루틴들 */
	
	
	/* 파일 열기 루틴
	 * 파일을 열 때 메시지는 뜨지 않고
	 * 파일의 내용을 불러오는 루틴입니다. */
	private void openFile(File file) {
		MyFileInput mfi = new MyFileInput( file );
		mfi.input( );
		content.setText(savedContent = mfi.getComments( ));
		savedFile = file;
	}
	
	
	/* 파일 저장 루틴
	 * 확장자를 빼고 번호를 붙인 다음 다시 확장자를 붙이는 방법을 씀으로써
	 * 파일의 숫자를 추출하지 못하면 번호를 붙이고
	 * 숫자가 붙어 있다면 번호를 붙일 필요가 없는 거나 마찬가지입니다. */
	private void saveFile(File file, String saveStr) {
		File tmpfile = null;
		if (getFileNum(file) == -1) {
			String fileParent = file.getParent();
			String fileName = file.getName( );
			//확장자 빼기
			fileName = fileName.substring(0, fileName.lastIndexOf('.'));
			//다시 번호를 붙이고 확장자 붙이기
			fileName += "." + currentFileNum + "." + EXT;
			tmpfile = new File(fileParent+Program.SEPARATOR+fileName);
		} else {
			tmpfile = new File(file.getPath( ));
		}
		MyFileOutput mfo = new MyFileOutput( tmpfile, savedContent = saveStr );
		mfo.output( );
		savedFile = tmpfile;
	}
	
	
	/* 파일 번호를 불러온 후
	 * 파일 번호를 현재 파일 번호에 저장하는 루틴입니다. */
	private void setFileNum( File anythingFile ) {
		int fileNum = getFileNum(anythingFile);
		if (fileNum == -1) return;
		currentFileNum = fileNum;
	}
	
	
	/* 파일을 이용해 파일 번호를 추출하는 루틴입니다.
	 * 만약 파일 번호를 추출하지 못했다면 -1을 반환합니다. */
	private int getFileNum(File file) {
		String commonNameStr = file.getName( );
		//확장자 빼기
		commonNameStr = commonNameStr.substring(0, commonNameStr.lastIndexOf('.'));
		if (commonNameStr.lastIndexOf('.') == -1) {
			return -1;
		}
		//현재 번호 추출
		return Integer.parseInt(commonNameStr.substring(commonNameStr.lastIndexOf('.')+1));
	}
	
	
	/* 공통 파일 경로
	 * 저장된 파일의 확장자와 번호를 뺀 String 상태로 반환하여
	 * 새로운 번호의 파일을 만들기 위해
	 * 만들어진 루틴입니다. */
	private String getCommonFilePath( ) {
		String commonPathStr = savedFile.getPath( );
		//확장자 빼기
		commonPathStr = commonPathStr.substring(0, commonPathStr.lastIndexOf('.'));
		//번호 빼기
		commonPathStr = commonPathStr.substring(0, commonPathStr.lastIndexOf('.'));
		return commonPathStr;
	}
	
	
	/* 저장을 할 것인가 안할 것인가를 묻는 메시지를 호출하는 루틴입니다.
	 * 상태는 int로 반환됩니다. */
	private int showSaveMsg( ) {
		return JOptionPane.showConfirmDialog(
				window, 
				Language.langStr("Do you want to save changes?", "변경된 내용을 저장하시겠습니까?"), 
				PROGRAM_TITLE, 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE
		);
	}
	
	
	/* 파일을 골라서 저장하는 루틴으로
	 * showSaveMsg 루틴을 이용하여 사용자에게 저장 메시지로 묻고
	 * 파일 내용을 저장하는 루틴입니다. */
	private boolean fileChooseSave() {
		File file = null;
		switch (showSaveMsg()) {
		case JOptionPane.YES_OPTION:
			if (savedFile == null) {
				file = MyFileDialog.showFileDialog((Component)window, EXT, 
						COMMON_PROGRAM_NAME+" 파일", MyFileDialog.SAVE);
				if (file == null)
					return false;
			} else {
				file = savedFile;
			}
			saveFile(file, savedContent = content.getText( ));
			break;
		case JOptionPane.NO_OPTION:
			break;
		case JOptionPane.CANCEL_OPTION:
		default:
			return false;
		}
		return true;
	}
	
	
	/* 제목을 새로고침하는 루틴입니다. */
	private void refreshTitle( ) {
		String viewFileName = "";
		if (savedFile == null) viewFileName = UNTITLED;
		else viewFileName = savedFile.getName( );
		
		window.setTitle((changed ? "*" : "") + viewFileName + " - " + PROGRAM_TITLE + " ver " + VERSION);
	}
	
	
	/* str을 통해 명령어를 텍스트 영역에 추가하는 루틴입니다. */
	private void addContent( String str ) {
		int focus = content.getSelectionEnd( );
		content.insert(str, focus);
		content.requestFocus( );
		content.setText(content.getText( ).replace("  ", " "));
		content.select(focus, focus+str.length( )+content.getRows()-1);
	}
	
	
	
	
	
	
	/* TODO: 위쪽에 메뉴 버튼들을 추가하는 루틴입니다. */
	private void addPanNorth() {
		panNorth.setLayout(new GridLayout(3, 1));
		
		panFile.setLayout(defaultPanelLayout);
		panEdit.setLayout(defaultPanelLayout);
		panHelp.setLayout(defaultPanelLayout);
		
		for (int fileBtnsIdx = 0; fileBtnsIdx < fileBtns.length; fileBtnsIdx++) {
			panFile.add(fileBtns[fileBtnsIdx]);
		}
		for (int editBtnsIdx = 0; editBtnsIdx < editBtns.length; editBtnsIdx++) {
			panEdit.add(editBtns[editBtnsIdx]);
		}
		for (int helpBtnsIdx = 0; helpBtnsIdx < helpBtns.length; helpBtnsIdx++) {
			panHelp.add(helpBtns[helpBtnsIdx]);
		}
		
		panNorth.add(panFile);
		panNorth.add(panEdit);
		panNorth.add(panHelp);
		
		window.add(panNorth, "North");
	}
	
	
	/* TODO: 메뉴 버튼들의 액션 추가 */
	private void addMenuBtnsAction() {
		
		// 새로 만들기
		btnNew.addActionListener(new ActionListener( ) {
			public void actionPerformed(ActionEvent e) {
				/* 내용이 다르면, 저장하고 새로 만들기 */
				if (changed) {
					if (!fileChooseSave()) {
						return;
					}
				}
				content.setText(savedContent = "");
				savedFile = null;
			}
		});
		
		
		// 새로 만들기 (다음 파일)
		btnNextNew.addActionListener(new ActionListener( ) {
			public void actionPerformed(ActionEvent arg0) {
				/* 내용이 다르면, 저장하고 새로 만들기 */
				if (savedFile == null) {
					if (!fileChooseSave()) {
						return;
					}
				} else if (changed) {
					switch (showSaveMsg()) {
					case JOptionPane.YES_OPTION:
						saveFile(savedFile, content.getText());
					case JOptionPane.NO_OPTION:
						break;
					default:
						return;
					}
				}
				File file = new File(getCommonFilePath( )+"."+(currentFileNum+1)+"."+EXT);
				if (file.exists( )) {
					int message = 
						JOptionPane.showConfirmDialog(	window, 
							Language.langStr("A next file is already exist.\nDo you want to overwrite it?", 
									"다음 파일이 이미 있습니다.\n덮어쓰시겠습니까?"), 
							PROGRAM_TITLE, 
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
					switch (message) {
					case JOptionPane.YES_OPTION:
						break;
					case JOptionPane.NO_OPTION:
					case JOptionPane.CANCEL_OPTION:
					default:
						return;
					}
				}
				MyFileOutput mfo = new MyFileOutput(file, savedContent = "");
				mfo.output( );
				currentFileNum++;
				content.setText(savedContent = "");
				savedFile = file;
			}
		});
		
		
		// 열기
		btnOpen.addActionListener(new ActionListener( ) {
			public void actionPerformed(ActionEvent e) {
				/* 내용이 다르면, 저장하고 열기 */
				if (changed) {
					if (!fileChooseSave()) {
						return;
					}
				}
				File file = MyFileDialog.showFileDialog(window, EXT, COMMON_PROGRAM_NAME+" File",
						MyFileDialog.OPEN);
				if (file == null) {
					return;
				}
				openFile(file);
				setFileNum(savedFile);
			}
		});
		
		
		// 열기 (이전 파일)
		btnPrevOpen.addActionListener(new ActionListener( ) {
			public void actionPerformed(ActionEvent e) {
				if (currentFileNum - 1 < 0) {
					JOptionPane.showMessageDialog(window, 
							Language.langStr("A current file is first file.", 
							"현재 파일은 첫 번째 파일입니다."), 
							PROGRAM_TITLE, 
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				/* 내용이 다르면, 저장하고 열기 */
				if (savedFile == null) {
					if (!fileChooseSave()) {
						return;
					}
				} else if (changed) {
					switch (showSaveMsg()) {
					case JOptionPane.YES_OPTION:
						saveFile(savedFile, content.getText());
					case JOptionPane.NO_OPTION:
						break;
					default:
						return;
					}
				}
				File file = new File(getCommonFilePath( )+"."+(currentFileNum-1)+"."+EXT);
				if (file.canRead( )) {
					openFile(file);
					currentFileNum--;
				} else {
					JOptionPane.showMessageDialog(window, 
							Language.langStr("A previous file is unreadable file.", 
							"이전 파일은 읽을 수 없는 파일입니다."), 
							PROGRAM_TITLE, 
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		
		// 열기 (다음 파일)
		btnNextOpen.addActionListener(new ActionListener( ) {
			public void actionPerformed(ActionEvent e) {
				/* 내용이 다르면, 저장하고 새로 만들기 */
				if (savedFile == null) {
					if (!fileChooseSave()) {
						return;
					}
				} else if (changed) {
					switch (showSaveMsg()) {
					case JOptionPane.YES_OPTION:
						saveFile(savedFile, content.getText());
					case JOptionPane.NO_OPTION:
						break;
					default:
						return;
					}
				}
				File file = new File(getCommonFilePath( )+"."+(currentFileNum+1)+"."+EXT);
				if (file.canRead( )) {
					openFile(file);
					currentFileNum++;
				} else {
					JOptionPane.showMessageDialog(window, 
							Language.langStr("A next file is not exist.", 
							"다음 파일이 없습니다."), 
							PROGRAM_TITLE, 
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		
		// TODO: 저장
		btnSave.addActionListener(new ActionListener( ) {
			public void actionPerformed(ActionEvent e) {
				File file = null;
				if (savedFile == null) {
					file = MyFileDialog.showFileDialog(window, EXT, COMMON_PROGRAM_NAME+" File", 
							MyFileDialog.SAVE);
					if (file == null) {
						return;
					} else {
						savedFile = file;
					}
				}
				saveFile(savedFile, content.getText( ));
			}
		});
		
		
		// 다른 이름으로 저장
		btnSaveAs.addActionListener(new ActionListener( ) {
			public void actionPerformed(ActionEvent e) {
				File file = MyFileDialog.showFileDialog(window, EXT, COMMON_PROGRAM_NAME+" File", 
						MyFileDialog.SAVE);
				if (file == null) {
					return;
				} else {
					savedFile = file;
				}
				saveFile(savedFile, content.getText( ));
			}
		});
		
		
		// TODO: 찾기
		btnFind.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				openFindDialog();
			}
		});
		
		
		// 바꾸기
		btnReplace.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openReplaceDialog();
			}
		});
		
		
		// TODO: 도움말
		btnHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Help.openHelp();
			}
		});
		
		
		// 정보
		btnAbout.addActionListener(new ActionListener( ) {
			public void actionPerformed(ActionEvent e) {
				final JDialog about = new JDialog((Frame)window, 
						Language.langStr("About", 
						"정보"), true);
				about.setLayout(new FlowLayout( ));
				about.setSize(120, 120);
				about.setLocation(window.getWidth( )/2, window.getHeight( )/2);
				about.setResizable(false);
				
				JLabel aboutLabel1 = new JLabel(PROGRAM_TITLE, JLabel.CENTER);
				JLabel aboutLabel2 = new JLabel("Version "+VERSION, JLabel.CENTER);
				JButton aboutOk = new JButton(Language.langStr("OK", "확인"));
				
				aboutOk.addActionListener(new ActionListener( ) {
					public void actionPerformed(ActionEvent e) {
						about.setVisible(false);
						about.dispose( );
					}
				});
				about.add(aboutLabel1); about.add(aboutLabel2); about.add(aboutOk);
				about.addWindowListener(new WindowListener( ) {
					public void windowOpened(WindowEvent e) {  }
					public void windowClosing(WindowEvent e) {
						about.setVisible(false);
						about.dispose( );
					}
					public void windowClosed(WindowEvent e) {  }
					public void windowIconified(WindowEvent e) {  }
					public void windowDeiconified(WindowEvent e) {  }
					public void windowActivated(WindowEvent e) {  }
					public void windowDeactivated(WindowEvent e) {  }
				});
				
				about.setVisible(true);
			}
		});
		
		
		// TODO: 이전 화면
		btnExit.addActionListener(new ActionListener( ) {
			public void actionPerformed(ActionEvent e) {
				if (changed) {
					if (!fileChooseSave( )) return;
				}
				
				closeWindow();
			}
		});
	}
	
	
	
	/* TODO: window에 content 추가 */
	private void addContent() {
		JScrollPane scrolledContent = new JScrollPane( content, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
		);
		window.add(scrolledContent, "Center");
	}
	
	
	
	
	
	
	/* TODO: 음 추가 기능 */
	private JPanel pitchFunction() {
		JPanel pitchPanel = new JPanel( );
		pitchPanel.setLayout(new FlowLayout( ));
		
		JPanel pitchOptionPanel = new JPanel( );
		JPanel pitchAddButtonsPanel = new JPanel();
		pitchOptionPanel.setLayout(new FlowLayout( ));
		pitchAddButtonsPanel.setLayout(new FlowLayout( ));
		
		/* pitchOptionPanel */
		pitchOptionPanel.add(new JLabel(Language.langStr("Octave", "옥타브")));
		final JTextField octave = new JTextField("5", 3); pitchOptionPanel.add(octave);
		
		final JComboBox<Object> pitches = 
			new JComboBox<Object>( new String[] {
					"C(" + Language.langStr("do", "도") + ")", 
					"D(" + Language.langStr("re", "레") + ")", 
					"E(" + Language.langStr("mi", "미") + ")", 
					"F(" + Language.langStr("fa", "파") + ")", 
					"G(" + Language.langStr("sol", "솔") + ")", 
					"A(" + Language.langStr("la", "라") + ")", 
					"B(" + Language.langStr("ti", "시") + ")" } );
		pitchOptionPanel.add(pitches);
		
		final JComboBox<Object> accidentals = new JComboBox<Object>( new String[] { 
				Language.langStr("natural", "제자리표"), 
				Language.langStr("sharp", "올림표"), 
				Language.langStr("flat", "내림표") } );
		pitchOptionPanel.add(accidentals);
		
		JButton noteAddBtn = new JButton(Language.langStr("Note", "음표"));
		noteAddBtn.addActionListener(new ActionListener( ) {
			public void actionPerformed(ActionEvent e) {
				String command = " n";
				
				try {
					int octaveNum = Integer.parseInt(octave.getText( ));
					if (octaveNum < 1 || octaveNum > 9) {
						throw new PitchException();
					}
					command += octaveNum;
				} catch (Exception xcp) {
					JOptionPane.showMessageDialog(window, 
							Language.langStr("You have entered the wrong octave.", 
									"옥타브를 잘못 입력하셨습니다."), 
							PROGRAM_TITLE,
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				command += ((String)pitches.getSelectedItem( )).charAt(0);
				
				switch (accidentals.getSelectedIndex( )) {
				case 0: //제자리표
					break;
				case 1: //올림표
					command += "#";
					break;
				case 2: //내림표
					command += "b";
					break;
				default:
					JOptionPane.showMessageDialog(window, 
							Language.langStr("Choose a accidental.", 
									"임시표를 선택하세요."), PROGRAM_TITLE,
							JOptionPane.WARNING_MESSAGE);
				}
				command += " ";
				
				addContent(command);
			}
		});
		pitchAddButtonsPanel.add(noteAddBtn);
		JButton restAddBtn = new JButton(Language.langStr("Rest", "쉼표"));
		restAddBtn.addActionListener(new ActionListener( ) {
			public void actionPerformed(ActionEvent e) {
				String command = " s";
				
				try {
					int octaveNum = Integer.parseInt(octave.getText( ));
					if (octaveNum < 1 || octaveNum > 9) {
						throw new PitchException();
					}
					command += octaveNum;
				} catch (Exception xcp) {
					JOptionPane.showMessageDialog(window, 
							Language.langStr("You have entered the wrong octave.", 
									"옥타브를 잘못 입력하셨습니다."), 
							PROGRAM_TITLE,
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				command += ((String)pitches.getSelectedItem( )).charAt(0);
				
				switch (accidentals.getSelectedIndex( )) {
				case 0: //제자리표
					break;
				case 1: //올림표
					command += "#";
					break;
				case 2: //내림표
					command += "b";
					break;
				default:
					JOptionPane.showMessageDialog(window, 
							Language.langStr("Choose a accidental.", 
									"임시표를 선택하세요."), PROGRAM_TITLE,
							JOptionPane.WARNING_MESSAGE);
				}
				command += " ";
				
				addContent(command);
			}
		});
		pitchAddButtonsPanel.add(restAddBtn);
		
		pitchPanel.add(pitchOptionPanel);
		pitchPanel.add(pitchAddButtonsPanel);
		
		return pitchPanel;
	}
	
	
	
	
	
	
	/* TODO: 박자/템포 추가 기능 */
	private JPanel timeNTempoFunction() {
		JPanel timeNTempoPanel = new JPanel( );
		timeNTempoPanel.setLayout(new GridLayout(1, 2));
		
		JPanel timePanel = new JPanel( );
		timePanel.setLayout(new FlowLayout( ));
		timePanel.add(new JLabel(Language.langStr("<Time>", "<박자>")));
		final JTextField timeTextField = new JTextField(5); timePanel.add(timeTextField);
		JButton timeAddBtn = new JButton(Language.langStr("Add", "추가"));
		timeAddBtn.addActionListener(new ActionListener( ) {
			public void actionPerformed(ActionEvent e) {
				try {
					double time = Double.parseDouble(timeTextField.getText( ));
					if (time > 0.0) {
						addContent(" e"+timeTextField.getText( )+" ");
					} else {
						throw new Exception();
					}
				} catch (Exception xcp) {
					JOptionPane.showMessageDialog(window, 
							Language.langStr("You have entered the wrong time.", 
									"박자를 잘못 입력하셨습니다."), 
							PROGRAM_TITLE,
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		timePanel.add(timeAddBtn);
		timeNTempoPanel.add(timePanel);
		
		JPanel tempoPanel = new JPanel( );
		tempoPanel.setLayout(new FlowLayout( ));
		tempoPanel.add(new JLabel(Language.langStr("<Tempo>", "<템포>")));
		final JTextField tempoTextField = new JTextField(5); tempoPanel.add(tempoTextField);
		JButton tempoAddBtn = new JButton(Language.langStr("Add", "추가"));
		tempoAddBtn.addActionListener(new ActionListener( ) {
			public void actionPerformed(ActionEvent e) {
				try {
					int tempo = Integer.parseInt(tempoTextField.getText( ));
					if (tempo > 0) {
						addContent(" t"+tempoTextField.getText( )+" ");
					} else {
						throw new Exception();
					}
				} catch (Exception xcp) {
					JOptionPane.showMessageDialog(window, 
							Language.langStr("You have entered the wrong tempo.", 
									"템포를 잘못 입력하셨습니다."), 
							PROGRAM_TITLE,
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		tempoPanel.add(tempoAddBtn);
		timeNTempoPanel.add(tempoPanel);
		return timeNTempoPanel;
	}
	
	
	
	
	
	/* TODO: 가사 출력/주석 달기 기능 */
	private JPanel outputNCommentFunction() {
		JPanel outputNCommentPanel = new JPanel( );
		outputNCommentPanel.setLayout(new BorderLayout( ));
		final JTextArea outputNComment = new JTextArea(8, 15);
		
		JPanel outputNCommentBtnsPanel = new JPanel();
		outputNCommentBtnsPanel.setLayout(new FlowLayout());
		JButton outputBtn = new JButton(Language.langStr("Lyrics output", "가사 출력"));
		outputBtn.addActionListener(new ActionListener( ) {
			public void actionPerformed(ActionEvent e) {
				addContent(" o<"+outputNComment.getText( )+"> ");
			}
		});
		JButton commentBtn = new JButton(Language.langStr("Commenting", "주석 달기"));
		commentBtn.addActionListener(new ActionListener( ) {
			public void actionPerformed(ActionEvent e) {
				addContent(" ("+outputNComment.getText( )+") ");
			}
		});
		
		outputNCommentBtnsPanel.add(outputBtn);
		outputNCommentBtnsPanel.add(commentBtn);
		
		JScrollPane outputNCommentScroll = new JScrollPane( outputNComment, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
		);
		outputNCommentPanel.add(outputNCommentScroll, "Center");
		outputNCommentPanel.add(outputNCommentBtnsPanel, "South");
		return outputNCommentPanel;
	}
	
	
	
	
	
	/* TODO: 셈여림 기능 */
	private JPanel dynamicsFunction() {
		JPanel dynamicsPanel = new JPanel( );
		dynamicsPanel.setLayout(new FlowLayout( ));
		dynamicsPanel.add(new JLabel(Language.langStr("<Dynamics>", "<셈여림>")));
		
		final JComboBox<Object> dynamics = new JComboBox<Object>( new String[] { "pp", "p", "mp", "mf", "f", "ff" } );
		
		JButton dynamicsAddBtn = new JButton(Language.langStr("Add", "추가"));
		dynamicsAddBtn.addActionListener(new ActionListener( ) {
			public void actionPerformed(ActionEvent e) {
				String commandCmt = " d";
				
				commandCmt += dynamics.getSelectedItem( );
				commandCmt += " ";
				addContent(commandCmt);
			}
		});
		dynamicsPanel.add(dynamics);
		dynamicsPanel.add(dynamicsAddBtn);
		return dynamicsPanel;
	}
	
	
	
	
	
	
	
	/* TODO: 찾기 다이얼로그 */
	private void openFindDialog() {
		final JDialog findDialog = new JDialog(window, Language.langStr("Find", "찾기"));
		findDialog.setSize(400, 75);
		findDialog.setResizable(false);
		
		JPanel findPanel = new JPanel();
		findPanel.setLayout(new FlowLayout());
		
		findPanel.add(new JLabel(Language.langStr("Find: ", "찾을 내용: ")));
		
		final JTextField findText = new JTextField(10);
		findPanel.add(findText);
		
		JButton findBtn = new JButton(Language.langStr("Find Next", "다음 찾기"));
		JButton cancelBtn = new JButton(Language.langStr("Cancel", "취소"));
		
		findBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				find(findText);
			}
		});
		
		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				findDialog.setVisible(false);
				findDialog.dispose();
			}
		});
		
		findPanel.add(findBtn);
		findPanel.add(cancelBtn);
		
		findDialog.add(findPanel);
		
		findDialog.setVisible(true);
	}
	
	
	
	
	/* TODO: 바꾸기 다이얼로그 */
	private void openReplaceDialog() {
		final JDialog replaceDialog = new JDialog(window, Language.langStr("Replace", "바꾸기"));
		replaceDialog.setSize(250, 150);
		replaceDialog.setResizable(false);
		replaceDialog.setLayout(new GridLayout(1, 2));
		
		JPanel replacePanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		replacePanel.setLayout(new FlowLayout());
		replacePanel.setLayout(new FlowLayout());
		buttonPanel.setLayout(new GridLayout(4, 1));
		
		replacePanel.add(new JLabel(Language.langStr("Find: ", "찾을 내용: ")));
		
		final JTextField findText = new JTextField(10);
		replacePanel.add(findText);
		
		
		replacePanel.add(new JLabel(Language.langStr("Replace: ", "바꿀 내용: ")));
		
		final JTextField replaceText = new JTextField(10);
		replacePanel.add(replaceText);
		
		JButton findBtn = new JButton(Language.langStr("Find Next", "다음 찾기"));
		JButton replaceBtn = new JButton(Language.langStr("Replace", "바꾸기"));
		JButton replaceAllBtn = new JButton(Language.langStr("Replace All", "모두 바꾸기"));
		JButton cancelBtn = new JButton(Language.langStr("Cancel", "취소"));
		
		findBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {			
				find(findText);
			}
		});
		
		replaceBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean findResult = true;
				
				if (content.getSelectedText() == null) findResult = find(findText);
				
				if (findResult) {
					content.replaceSelection(replaceText.getText());
					find(findText);
				} else {
					JOptionPane.showMessageDialog(replaceDialog, 
							Language.langStr("You can't replace text.", "텍스트를 바꿀 수 없습니다."),
							PROGRAM_TITLE, JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		replaceAllBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				while (true) {
					content.select(0, 0);
					
					boolean findResult = find(findText);
					
					if (findResult) {
						content.replaceSelection(replaceText.getText());
					} else {
						break;
					}
				}
			}
		});
		
		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				replaceDialog.setVisible(false);
				replaceDialog.dispose();
			}
		});
		
		buttonPanel.add(findBtn);
		buttonPanel.add(replaceBtn);
		buttonPanel.add(replaceAllBtn);
		buttonPanel.add(cancelBtn);
		
		replaceDialog.add(replacePanel);
		replaceDialog.add(buttonPanel);
		
		replaceDialog.setVisible(true);
	}
	
	
	
	
	/* TODO: 찾기 루틴
	 * 찾았으면 true, 찾지 못했으면 false를 반환 */
	private boolean find(JTextField findText) {
		String contentText = content.getText();
		String findContentText = findText.getText();
		
		int findPos = contentText.indexOf(findContentText, content.getSelectionEnd());
		
		if (findPos == -1) {
			content.requestFocus();
			findPos = contentText.indexOf(findContentText, content.getSelectionEnd());
			if (findPos == -1) {
				return false;
			}
		}
		
		content.requestFocus();
		content.select(findPos, findPos+findContentText.length());
		return true;
	}
	
	
	
	
	/* TODO: 줄, 칸 수 표시 */
	private JPanel panCols() {
		JPanel colsPanel = new JPanel();
		
		colsPanel.add(new JLabel(Language.langStr("Position: ", "위치: ")));
		colsPanel.add(cols);
		
		return colsPanel;
	}
	
	
	
	
	
	/* TODO: 윈도우 이벤트 추가 */
	private void addWindowEvent() {
		window.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (changed) {
					if (!fileChooseSave()) return;
				}
				closeWindow();
			}
		});
	}
	
	
	
	
	
	
	/* TODO: 내부 클래스: 에디터 이벤트 */
	private class EditorEvent implements Runnable {
		public void run( ) {
			while (true) {
				try {
					if (!content.getText( ).equals(savedContent)) changed = true;
					else changed = false;
					
					// 최적화를 위해 if문이 tmp와 changed와 다를 경우 새로고침하도록 되어 있음.
					refreshTitle( );
					
					cols.setText(content.getSelectionEnd()+"");
					Thread.sleep(100);
				} catch (InterruptedException e) {
					break;
				}
			}
		}
	}
}