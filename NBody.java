/*******************************************************************************
 * 
 *  GOAL: Replace all parallel array with a single Body type array
 *  Dependencies: StdDraw.java, StdAudio.java, Body.java
 * 
 *  N-body simulation.
 *    *  Reads in input .txt file from the command line
 *    *  Reads in number of bodies N, radius of universe R, initial positions,
 *       velocities, masses, and name of image files from standard input;
 *    *  Simulate the system from time t = 0 until t >= T and plots the
 *       the results to standard drawing;
 *    *  Prints the final positions and velocities to standard output.
 *
 ******************************************************************************/

import java.util.Scanner;
import java.io.File; 
import java.io.FileNotFoundException;

public class NBody {

    // animation pause (in miliseconds)
    public static final int DELAY = 20;

    // music (2001 theme)
    public static final String MUSIC = "2001theme.wav";

    // background image
    public static final String BACKGROUND = "starfield.jpg";

    // parameters input file
    public static String PLANETS_FILE;

    // numerical constants
    public static final double G = 6.67e-11;    // gravitational constant (N m^2 / kg^2)
    public static final double T= 157788000.0;  // simulate from time 0 to T (s);             
    public static final double dt = 25000.0;    // time quantum (s);
    public static final double SCALE = 0.042;   // scalar for star size

    // parameters from first two lines 
    public static int N;                // number of bodies
    public static double R;             // radius of universe

    // global arrays of Body properties
    // TODO:    replace all of these arrays with one array of Body objects
    public static double[] rx;          // x position (m)
    public static double[] ry;          // y position (m)
    public static double[] vx;          // x velocity (m/s)
    public static double[] vy;          // y velocity (m/s)
    public static double[] mass;        // mass (kg)
    public static double[] size;        // size of object 
    public static String[] image;       // name of gif

    // read the planet file, new the parallel arrays, 
    // and load their values from the file.
    public static void loadPlanets() {
        
        // open a parameters File to read from
        Scanner scan = null;
        try { File f = new File(getFile()); scan = new Scanner( f ); } 
        catch(FileNotFoundException e) { System.out.println("File not found exception"); } 

        // read from standard input
        N = scan.nextInt();         // number of bodies
        R = scan.nextDouble();      // radius of universe (m)

        // declare parallel arrays
        // TODO:    declare the size of our array of Body objects in place of all of these arrays
        rx = new double[N];         // x position (m)
        ry = new double[N];         // y position (m)
        vx = new double[N];         // x velocity (m/s)
        vy = new double[N];         // y velocity (m/s)
        mass = new double[N];       // mass (kg)
        size = new double[N];       // size of object (~ diameter)
        image = new String[N];      // name of gif

        // read in initial position, velocity, mass, and image name from stdin
        // TODO:    for each index in our array of Body objects, initialize a Body object by passing the scanner to the Body constructor along with the world-size R
        for (int i = 0; i < N; i++) {
            rx[i]    = scan.nextDouble();
            ry[i]    = scan.nextDouble();
            vx[i]    = scan.nextDouble();
            vy[i]    = scan.nextDouble();
            mass[i]  = scan.nextDouble();
            size[i]  = Math.random() * SCALE * R;
            image[i] = scan.next();
        }
    }

    public static String getFile() {
        Scanner console = new Scanner( System.in );
        System.out.print("parameters input file: ");
        String inputFile = "chaos.txt"; // console.next();
        System.out.println(inputFile);
        console.close();
        return inputFile;
    }

    public static void runSimulation() {

        // run numerical simulation from 0 to T
        for (double t = 0.0; t < T; t += dt) {

            StdDraw.picture(0.0, 0.0, BACKGROUND);

            // the x- and y-components of force
            double[] fx = new double[N];
            double[] fy = new double[N];

            // calculate forces on each object
            // TODO:    for each index i in our array of Body objects, 
            //              call zeroF to set the Force of object i to 0
            //              call updateF for every other object j in our array of Body objects to sum all forces for object i at ths instance
            //              call stepF for object i to update velocity and position
            //              call draw for object i to add the object to the StDraw canvas
            for (int i = 0; i < N; i++) {
                fx[i] = 0;
                fy[i] = 0;
                for (int j = 0; j < N; j++) {
                    if (i != j) {
                        double dx = rx[j] - rx[i];
                        double dy = ry[j] - ry[i];
                        double rad = Math.sqrt(dx * dx + dy * dy);
                        double Force = G * mass[i] * mass[j] / (rad * rad);
                        
                        fx[i] += Force * dx / rad;
                        fy[i] += Force * dy / rad;
                    }    
                }
                // update velocities and positions
                vx[i] += dt * fx[i] / mass[i];
                vy[i] += dt * fy[i] / mass[i];

                rx[i] += dt * vx[i];
                ry[i] += dt * vy[i];

                StdDraw.picture(rx[i], ry[i], image[i], size[i], size[i]);
            }
            StdDraw.show();
            StdDraw.pause(DELAY);
        }

    }

    public static void main(String[] args) {
        loadPlanets();

        // rescale coordinates that we can use natural x- and y-coordinates
        StdDraw.setXscale(-R, +R);
        StdDraw.setYscale(-R, +R);

        StdAudio.play( MUSIC );

        StdDraw.enableDoubleBuffering();

        // Run simulation
        runSimulation();

        // print final state of universe to standard output
        System.out.printf("%d\n", N);
        System.out.printf("%.2e\n", R);
        
        // TODO:    for each index i in our array of Body objects, call status to format print object data
        for (int i = 0; i < N; i++) {
            System.out.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                          rx[i], ry[i], vx[i], vy[i], mass[i], image[i]);
        }

    }
}
