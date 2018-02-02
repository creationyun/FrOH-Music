package package_MyFunction;

import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.*;

import package_FrOH_Music.Program;

public class MyFileDialog {
	public static final int OPEN = 0;
	public static final int SAVE = 1;
	public static File showFileDialog(Component com, String ext, String des, int mode) {
		JFileChooser chooser = new JFileChooser( );
		FileNameExtensionFilter filter = new FileNameExtensionFilter(des, ext);
		chooser.setFileFilter(filter);
		
		int status = -1;
		
		switch (mode) {
		case OPEN:
			status = chooser.showOpenDialog(com);
			break;
		case SAVE:
			status = chooser.showSaveDialog(com);
			break;
		default:
			return null;
		}
		
		if (status == JFileChooser.APPROVE_OPTION) {
			String selectedFileParentStr = chooser.getSelectedFile().getParent();
			String selectedFileStr = chooser.getSelectedFile( ).getName( );
			//마지막의 점 위치를 찾을 수 없거나 확장자가 포함되어 있지 않을경우
			if (selectedFileStr.lastIndexOf('.') == -1 ||
					!selectedFileStr.substring(
							selectedFileStr.lastIndexOf('.')+1).toUpperCase( ).equals(ext.toUpperCase( ))
							) {
				selectedFileStr += "." + ext;
			}
			
			File resultFile = new File(selectedFileParentStr + Program.SEPARATOR + selectedFileStr);
			return resultFile;
		}
		return null;
	}
}