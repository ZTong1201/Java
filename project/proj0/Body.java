public class Body{
	/* 6 instances variables for Body class*/
	public double xxPos,yyPos,xxVel,yyVel,mass;
	public String imgFileName;


	/* First constructor*/
	public Body(double xP,double yP,double xV,double yV,double m,String img){
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	/*Second Constructor*/
	public Body(Body b){
		xxPos = b.xxPos;
		yyPos = b.yyPos;
		xxVel = b.xxVel;
		yyVel = b.yyVel;
		mass = b.mass;
		imgFileName = b.imgFileName;
	}

	public double calcDistance(Body b){
		double dist = 0;
		dist = Math.sqrt(Math.pow((xxPos-b.xxPos),2)+Math.pow((yyPos-b.yyPos),2));
		return dist;
	}

	public double calcForceExertedBy(Body b){
		double force = 0;
		double G = 6.67*Math.pow(10,-11);
		double r = this.calcDistance(b);
		force = G*mass*b.mass/Math.pow(r,2);
		return force;
	}

	public double calcForceExertedByX(Body b){
		double dx = b.xxPos - xxPos;
		double F = this.calcForceExertedBy(b);
		double r = this.calcDistance(b);
		double Fx = F*dx/r;
		return Fx;
	}

	public double calcForceExertedByY(Body b){
		double dy = b.yyPos - yyPos;
		double F = this.calcForceExertedBy(b);
		double r = this.calcDistance(b);
		double Fy = F*dy/r;
		return Fy;
	}

	public double calcNetForceExertedByX(Body[] bodies){
		double netForceX = 0;
		for (Body s:bodies){
			if (this.equals(s)){
				continue;
			}
			netForceX += this.calcForceExertedByX(s);
		}
		return netForceX;
	}

	public double calcNetForceExertedByY(Body[] bodies){
		double netForceY = 0;
		for (Body s:bodies){
			if (this.equals(s)){
				continue;
			}
			netForceY += this.calcForceExertedByY(s);
		}
		return netForceY;
	}

	public void update(double dt,double Fx, double Fy){
		double ax = Fx/mass;
		double ay = Fy/mass;
		xxVel += dt*ax;
		yyVel += dt*ay;
		xxPos += dt*xxVel;
		yyPos += dt*yyVel;
	}

	public void draw(){
		StdDraw.picture(xxPos, yyPos, "./images/"+imgFileName);
	}
}