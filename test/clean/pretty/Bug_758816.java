package pretty;

class Bug_758816 {

   Bug_758816() {
       boolean negative = true;
       Long result = new Long(10);
       result = negative ? new Long((long)-result.longValue()) : result; 
   }
} 
