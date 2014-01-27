package org.powerbot.os.event;

import java.awt.*;
import java.util.EventListener;

/**
 * A listener that represents a class object that listens for paint events.
 *
 * @author Timer
 */
public interface PaintListener extends EventListener {
	public void repaint(Graphics render);
}
