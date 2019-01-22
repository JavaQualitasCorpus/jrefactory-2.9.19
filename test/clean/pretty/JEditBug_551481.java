package pretty;

public class JEditBug_551481 {

   /**
    * Constructor for the AdapterInfo object
    *
    */
   //public JEditBug_551481() { }


   /**
    * Construct an instance of this class with
      the given field values.
    *
    * @param protocolName The name of the
             protocol the adapter supports (i.e., FTP).
    * @param adapter A reference to the
             adapter itself (i.e., this).
    * @param isServer True if the adapter
             is a server adapter, false if not.
    */
   public JEditBug_551481(
      String protocolName,
      AdapterActionListener adapter,
      boolean isServer,
      int processId) {}
}

