public class Annotations {
   // Normal annotation
   @RequestForEnhancement(
       id       = 2868724,
       synopsis = "Provide time-travel functionality",
       engineer = "Mr. Peabody",
       date     = "4/1/2004"
   )
   public static void travelThroughTime(Date destination) { }

   // Marker annotation
   @Preliminary public class TimeTravel {
      public TimeTravel() {}
   }


   // Single-member annotation
   @Copyright("2002 Yoyodyne Propulsion Systems, Inc., All rights reserved.")
   public class OscillationOverthruster {
      public OscillationOverthruster() {}
   }

   //Example with array-valued single-member annotation:

   // Array-valued single-member annotation
   @Endorsers({"Children", "Unscrupulous dentists"})
   public class Lollipop {
      public Lollipop() {}
   }

   //Example with single-element array-valued single-member annotation (note that the curly braces are omitted):

   // Single-element array-valued single-member annotation
   @Endorsers("Epicurus") // comment on Endorsers line
   // comment between Endorsers and public
   public class Pleasure {
      public Pleasure() {}
   }

   //Example with complex annotation:

   // Single-member complex annotation
   @Author(@Name(first = "Joe", last = "Hacker"))
   public class BitTwiddle {
      public BitTwiddle() {}
   }

   //Here is an example of an annotation that takes advantage of default values:

   // Normal annotation with default values
   /* this RFE has been outstanding for ages */
   @RequestForEnhancement(
       /* see also 4561444 - balance trade deficit */
       id       = 4561414,
       synopsis = "Balance the federal budget" /* surprisingly easy to fix! */
       // end of RFE
   ) // after RequestForEnhancement
   @Author(
      //The author name
      @Name(first = "Joe", last = "Hacker") // joe did a good job here
   )
   // after Author
   public static void balanceFederalBudget() {
       throw new UnsupportedOperationException("Not implemented");
   }

   // Single-member annotation with Class member restricted by bounded wildcard
   // The annotation presumes the existence of this class.
   class GorgeousFormatter implements Formatter {
      GorgeousFormatter() { }
   }

   @PrettyPrinter(GorgeousFormatter.class)
   public class Petunia {
      Petunia() {}
   }


}
