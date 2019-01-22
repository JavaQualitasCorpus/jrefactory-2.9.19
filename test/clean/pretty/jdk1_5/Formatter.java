// Annotation type declaration with bounded wildcard to restrict Class annotation


/**
 *  The annotation type declaration below presumes the existence of this interface, which describes a formatter for Java
 *  programming language source code.
 *
 * @author    Chris Seguin
 */
// before
@InterfaceAnnotation1 // online 1
// after 1
@InterfaceAnnotation2 // online 2
// after 2
public interface Formatter {
   //before
   @FieldAnnotation1 //online 1
   //after 1
   @FieldAnnotation2 //online 2
   //after 2
   private int x = 10;


   /**
    *  Description of the Method
    *
    * @param  argv  Description of Parameter
    */
   public void main( /*before */ @readonly /*after*/ String[] argv) {
      //before
      @LocalVarAnnotation1 //online 1
      //after 1
      @LocalVarAnnotation2 //online 2
      //after 2
      String name = argv[0];
      int y = 0;
   }

}

