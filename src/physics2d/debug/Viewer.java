package physics2d.debug;

import java.awt.*;
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
		
		bodies = Arrays.asList(
				new CircleBody(new Vec2(800, 400), new Vec2(-40, 0), 100),
				new CircleBody(new Vec2(400, 400), new Vec2(40, 0), 100)
		);
		
		Integrator integrator = new EulerIntegrator(bodies, 0.005, 7);
		CollisionDetector detector = new BasicCollision();
		
		for(RigidBody body : bodies) {
			detector.addRigidBody(body);
		}
		
		ContactSolver solver = new PGSContactSolver(4, 0.9);
		
		world = new PhysicsWorld(detector, solver, integrator);		
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
			paintShape(b, (Graphics2D) g);
		}
	}

	private void paintShape(RigidBody b, Graphics2D g) {
		int x = (int) (b.position().x() - 50);
		int y = (int) (b.position().y() - 50);
		
		g.drawOval(x, y, 100, 100);
	}
}
