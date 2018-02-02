package package_MyFunction;

import java.io.*;

public class MyFileIO {
	private File file;
	private String comments;
	
	public MyFileIO(File file, String comments) {
		this.file = file;
		setComments(comments);
	}
	
	public File getFile( ) { return file; }
	public void setComments(String comments) { this.comments = comments; }
	public String getComments( ) { return comments; }
}