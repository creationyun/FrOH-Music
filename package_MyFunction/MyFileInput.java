package package_MyFunction;

import java.io.*;

public class MyFileInput extends MyFileIO {
	public MyFileInput(File file) {
		super(file, "");
	}
	
	public void input( ) {
		try {
			FileReader fr = new FileReader( getFile() );
			int ch;
			
			while ((ch = fr.read()) != -1) {
				setComments(getComments() + (char)ch);
			}
			
			fr.close( );
		} catch (IOException e) {
			e.printStackTrace( );
		}
	}
}