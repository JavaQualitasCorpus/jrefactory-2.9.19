// Annotation type declaration with defaults on some members
public @interface Reviewer {
    int    id();       // No default - must be specified in each annotation
    String synopsis(); // No default - must be specified in each annotation
    String engineer()  default "[unassigned]";
    String date()      default "[unimplemented]";
}
