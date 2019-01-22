package pretty;

/**
 * 
 */
class Bug_641473  {

   /**
    * Wrong ordering of "@param" in constructor
    *@param o blah blah blah 
    */
	Bug_641473(String s, Object o) {
	}
   /**
    * Wrong ordering of "@param" in method
    *@param o blah blah blah 
    */
	method(String s, Object o) {
	}
}
