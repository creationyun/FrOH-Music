package package_MyFunction;

import java.io.*;

public class MyFileOutput extends MyFileIO {
	public MyFileOutput(File file, String comments) {
		super(file, comments);
	}
	
	public void output( ) {
		try {
			FileWriter fw = new FileWriter( getFile() );
			fw.write(getComments());
			fw.close( );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}