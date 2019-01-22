/**
 *  Description of the Class
 *
 * @author    Mike Atkinson
 */
public class Annotations {
   /**
    *  Description of the Method
    *
    * @param  destination  Description of Parameter
    */
   @RequestForEnhancement(
      id=2868724,
      synopsis="Provide time-travel functionality",
      engineer="Mr. Peabody",
      date="4/1/2004")
   public static void travelThroughTime(Date destination) { }


   /**  Description of the Method */
   @RequestForEnhancement(
      /* see also 4561444 - balance trade deficit */
      id=4561414,
      synopsis="Balance the federal budget")   // after RequestForEnhancement
   @Author(
      //The author name
      @Name(first="Joe", last="Hacker") )
   // after Author
   public static void balanceFederalBudget() {
      throw new UnsupportedOperationException("Not implemented");
   }


   // Marker annotation
   /**
    *  Description of the Class
    *
    * @author    Mike Atkinson
    */
   @Preliminary
   public class TimeTravel {
      /**  Constructor for the TimeTravel object */
      public TimeTravel() { }
   }


   // Single-member annotation
   /**
    *  Description of the Class
    *
    * @author    Mike Atkinson
    */
   @Copyright("2002 Yoyodyne Propulsion Systems, Inc., All rights reserved.")
   public class OscillationOverthruster {
      /**  Constructor for the OscillationOverthruster object */
      public OscillationOverthruster() { }
   }

   //Example with array-valued single-member annotation:

   // Array-valued single-member annotation
   /**
    *  Description of the Class
    *
    * @author    Mike Atkinson
    */
   @Endorsers({"Children", "Unscrupulous dentists"})
   public class Lollipop {
      /**  Constructor for the Lollipop object */
      public Lollipop() { }
   }

   //Example with single-element array-valued single-member annotation (note that the curly braces are omitted):

   // Single-element array-valued single-member annotation
   /**
    *  Description of the Class
    *
    * @author    Mike Atkinson
    */
   @Endorsers("Epicurus")   // comment on Endorsers line
   // comment between Endorsers and public
   public class Pleasure {
      /**  Constructor for the Pleasure object */
      public Pleasure() { }
   }

   //Example with complex annotation:

   // Single-member complex annotation
   /**
    *  Description of the Class
    *
    * @author    Mike Atkinson
    */
   @Author(@Name(first="Joe", last="Hacker") )
   public class BitTwiddle {
      /**  Constructor for the BitTwiddle object */
      public BitTwiddle() { }
   }


   /**
    *  Description of the Class
    *
    * @author    Mike Atkinson
    */
   @PrettyPrinter(GorgeousFormatter.class)
   public class Petunia {
      /**  Constructor for the Petunia object */
      Petunia() { }
   }


   /**
    *  Description of the Class
    *
    * @author    Mike Atkinson
    */
   //check that the Annotation may have conditional expressions
   @XXX(1 == 2)
   public class ConditionalExpression {
   }


   // Single-member annotation with Class member restricted by bounded wildcard
   // The annotation presumes the existence of this class.
   /**
    *  Description of the Class
    *
    * @author    Mike Atkinson
    */
   class GorgeousFormatter implements Formatter {
      /**  Constructor for the GorgeousFormatter object */
      GorgeousFormatter() { }
   }

}

