/**
 * Describes the "request-for-enhancement" (RFE) that led to the presence of 
 * the annotated API element.
 */
public @interface RequestForEnhancement {
    int    id();        // Unique ID number associated with RFE
    String synopsis();  // Synopsis of RFE
    String engineer();  // Name of engineer who implemented RFE
    String date();      // Date RFE was implemented
}
