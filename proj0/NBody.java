public class NBody{

	public static double readRadius(String filepath){
		In in = new In(filepath);
		int N = in.readInt();
		double radius = in.readDouble();
		return radius;
	}

	public static Body[] readBodies(String filepath){
		In in = new In(filepath);
		int N = in.readInt();
		double r = in.readDouble();
		int i = 0;
		Body[] bodies = new Body[N];
		while (i<N){
			double xP = in.readDouble();
			double yP = in.readDouble();
			double xV = in.readDouble();
			double yV = in.readDouble();
			double m = in.readDouble();
			String name = in.readString();
			bodies[i] = new Body(xP,yP,xV,yV,m,name);
			i += 1;
		}
		return bodies;
	}

	public static void main(String[] args){
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double radius = readRadius(filename);
		Body[] bodies = readBodies(filename);
		StdDraw.enableDoubleBuffering();
		StdDraw.setScale(-(radius+0.5*Math.pow(10,11)), radius+0.5*Math.pow(10,11));
		StdDraw.picture(0, 0, "./images/starfield.jpg");
		StdDraw.show();
		for(int i=0;i<bodies.length;i++){
			bodies[i].draw();
		}
		double[] xForces = new double[bodies.length];
		double[] yForces = new double[bodies.length];
		for (double time=0; time <= T; time = time + dt){
			for(int i=0; i<bodies.length; i++){
				xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
				yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
			}
			for(int j=0; j<bodies.length; j++){
				bodies[j].update(dt,xForces[j],yForces[j]);
			}
			StdDraw.setScale(-(radius+0.5*Math.pow(10,11)), radius+0.5*Math.pow(10,11));
			StdDraw.picture(0, 0, "./images/starfield.jpg");
			for(int i=0; i<bodies.length;i++){
				bodies[i].draw();
			}
			StdDraw.show();
			StdDraw.pause(10);
		}
		StdOut.printf("%d\n", bodies.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < bodies.length; i++) {
    	StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                  bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);   
}

	}
}