package pretty;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
class Bug_465568 {

   class Inner1 {
      Inner1() {};
   }

   class Inner2 extends Inner1 {
     Inner2(Bug_465568 a) {
        a.super();
     }
   }
}

