/**
 * A person's name.  This annotation type is not designed to be used
 * directly to annotate program elements, but to define members
 * of other annotation types.
 */
public @interface Name {
    String first();
    String last();
}
