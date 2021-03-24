/**********************************************************************************
 *
 *  GOAL: complete all methods below!
 *              
 **********************************************************************************/
import java.util.Scanner;

public class Body {
    public final double SCALE = 0.042;  // scalar for star size

    private double rx;      // x position
    private double ry;      // y position
    private double vx;      // x velocity
    private double vy;      // y velocity

    private double mass;    // mass
    private String image;   // png image
    private double size;    // size
    
    private double fx;      // x force
    private double fy;      // y force

/**********************************************************************************
 *  Constructors        
 **********************************************************************************/

    // create and init a new object with input parameters scanned from a .txt file
    public Body(Scanner scan, double R) {
        // TODO
    }

/**********************************************************************************
 *  Modifiers      
 **********************************************************************************/

    // set fx & fy to 0
    public void zeroF() {
        // TODO
    }

    // update fx & fy with the additive gravitational force from the input Body obj
    public void updateF(Body obj, double G) {
        // TODO
    }

    // update this object's position and velocity
    public void step(double dt, double R) {
        // TODO
    }

    // draw this object using it's rx, ry, and image data
    public void draw() {;
        // TODO
    }

/**********************************************************************************
 *  Accessors      
 **********************************************************************************/

    // print formatted rx, ry, vx, vy, mass, & image data of the object
    public void status() {
        // TODO
    }

}
