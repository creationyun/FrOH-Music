package package_FrOH_Music;

import java.awt.*;

public class UseContainer {
	private Container container;
	
	public void setContainer(Container container) {
		this.container = container;
	}
	public Container getContainer() {
		return container;
	}
	public void openWait() {
		try {
			while (true) {
				if (!container.isVisible()) {
					break;
				}
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void setVisible(boolean visible) {
		container.setVisible(visible);
	}
	public void dispose() {
		if (container instanceof Window) {
			((Window) container).dispose();
		}
	}
}
