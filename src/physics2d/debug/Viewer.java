package physics2d.debug;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import physics2d.*;
import physics2d.collisiondetections.*;
import physics2d.contactsolver.*;
import physics2d.integration.*;

public class Viewer extends Canvas {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(Viewer::startApplication);
	}
	
	private static void startApplication() {
		JFrame frame = new JFrame();
		frame.add(new Viewer());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
	    frame.setLocationRelativeTo(null);
	    frame.setResizable(false);
		frame.setVisible(true);
	}
	
	private final PhysicsWorld world;
	private final Collection<RigidBody> bodies;
	
	public Viewer() {
		super();
		
		setSize(1000, 1000);
		
		bodies = Arrays.asList();
		Integrator integrator = new EulerIntegrator(bodies, 0.005, 7);
		CollisionDetector detector = new BasicCollision();
		
		for(RigidBody body : bodies) {
			detector.addRigidBody(body);
		}
		
		ContactSolver solver = new PGSContactSolver(4, 0.9);
		
		world = new PhysicsWorld(detector, solver, integrator);
		
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		
		for(RigidBody b : bodies) {
			paintShape(b, (Graphics2D) g);
		}
		
		repaint();
	}

	private void paintShape(RigidBody b, Graphics2D g) {
		int x = (int) (b.position().x() - 50);
		int y = (int) (b.position().y() - 50);
		
		g.drawOval(x, y, 100, 100);
	}
}
