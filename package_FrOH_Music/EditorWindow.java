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
			Language.langStr("New", "���� �����"));
	private final JButton btnNextNew = new JButton(
			Language.langStr("New (Next File)", "���� ����� (���� ����)"));
	private final JButton btnOpen = new JButton(
			Language.langStr("Open", "����"));
	private final JButton btnPrevOpen = new JButton(
			Language.langStr("Open (Previous File)", "���� (���� ����)"));
	private final JButton btnNextOpen = new JButton(
			Language.langStr("Open (Next File)", "���� (���� ����)"));
	private final JButton btnSave = new JButton(
			Language.langStr("Save", "����"));
	private final JButton btnSaveAs = new JButton(
			Language.langStr("Save As", "�ٸ� �̸����� ����"));
	
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
			Language.langStr("Find", "ã��"));
	private final JButton btnReplace = new JButton(
			Language.langStr("Replace", "�ٲٱ�"));
	
	private final JButton[] editBtns = {
			btnFind, 
			btnReplace, 
	};
	
	private final JButton btnHelp = new JButton(
			Language.langStr("Help", "����"));
	private final JButton btnAbout = new JButton(
			Language.langStr("About", "����"));
	private final JButton btnExit = new JButton(
			Language.langStr("Return to menu", "�޴��� ���ư���"));
	
	private final JButton[] helpBtns = {
			btnHelp,
			btnAbout,
			btnExit,
	};
	
	private final JTextArea content = new JTextArea("");
	
	private final String UNTITLED = Language.langStr("Untitled", "���� ����");
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
		
		/* TODO: ��� �߰� �г� */
		panSouth.setLayout(new BorderLayout());
		panFunction.setLayout(new GridLayout(2, 3));
		
		/* ��� ����� panFunction���� */
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
	
	
	
	
	/* TODO: �������� �ɼ� */
	/* ������ â�� ����. */
	public void openWindow() {
		editorEvent.start();
		window.setVisible(true);
	}
	
	/* ������ â�� ����� ������ ��ٸ���. */
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
	
	/* ������ â�� �ݱ�. */
	public void closeWindow() {
		window.setVisible(false);
		editorEvent.interrupt();
		window.dispose();
	}
	
	
	
	
	/* TODO: ���� ���� ��ƾ�� */
	
	
	/* ���� ���� ��ƾ
	 * ������ �� �� �޽����� ���� �ʰ�
	 * ������ ������ �ҷ����� ��ƾ�Դϴ�. */
	private void openFile(File file) {
		MyFileInput mfi = new MyFileInput( file );
		mfi.input( );
		content.setText(savedContent = mfi.getComments( ));
		savedFile = file;
	}
	
	
	/* ���� ���� ��ƾ
	 * Ȯ���ڸ� ���� ��ȣ�� ���� ���� �ٽ� Ȯ���ڸ� ���̴� ����� �����ν�
	 * ������ ���ڸ� �������� ���ϸ� ��ȣ�� ���̰�
	 * ���ڰ� �پ� �ִٸ� ��ȣ�� ���� �ʿ䰡 ���� �ų� ���������Դϴ�. */
	private void saveFile(File file, String saveStr) {
		File tmpfile = null;
		if (getFileNum(file) == -1) {
			String fileParent = file.getParent();
			String fileName = file.getName( );
			//Ȯ���� ����
			fileName = fileName.substring(0, fileName.lastIndexOf('.'));
			//�ٽ� ��ȣ�� ���̰� Ȯ���� ���̱�
			fileName += "." + currentFileNum + "." + EXT;
			tmpfile = new File(fileParent+Program.SEPARATOR+fileName);
		} else {
			tmpfile = new File(file.getPath( ));
		}
		MyFileOutput mfo = new MyFileOutput( tmpfile, savedContent = saveStr );
		mfo.output( );
		savedFile = tmpfile;
	}
	
	
	/* ���� ��ȣ�� �ҷ��� ��
	 * ���� ��ȣ�� ���� ���� ��ȣ�� �����ϴ� ��ƾ�Դϴ�. */
	private void setFileNum( File anythingFile ) {
		int fileNum = getFileNum(anythingFile);
		if (fileNum == -1) return;
		currentFileNum = fileNum;
	}
	
	
	/* ������ �̿��� ���� ��ȣ�� �����ϴ� ��ƾ�Դϴ�.
	 * ���� ���� ��ȣ�� �������� ���ߴٸ� -1�� ��ȯ�մϴ�. */
	private int getFileNum(File file) {
		String commonNameStr = file.getName( );
		//Ȯ���� ����
		commonNameStr = commonNameStr.substring(0, commonNameStr.lastIndexOf('.'));
		if (commonNameStr.lastIndexOf('.') == -1) {
			return -1;
		}
		//���� ��ȣ ����
		return Integer.parseInt(commonNameStr.substring(commonNameStr.lastIndexOf('.')+1));
	}
	
	
	/* ���� ���� ���
	 * ����� ������ Ȯ���ڿ� ��ȣ�� �� String ���·� ��ȯ�Ͽ�
	 * ���ο� ��ȣ�� ������ ����� ����
	 * ������� ��ƾ�Դϴ�. */
	private String getCommonFilePath( ) {
		String commonPathStr = savedFile.getPath( );
		//Ȯ���� ����
		commonPathStr = commonPathStr.substring(0, commonPathStr.lastIndexOf('.'));
		//��ȣ ����
		commonPathStr = commonPathStr.substring(0, commonPathStr.lastIndexOf('.'));
		return commonPathStr;
	}
	
	
	/* ������ �� ���ΰ� ���� ���ΰ��� ���� �޽����� ȣ���ϴ� ��ƾ�Դϴ�.
	 * ���´� int�� ��ȯ�˴ϴ�. */
	private int showSaveMsg( ) {
		return JOptionPane.showConfirmDialog(
				window, 
				Language.langStr("Do you want to save changes?", "����� ������ �����Ͻðڽ��ϱ�?"), 
				PROGRAM_TITLE, 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE
		);
	}
	
	
	/* ������ ��� �����ϴ� ��ƾ����
	 * showSaveMsg ��ƾ�� �̿��Ͽ� ����ڿ��� ���� �޽����� ����
	 * ���� ������ �����ϴ� ��ƾ�Դϴ�. */
	private boolean fileChooseSave() {
		File file = null;
		switch (showSaveMsg()) {
		case JOptionPane.YES_OPTION:
			if (savedFile == null) {
				file = MyFileDialog.showFileDialog((Component)window, EXT, 
						COMMON_PROGRAM_NAME+" ����", MyFileDialog.SAVE);
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
	
	
	/* ������ ���ΰ�ħ�ϴ� ��ƾ�Դϴ�. */
	private void refreshTitle( ) {
		String viewFileName = "";
		if (savedFile == null) viewFileName = UNTITLED;
		else viewFileName = savedFile.getName( );
		
		window.setTitle((changed ? "*" : "") + viewFileName + " - " + PROGRAM_TITLE + " ver " + VERSION);
	}
	
	
	/* str�� ���� ��ɾ �ؽ�Ʈ ������ �߰��ϴ� ��ƾ�Դϴ�. */
	private void addContent( String str ) {
		int focus = content.getSelectionEnd( );
		content.insert(str, focus);
		content.requestFocus( );
		content.setText(content.getText( ).replace("  ", " "));
		content.select(focus, focus+str.length( )+content.getRows()-1);
	}
	
	
	
	
	
	
	/* TODO: ���ʿ� �޴� ��ư���� �߰��ϴ� ��ƾ�Դϴ�. */
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
	
	
	/* TODO: �޴� ��ư���� �׼� �߰� */
	private void addMenuBtnsAction() {
		
		// ���� �����
		btnNew.addActionListener(new ActionListener( ) {
			public void actionPerformed(ActionEvent e) {
				/* ������ �ٸ���, �����ϰ� ���� ����� */
				if (changed) {
					if (!fileChooseSave()) {
						return;
					}
				}
				content.setText(savedContent = "");
				savedFile = null;
			}
		});
		
		
		// ���� ����� (���� ����)
		btnNextNew.addActionListener(new ActionListener( ) {
			public void actionPerformed(ActionEvent arg0) {
				/* ������ �ٸ���, �����ϰ� ���� ����� */
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
									"���� ������ �̹� �ֽ��ϴ�.\n����ðڽ��ϱ�?"), 
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
		
		
		// ����
		btnOpen.addActionListener(new ActionListener( ) {
			public void actionPerformed(ActionEvent e) {
				/* ������ �ٸ���, �����ϰ� ���� */
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
		
		
		// ���� (���� ����)
		btnPrevOpen.addActionListener(new ActionListener( ) {
			public void actionPerformed(ActionEvent e) {
				if (currentFileNum - 1 < 0) {
					JOptionPane.showMessageDialog(window, 
							Language.langStr("A current file is first file.", 
							"���� ������ ù ��° �����Դϴ�."), 
							PROGRAM_TITLE, 
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				/* ������ �ٸ���, �����ϰ� ���� */
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
							"���� ������ ���� �� ���� �����Դϴ�."), 
							PROGRAM_TITLE, 
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		
		// ���� (���� ����)
		btnNextOpen.addActionListener(new ActionListener( ) {
			public void actionPerformed(ActionEvent e) {
				/* ������ �ٸ���, �����ϰ� ���� ����� */
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
							"���� ������ �����ϴ�."), 
							PROGRAM_TITLE, 
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		
		// TODO: ����
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
		
		
		// �ٸ� �̸����� ����
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
		
		
		// TODO: ã��
		btnFind.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				openFindDialog();
			}
		});
		
		
		// �ٲٱ�
		btnReplace.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openReplaceDialog();
			}
		});
		
		
		// TODO: ����
		btnHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Help.openHelp();
			}
		});
		
		
		// ����
		btnAbout.addActionListener(new ActionListener( ) {
			public void actionPerformed(ActionEvent e) {
				final JDialog about = new JDialog((Frame)window, 
						Language.langStr("About", 
						"����"), true);
				about.setLayout(new FlowLayout( ));
				about.setSize(120, 120);
				about.setLocation(window.getWidth( )/2, window.getHeight( )/2);
				about.setResizable(false);
				
				JLabel aboutLabel1 = new JLabel(PROGRAM_TITLE, JLabel.CENTER);
				JLabel aboutLabel2 = new JLabel("Version "+VERSION, JLabel.CENTER);
				JButton aboutOk = new JButton(Language.langStr("OK", "Ȯ��"));
				
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
		
		
		// TODO: ���� ȭ��
		btnExit.addActionListener(new ActionListener( ) {
			public void actionPerformed(ActionEvent e) {
				if (changed) {
					if (!fileChooseSave( )) return;
				}
				
				closeWindow();
			}
		});
	}
	
	
	
	/* TODO: window�� content �߰� */
	private void addContent() {
		JScrollPane scrolledContent = new JScrollPane( content, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
		);
		window.add(scrolledContent, "Center");
	}
	
	
	
	
	
	
	/* TODO: �� �߰� ��� */
	private JPanel pitchFunction() {
		JPanel pitchPanel = new JPanel( );
		pitchPanel.setLayout(new FlowLayout( ));
		
		JPanel pitchOptionPanel = new JPanel( );
		JPanel pitchAddButtonsPanel = new JPanel();
		pitchOptionPanel.setLayout(new FlowLayout( ));
		pitchAddButtonsPanel.setLayout(new FlowLayout( ));
		
		/* pitchOptionPanel */
		pitchOptionPanel.add(new JLabel(Language.langStr("Octave", "��Ÿ��")));
		final JTextField octave = new JTextField("5", 3); pitchOptionPanel.add(octave);
		
		final JComboBox<Object> pitches = 
			new JComboBox<Object>( new String[] {
					"C(" + Language.langStr("do", "��") + ")", 
					"D(" + Language.langStr("re", "��") + ")", 
					"E(" + Language.langStr("mi", "��") + ")", 
					"F(" + Language.langStr("fa", "��") + ")", 
					"G(" + Language.langStr("sol", "��") + ")", 
					"A(" + Language.langStr("la", "��") + ")", 
					"B(" + Language.langStr("ti", "��") + ")" } );
		pitchOptionPanel.add(pitches);
		
		final JComboBox<Object> accidentals = new JComboBox<Object>( new String[] { 
				Language.langStr("natural", "���ڸ�ǥ"), 
				Language.langStr("sharp", "�ø�ǥ"), 
				Language.langStr("flat", "����ǥ") } );
		pitchOptionPanel.add(accidentals);
		
		JButton noteAddBtn = new JButton(Language.langStr("Note", "��ǥ"));
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
									"��Ÿ�긦 �߸� �Է��ϼ̽��ϴ�."), 
							PROGRAM_TITLE,
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				command += ((String)pitches.getSelectedItem( )).charAt(0);
				
				switch (accidentals.getSelectedIndex( )) {
				case 0: //���ڸ�ǥ
					break;
				case 1: //�ø�ǥ
					command += "#";
					break;
				case 2: //����ǥ
					command += "b";
					break;
				default:
					JOptionPane.showMessageDialog(window, 
							Language.langStr("Choose a accidental.", 
									"�ӽ�ǥ�� �����ϼ���."), PROGRAM_TITLE,
							JOptionPane.WARNING_MESSAGE);
				}
				command += " ";
				
				addContent(command);
			}
		});
		pitchAddButtonsPanel.add(noteAddBtn);
		JButton restAddBtn = new JButton(Language.langStr("Rest", "��ǥ"));
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
									"��Ÿ�긦 �߸� �Է��ϼ̽��ϴ�."), 
							PROGRAM_TITLE,
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				command += ((String)pitches.getSelectedItem( )).charAt(0);
				
				switch (accidentals.getSelectedIndex( )) {
				case 0: //���ڸ�ǥ
					break;
				case 1: //�ø�ǥ
					command += "#";
					break;
				case 2: //����ǥ
					command += "b";
					break;
				default:
					JOptionPane.showMessageDialog(window, 
							Language.langStr("Choose a accidental.", 
									"�ӽ�ǥ�� �����ϼ���."), PROGRAM_TITLE,
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
	
	
	
	
	
	
	/* TODO: ����/���� �߰� ��� */
	private JPanel timeNTempoFunction() {
		JPanel timeNTempoPanel = new JPanel( );
		timeNTempoPanel.setLayout(new GridLayout(1, 2));
		
		JPanel timePanel = new JPanel( );
		timePanel.setLayout(new FlowLayout( ));
		timePanel.add(new JLabel(Language.langStr("<Time>", "<����>")));
		final JTextField timeTextField = new JTextField(5); timePanel.add(timeTextField);
		JButton timeAddBtn = new JButton(Language.langStr("Add", "�߰�"));
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
									"���ڸ� �߸� �Է��ϼ̽��ϴ�."), 
							PROGRAM_TITLE,
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		timePanel.add(timeAddBtn);
		timeNTempoPanel.add(timePanel);
		
		JPanel tempoPanel = new JPanel( );
		tempoPanel.setLayout(new FlowLayout( ));
		tempoPanel.add(new JLabel(Language.langStr("<Tempo>", "<����>")));
		final JTextField tempoTextField = new JTextField(5); tempoPanel.add(tempoTextField);
		JButton tempoAddBtn = new JButton(Language.langStr("Add", "�߰�"));
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
									"������ �߸� �Է��ϼ̽��ϴ�."), 
							PROGRAM_TITLE,
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		tempoPanel.add(tempoAddBtn);
		timeNTempoPanel.add(tempoPanel);
		return timeNTempoPanel;
	}
	
	
	
	
	
	/* TODO: ���� ���/�ּ� �ޱ� ��� */
	private JPanel outputNCommentFunction() {
		JPanel outputNCommentPanel = new JPanel( );
		outputNCommentPanel.setLayout(new BorderLayout( ));
		final JTextArea outputNComment = new JTextArea(8, 15);
		
		JPanel outputNCommentBtnsPanel = new JPanel();
		outputNCommentBtnsPanel.setLayout(new FlowLayout());
		JButton outputBtn = new JButton(Language.langStr("Lyrics output", "���� ���"));
		outputBtn.addActionListener(new ActionListener( ) {
			public void actionPerformed(ActionEvent e) {
				addContent(" o<"+outputNComment.getText( )+"> ");
			}
		});
		JButton commentBtn = new JButton(Language.langStr("Commenting", "�ּ� �ޱ�"));
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
	
	
	
	
	
	/* TODO: ������ ��� */
	private JPanel dynamicsFunction() {
		JPanel dynamicsPanel = new JPanel( );
		dynamicsPanel.setLayout(new FlowLayout( ));
		dynamicsPanel.add(new JLabel(Language.langStr("<Dynamics>", "<������>")));
		
		final JComboBox<Object> dynamics = new JComboBox<Object>( new String[] { "pp", "p", "mp", "mf", "f", "ff" } );
		
		JButton dynamicsAddBtn = new JButton(Language.langStr("Add", "�߰�"));
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
	
	
	
	
	
	
	
	/* TODO: ã�� ���̾�α� */
	private void openFindDialog() {
		final JDialog findDialog = new JDialog(window, Language.langStr("Find", "ã��"));
		findDialog.setSize(400, 75);
		findDialog.setResizable(false);
		
		JPanel findPanel = new JPanel();
		findPanel.setLayout(new FlowLayout());
		
		findPanel.add(new JLabel(Language.langStr("Find: ", "ã�� ����: ")));
		
		final JTextField findText = new JTextField(10);
		findPanel.add(findText);
		
		JButton findBtn = new JButton(Language.langStr("Find Next", "���� ã��"));
		JButton cancelBtn = new JButton(Language.langStr("Cancel", "���"));
		
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
	
	
	
	
	/* TODO: �ٲٱ� ���̾�α� */
	private void openReplaceDialog() {
		final JDialog replaceDialog = new JDialog(window, Language.langStr("Replace", "�ٲٱ�"));
		replaceDialog.setSize(250, 150);
		replaceDialog.setResizable(false);
		replaceDialog.setLayout(new GridLayout(1, 2));
		
		JPanel replacePanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		replacePanel.setLayout(new FlowLayout());
		replacePanel.setLayout(new FlowLayout());
		buttonPanel.setLayout(new GridLayout(4, 1));
		
		replacePanel.add(new JLabel(Language.langStr("Find: ", "ã�� ����: ")));
		
		final JTextField findText = new JTextField(10);
		replacePanel.add(findText);
		
		
		replacePanel.add(new JLabel(Language.langStr("Replace: ", "�ٲ� ����: ")));
		
		final JTextField replaceText = new JTextField(10);
		replacePanel.add(replaceText);
		
		JButton findBtn = new JButton(Language.langStr("Find Next", "���� ã��"));
		JButton replaceBtn = new JButton(Language.langStr("Replace", "�ٲٱ�"));
		JButton replaceAllBtn = new JButton(Language.langStr("Replace All", "��� �ٲٱ�"));
		JButton cancelBtn = new JButton(Language.langStr("Cancel", "���"));
		
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
							Language.langStr("You can't replace text.", "�ؽ�Ʈ�� �ٲ� �� �����ϴ�."),
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
	
	
	
	
	/* TODO: ã�� ��ƾ
	 * ã������ true, ã�� �������� false�� ��ȯ */
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
	
	
	
	
	/* TODO: ��, ĭ �� ǥ�� */
	private JPanel panCols() {
		JPanel colsPanel = new JPanel();
		
		colsPanel.add(new JLabel(Language.langStr("Position: ", "��ġ: ")));
		colsPanel.add(cols);
		
		return colsPanel;
	}
	
	
	
	
	
	/* TODO: ������ �̺�Ʈ �߰� */
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
	
	
	
	
	
	
	/* TODO: ���� Ŭ����: ������ �̺�Ʈ */
	private class EditorEvent implements Runnable {
		public void run( ) {
			while (true) {
				try {
					if (!content.getText( ).equals(savedContent)) changed = true;
					else changed = false;
					
					// ����ȭ�� ���� if���� tmp�� changed�� �ٸ� ��� ���ΰ�ħ�ϵ��� �Ǿ� ����.
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