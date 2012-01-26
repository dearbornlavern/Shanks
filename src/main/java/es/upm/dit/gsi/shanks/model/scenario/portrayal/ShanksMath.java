package es.upm.dit.gsi.shanks.model.scenario.portrayal;

import sim.util.Double2D;
import sim.util.Double3D;

public class ShanksMath {

    public static final Double3D origen3D = new Double3D(0, 0, 0);
    public static final Double2D origen2D = new Double2D(0, 0);

    public static final Double2D ANGLE_0 = new Double2D(0,0);
    public static final Double2D ANGLE_45 = new Double2D(1,1);
    public static final Double2D ANGLE_90 = new Double2D(0,1);
    public static final Double2D ANGLE_135 = new Double2D(-1,1);
    public static final Double2D ANGLE_180 = new Double2D(-1,0);
    public static final Double2D ANGLE_225 = new Double2D(-1,-1);
    public static final Double2D ANGLE_270 = new Double2D(0,-1);
    public static final Double2D ANGLE_315 = new Double2D(1,-1);

    public static final Double RAD_ANGLE_0 = 0.0;
    public static final Double RAD_ANGLE_45 = Math.PI/4;
    public static final Double RAD_ANGLE_90 = Math.PI/2;
    public static final Double RAD_ANGLE_135 = Math.PI*3/4;
    public static final Double RAD_ANGLE_180 = Math.PI;
    public static final Double RAD_ANGLE_225 = Math.PI*5/4;
    public static final Double RAD_ANGLE_270 = Math.PI*3/2;
    public static final Double RAD_ANGLE_315 = Math.PI*7/4;
    
    /**
     * @param orig
     * @param alpha XY
     * @param beta XZ
     * @param gamma YZ
     * @return Double3D object
     */
    public static Double3D rotate(Double3D orig, Double alpha, Double beta, Double gamma) {
        Double2D origXY = new Double2D(orig.x, orig.y);
        Double2D firstRotated = ShanksMath.rotate(origXY, alpha);
        Double2D firstRotatedXorigZ = new Double2D(firstRotated.x, orig.z);
        Double2D secondRotated = ShanksMath.rotate(firstRotatedXorigZ,
                beta);
        Double2D firstRotatedYsecondRotatedZ = new Double2D(firstRotated.y, secondRotated.y);
        Double2D thirdRotated = ShanksMath.rotate(firstRotatedYsecondRotatedZ, gamma);
        return new Double3D(secondRotated.x, thirdRotated.x, thirdRotated.y);
    }
    
    /**
     * @param orig
     * @param alpha
     * @return Double2D object
     */
    public static Double2D rotate(Double2D orig, Double alpha) {
        Double finalX = Math.cos(alpha) * orig.x - Math.sin(alpha) * orig.y;
        Double finalY = Math.sin(alpha) * orig.x + Math.cos(alpha) * orig.y;
        return new Double2D(finalX, finalY);
    }

    /**
     * @param orig
     * @param alpha XY
     * @param beta XZ
     * @param gamma YZ
     * @return Double3D object
     */
    public static Double3D rotate(Double3D orig, Double2D alpha, Double2D beta, Double2D gamma) {
        Double2D origXY = new Double2D(orig.x, orig.y);
        Double2D firstRotated = ShanksMath.rotate(origXY, alpha);
        Double2D firstRotatedXorigZ = new Double2D(firstRotated.x, orig.z);
        Double2D secondRotated = ShanksMath.rotate(firstRotatedXorigZ,
                beta);
        Double2D firstRotatedYsecondRotatedZ = new Double2D(firstRotated.y,secondRotated.y);
        Double2D thirdRotated = ShanksMath.rotate(firstRotatedYsecondRotatedZ, gamma); 
        return new Double3D(secondRotated.x, thirdRotated.x, thirdRotated.y);
    }

    /**
     * @param orig
     * @param alpha
     * @return Double2D object
     */
    public static Double2D rotate(Double2D orig, Double2D alpha) {
        Double moduleA = alpha.distance(origen2D);
        if (moduleA == 0) {
            return orig;
        } else {
            Double cosA = alpha.x / moduleA;
            Double sinA = alpha.y / moduleA;
            Double finalX = cosA * orig.x - sinA * orig.y;
            Double finalY = sinA * orig.x + cosA * orig.y;
            return new Double2D(finalX, finalY);
        }
    }
    
    /**
     * @param orig
     * @param alpha
     * @param beta
     * @return Double2D object
     */
    public static Double2D rotate(Double2D orig, Double2D alpha, Double2D beta) {
        Double2D firstRotated = ShanksMath.rotate(orig, alpha);
        Double2D secondRotated = ShanksMath.rotate(new Double2D(firstRotated.y,0.0), beta);
        return new Double2D(firstRotated.x,secondRotated.x);
    }

    /**
     * @param orig
     * @param offset
     * @return Double3D object
     */
    public static Double3D add(Double3D orig, Double3D offset) {
        return new Double3D(orig.x + offset.x, orig.y + offset.y, orig.z
                + offset.z);
    }

    /**
     * @param orig
     * @param offset
     * @return Double2D object
     */
    public static Double2D add(Double2D orig, Double2D offset) {
        return new Double2D(orig.x + offset.x, orig.y + offset.y);
    }

}
