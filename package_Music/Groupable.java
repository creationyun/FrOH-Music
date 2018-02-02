package package_Music;


public interface Groupable {
	public void add(MusicElement m);
	public void add(MusicElement[] m);
	public void remove(MusicElement m);
	public void remove(int index);
	public MusicElement[] getGroup( );
}