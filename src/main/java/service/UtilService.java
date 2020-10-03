package service;

import com.mercadolibre.quasarfireoperationjg.exceptions.CalculationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import service.trileration.Point;
import service.trileration.Trilateration;

@Service
public class UtilService {

    private static final double EPSILON = 0.000001;

    private static final Logger log = LoggerFactory.getLogger(UtilService.class);


    public String mergeMessages(String[] messages1, String[] messages2,String[] messages3 ){
        StringBuilder sb = new StringBuilder();
        for(int i= 0; i < messages1.length; i++){
            String word = "";
            if (messages1[i].equals(messages2[i]) && !messages1[i].equals("")) {
                word =  ("".equals(messages1[i]))?  messages3[i]:  messages1[i];
            } else if (messages1[i].equals(messages3[i]) && !messages1[i].equals("")) {
                word =  ("".equals(messages1[i]))?  messages2[i]:  messages1[i];
            } else if (messages2[i].equals(messages3[i]) && !messages2[i].equals("")) {
                word =  ("".equals(messages1[i]))?  messages1[i]:  messages2[i];
            } else {
                word = messages1[i] + messages2[i] + messages3[i];
            }
            if (word == null || "".equals(word)){
                word = " ";
            } else {
                word += " ";
            }
            sb.append(word);
        }
        return sb.toString();
    }

    public Double[] trilateration(double x0, double y0, double r0,
                                   double x1, double y1, double r1,
                                   double x2, double y2, double r2) throws RuntimeException{

        Point p1=new Point(x0,y0,r0);
        Point p2=new Point(x1,y1,r1);
        Point p3=new Point(x2,y2,r2);
        double[] compute = Trilateration.Compute(p1, p2, p3);
        if (compute != null){
            return new Double[] {new Double(compute[0]), new Double(compute[1])};
        } else{
            return null;
        }

    }

    /**
     * Function copied from https://stackoverrun.com/es/q/5384586
     */
    public Double[] calculateThreeCircleIntersection(double x0, double y0, double r0,
                                                     double x1, double y1, double r1,
                                                     double x2, double y2, double r2) throws RuntimeException
    {
        double a, dx, dy, d, h, rx, ry;
        double point2_x, point2_y;

       /* dx and dy are the vertical and horizontal distances between
       * the circle centers.
       */
        dx = x1 - x0;
        dy = y1 - y0;

       /* Determine the straight-line distance between the centers. */
        d = Math.sqrt((dy*dy) + (dx*dx));

       /* Check for solvability. */
        if (d > (r0 + r1))
        {
           /* no solution. circles do not intersect. */
            throw new CalculationException();
        }
        if (d < Math.abs(r0 - r1))
        {
           /* no solution. one circle is contained in the other */
            throw new CalculationException();
        }

       /* 'point 2' is the point where the line through the circle
       * intersection points crosses the line between the circle
       * centers.
       */

       /* Determine the distance from point 0 to point 2. */
        a = ((r0*r0) - (r1*r1) + (d*d)) / (2.0 * d) ;

       /* Determine the coordinates of point 2. */
        point2_x = x0 + (dx * a/d);
        point2_y = y0 + (dy * a/d);

       /* Determine the distance from point 2 to either of the
       * intersection points.
       */
        h = Math.sqrt((r0*r0) - (a*a));

       /* Now determine the offsets of the intersection points from
       * point 2.
       */
        rx = -dy * (h/d);
        ry = dx * (h/d);

       /* Determine the absolute intersection points. */
        double intersectionPoint1_x = point2_x + rx;
        double intersectionPoint2_x = point2_x - rx;
        double intersectionPoint1_y = point2_y + ry;
        double intersectionPoint2_y = point2_y - ry;


       /* Lets determine if circle 3 intersects at either of the above intersection points. */
        dx = intersectionPoint1_x - x2;
        dy = intersectionPoint1_y - y2;
        double d1 = Math.sqrt((dy*dy) + (dx*dx));

        dx = intersectionPoint2_x - x2;
        dy = intersectionPoint2_y - y2;
        double d2 = Math.sqrt((dy*dy) + (dx*dx));

        if(Math.abs(d1 - r2) < EPSILON) {
            log.debug("INTERSECTION Circle1 AND Circle2 AND Circle3:", "(" + intersectionPoint1_x + "," + intersectionPoint1_y + ")");
            return new Double[] {intersectionPoint1_x,intersectionPoint1_y};
        }
        else if(Math.abs(d2 - r2) < EPSILON) {
            log.debug("INTERSECTION Circle1 AND Circle2 AND Circle3:", "(" + intersectionPoint2_x + "," + intersectionPoint2_y + ")"); //here was an error
            return new Double[] {intersectionPoint1_x,intersectionPoint1_y};

        }
        else {
            throw new CalculationException();
        }
    }

}
