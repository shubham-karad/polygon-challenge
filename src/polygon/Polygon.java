package polygon;

public class Polygon {

	 
    static float EXTREME = 1000; 		// extreme point
  
    static class Point  
    { 
        double x; 
        double y;
  
        public Point(double x, double y) 
        { 
            this.x = x; 
            this.y = y; 
        }
    }; 
  
    
    static boolean checkIfPointLiesOnSegment(Point p, Point q, Point r)  // check if q lies on line pr
    { 
        if (q.x <= ((p.x > r.x)? p.x : r.x) && 
            q.x >= ((p.x < r.x)? p.x : r.x) && 
            q.y <= ((p.y > r.y)? p.y : r.y) && 
            q.y >= ((p.y < r.y)? p.y : r.y)) 
        { 
            return true; 
        } 
        return false; 
    } 
  
    
    
    static int orientationOfPointComparedToSidesOfPolygon(Point p, Point q, Point r)  // orientation of point q wrt line pr 
    { 
    	double crossProduct = (p.x-q.x) * (r.y - q.y)      // cross product of qp and qr
                - (r.x - q.x) * (p.y - q.y); 
  
        if (crossProduct == 0)  
        { 
            return 0; // collinear 
        } 
        
        int orientation = (crossProduct > 0) ? 1 : 2;  // 1 --> clockwise 
        return orientation ;
    } 
    
    
    
    
    static boolean doLineSegmentsIntersect(Point p1, Point q1, Point p2, Point q2)    // To check if line segment 'p1q1' and 'p2q2' intersect.
    { 
       
        int o1 = orientationOfPointComparedToSidesOfPolygon(p1, q1, p2);      // orientation of p2 wrt line p1q1
        int o2 = orientationOfPointComparedToSidesOfPolygon(p1, q1, q2); 	  // orientation of q2 wrt line p1q1
        int o3 = orientationOfPointComparedToSidesOfPolygon(p2, q2, p1); 	  // orientation of p1 wrt line p2q2
        int o4 = orientationOfPointComparedToSidesOfPolygon(p2, q2, q1); 	  // orientation of q1 wrt line p2q2
  
        // General case 
        if (o1 != o2 && o3 != o4) 
        { 
            return true; 
        } 
  
        // Special Cases 
        if (o1 == 0 && checkIfPointLiesOnSegment(p1, p2, q1))   // if p1, q1 and p2 are colinear and p2 lies on p1q1
        { 
            return true; 
        } 
  
        if (o2 == 0 && checkIfPointLiesOnSegment(p1, q2, q1))  // if p1, q1 and q2 are colinear and q2 lies on p1q1
        { 
            return true; 
        } 
   
        if (o3 == 0 && checkIfPointLiesOnSegment(p2, p1, q2)) // if p2, q2 and p1 are colinear and p1 lies on p2q2
        { 
            return true; 
        } 
  
        if (o4 == 0 && checkIfPointLiesOnSegment(p2, q1, q2)) // if p2, q2 and q1 are colinear and q1 lies on p2q2
        { 
            return true; 
        } 
  
        return false;  
    } 
  
    
    
    
    static boolean isPointInsidePolygon(Point polygon[], Point p) //Returns true if the point p lies inside the polygon[]
    { 
        int n = polygon.length;		// number of vertices
        
        Point extreme = new Point(EXTREME, p.y); 				// Create a extreme point to create a line segment from p to extreme  
        
        int count = 0;
        for(int i=0; i<n; i++)				// Count intersections of the above line with sides of polygon
        {
        	int nextVertex = i+1;
        	
        	if(nextVertex == n) nextVertex=0;
        	
        	 if (doLineSegmentsIntersect(polygon[i], polygon[nextVertex], p, extreme))  
               { 
                    
                   if (orientationOfPointComparedToSidesOfPolygon(polygon[i], p, polygon[nextVertex]) == 0)  //If the point 'p' is Collinear with side of polygon from point 'i' to point 'nextVertex'
                   { 
                	   boolean doesPointLie = checkIfPointLiesOnSegment(polygon[i], p, polygon[nextVertex]);    // check if the point lies on  
                       return doesPointLie;    
                   } 
     
                   count++; // count the number of intersections with a particular side of polygon
               }
        	 
        }
        
        int remainder = count % 2;
        
        if(remainder == 1) 		// Return true if count is odd, false otherwise
        {	return true;	
        }	
        else 
        {	return false;
        }
  
    } 
    
    
  
    // Driver Code 
    public static void main(String[] args)  
    { 
    	Point polygon1[] = {new Point(1, 0), 
                new Point(8, 3),  
                new Point(8, 8),  
                new Point(1, 5)}; 

    	Point p1 = new Point(3, 5); 
    	if (isPointInsidePolygon(polygon1, p1)) 
    	{ 
    		System.out.println("True"); 
    	}  
    	else 
    	{ 
    		System.out.println("False"); 
    	} 
    	
    	
    
    	Point polygon2[] = {new Point(-3, 2), 
                            new Point(-2, -0.8),  
                            new Point(0, 1.2),  
                            new Point(2.2, 0),
                            new Point(2,4.5)}; 
         
        Point p2 = new Point(0, 0); 
        if (isPointInsidePolygon(polygon2, p2)) 
        { 
            System.out.println("True"); 
        }  
        else 
        { 
            System.out.println("False"); 
        } 
       
       
        
    } 

}
