package physics2d.debug;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;

import javafx.geometry.Dimension2D;
import physics2d.*;
import physics2d.collisiondetections.*;
import physics2d.contactsolver.*;
import physics2d.integration.*;
import physics2d.maths.Vec2;

public class Viewer extends JPanel {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(Viewer::startApplication);
	}
	
	private static void startApplication() {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new Viewer());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
	    frame.setLocationRelativeTo(null);
	    frame.setResizable(false);
		frame.setVisible(true);
	}
	
	private final PhysicsWorld world;
	private final Collection<RigidBody> bodies;
	
	public Viewer() {
		super(true);
		
		new Timer(16, e -> {
			repaint();
		}).start();
		
		bodies = new ArrayList<>();
		
		bodies.addAll(Arrays.asList(
				new CircleBody(new Vec2(400, 400), new Vec2(0, 0), 100),
			    
				new PlaneBody(new Vec2(0, 0), new Vec2(0, 1)),
				new PlaneBody(new Vec2(0, 0), new Vec2(1, 0)),
				new PlaneBody(new Vec2(1000, 1000), new Vec2(-1, 0)),
				new PlaneBody(new Vec2(1000, 1000), new Vec2(0, -1))
		));
		
		Integrator integrator = new EulerIntegrator(bodies, 0.005, Integer.MAX_VALUE);
		CollisionDetector detector = new BasicCollision();
		
		for(RigidBody body : bodies) {
			detector.addRigidBody(body);
		}
		
		ContactSolver solver = new PGSContactSolver(4, 0.9);
		
		world = new PhysicsWorld(detector, solver, integrator);
		
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				RigidBody b = new CircleBody(new Vec2(e.getX(), e.getY()), new Vec2(Math.random() * 1000 - 500, Math.random() * 1000 - 500), Math.random() * 200 + 50);
				bodies.add(b);
				detector.addRigidBody(b);
			}

			@Override public void mousePressed(MouseEvent e) {}
			@Override public void mouseReleased(MouseEvent e) {}
			@Override public void mouseEntered(MouseEvent e) {}
			@Override public void mouseExited(MouseEvent e) {}
		});
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(1000, 1000);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.clearRect(0, 0, getWidth(), getHeight());
		
		world.tickWorld(0.016);
		
		for(RigidBody b : bodies) {
			if(b instanceof CircleBody)
				paintShape((CircleBody) b, (Graphics2D) g);
		}
	}

	private void paintShape(CircleBody b, Graphics2D g) {
		int x = (int) (b.position().x() - b.radius());
		int y = (int) (b.position().y() - b.radius());
		
		g.drawOval(x, y, (int) b.radius() * 2, (int) b.radius() * 2);
	}
}
