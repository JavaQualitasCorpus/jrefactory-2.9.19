public class CopyConstructor {
   private int x;
   public CopyConstructor(CopyConstructor cc) {
      this.x = cc.x;
   }
}
      
