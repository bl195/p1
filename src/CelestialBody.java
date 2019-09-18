
import java.util.*;
import java.lang.*;
import java.io.*;
/**
 * Celestial Body class for NBody
 * @author ola
 *
 */
public class CelestialBody {

	private double myXPos;
	private double myYPos;
	private double myXVel;
	private double myYVel;
	private double myMass;
	private String myFileName;

	/**
	 * Create a Body from parameters
	 * @param xp initial x position
	 * @param yp initial y position
	 * @param xv initial x velocity
	 * @param yv initial y velocity
	 * @param mass of object
	 * @param filename of image for object animation
	 */
	public CelestialBody(double xp, double yp, double xv,
						 double yv, double mass, String filename){
		this.myXPos =xp;
		this.myYPos =yp;
		this.myXVel = xv;
		this.myYVel = yv;
		this.myMass = mass;
		this.myFileName = filename;
	}

	/**
	 * Copy constructor: copy instance variables from one
	 * body to this body
	 * @param b used to initialize this body
	 */
	public CelestialBody(CelestialBody b){
		this.myXPos = b.myXPos;
		this.myYPos =b.myYPos;
		this.myXVel = b.myXVel;
		this.myYVel = b.myYVel;
		this.myMass = b.myMass;
		this.myFileName = b.myFileName;
	}

	public double getX() {
		return myXPos;
	}
	public double getY() {
		return myYPos;
	}
	public double getXVel() {
		return myXVel;
	}
	/**
	 * Return y-velocity of this Body.
	 * @return value of y-velocity.
	 */
	public double getYVel() {
		return myYVel;
	}

	public double getMass() {
		return myMass;
	}
	public String getName() {
		return myFileName;
	}

	/**
	 * Return the distance between this body and another
	 * @param b the other body to which distance is calculated
	 * @return distance between this body and b
	 */
	public double calcDistance(CelestialBody b) {
		// distance formula
		return Math.sqrt(Math.pow(b.myXPos-this.myXPos,2) + Math.pow(b.myYPos-this.myYPos,2));
	}

	public double calcForceExertedBy(CelestialBody b) {
		return (6.67*Math.pow(10,-11))*this.myMass*b.myMass/(this.calcDistance(b)*this.calcDistance(b));
	}

	public double calcForceExertedByX(CelestialBody b) {
		//x forces only
		return this.calcForceExertedBy(b)*(-this.myXPos+b.myXPos)/this.calcDistance(b);
	}
	public double calcForceExertedByY(CelestialBody b) {
		// y forces only
		return this.calcForceExertedBy(b)*(-this.myYPos+b.myYPos)/this.calcDistance(b);
	}

	public double calcNetForceExertedByX(CelestialBody[] bodies) {
		//x net force
		double netF = 0.0;
		for(CelestialBody b: bodies){
			if(b.equals(this)==false){
				netF += this.calcForceExertedByX(b);
			}
		}
		return netF;
	}

	public double calcNetForceExertedByY(CelestialBody[] bodies) {
		//y net force
		double netF = 0.0;
		for(CelestialBody b: bodies){
			if(b.equals(this)==false){
				netF += this.calcForceExertedByY(b);
			}
		}
		return netF;
	}

	public void update(double deltaT,
					   double xforce, double yforce) {
		double ax = xforce/this.myMass;
		double ay = yforce/this.myMass;
		double nvx = this.myXVel + deltaT*ax;
		double nvy = this.myYVel + deltaT*ay;
		double nx = this.myXPos+deltaT*nvx;
		double ny = this.myYPos + deltaT*nvy;
		this.myXVel = nvx;
		this.myYVel = nvy;
		this.myXPos =nx;
		this.myYPos = ny;
	}

	public void draw() {
		StdDraw.picture(this.myXPos, this.myYPos, "images/"+myFileName);
	}
}
