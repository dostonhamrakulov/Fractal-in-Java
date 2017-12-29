package main;
/**
* Created by Doston Hamrakulov doston.hamrakulov@gmail.com 15.11.2017
*
*/
import fractal.Zoomable;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class CanvasZoomable implements MouseListener, MouseMotionListener {
	
	private Component comp;
	private Zoomable toZoom;
	
	private double minScale;
	private boolean isZooming;
	private int x, y;

	public CanvasZoomable(double minScale, Component component, Zoomable toZoom) {
		this.minScale = minScale;
		this.toZoom = toZoom;
		this.comp = component;
		comp.addMouseListener(this);
		comp.addMouseMotionListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
		isZooming = false;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		isZooming = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (isZooming) {
			double w = comp.getWidth();
			double h = comp.getHeight();
			double dx = e.getX() - x;
			double dy = e.getY() - y;
			double rx = x / w;
			double ry = y / h;
			double dist = Math.sqrt(dx * dx + dy * dy);
			double scale = dist / Math.min(w, h);
			scale = Math.max(scale, minScale);

			toZoom.zoom(rx, ry, scale);
			isZooming = false;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
	
}
