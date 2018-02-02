package package_Music;

import javax.swing.JLabel;

public class PlayThread implements Runnable {
	private JLabel currentMinute;
	private JLabel currentSecond;
	private JLabel playMinute;
	private JLabel playSecond;

	private long playTime;

	private static final long MAX_SECOND = 60L;

	public void init() {
		playTime = 0L;
	}

	public void setPlayTime() {
		long playTimeMinute = playTime / MAX_SECOND;
		playMinute.setText(String.format("%02d", playTimeMinute));

		long playTimeSecond = playTime - (playTimeMinute * MAX_SECOND);
		playSecond.setText(String.format("%02d", playTimeSecond));
	}

	@Override
	public void run() {
		setPlayTime();

		long minute = 0L;
		long second = 0L;

		try {
			currentMinute.setText(String.format("%02d", minute));
			currentSecond.setText(String.format("%02d", second));

			while (true) {
				Thread.sleep(1000);
				second++;

				if (second >= MAX_SECOND) {
					minute++;
					second -= MAX_SECOND;
				}

				currentMinute.setText(String.format("%02d", minute));
				currentSecond.setText(String.format("%02d", second));
			}
		} catch (InterruptedException xcp) {
		}
	}

	public void calculatePlayTime(Music[] music) {
		long musicPlayMax = 0L;

		for (int musicIdx = 0; musicIdx < music.length; musicIdx++) {
			long posMusicPlay = 0L;
			Music posMusic = music[musicIdx];
			Tempo currentTempo = new Tempo(120);
			for (int getIdx = 0; getIdx < posMusic.size(); getIdx++) {
				MusicElement posElement = posMusic.get(getIdx);

				if (posElement instanceof Tempo) {
					currentTempo.setTempo(((Tempo) posElement).getTempo());
				}

				if (posElement instanceof BeatTime) {
					posMusicPlay +=
						(long) (60.0 / (double)currentTempo.getTempo() * (double)((BeatTime) posElement).get());
				}
			}

			if (musicPlayMax < posMusicPlay) musicPlayMax = posMusicPlay;
		}

		playTime = musicPlayMax / SequencePlayer.TICK;
	}

	public void setCurrentMinute(JLabel currentMinute) { this.currentMinute = currentMinute; }
	public void setCurrentSecond(JLabel currentSecond) { this.currentSecond = currentSecond; }
	public void setPlayMinute(JLabel playMinute) { this.playMinute = playMinute; }
	public void setPlaySecond(JLabel playSecond) { this.playSecond = playSecond; }
}
