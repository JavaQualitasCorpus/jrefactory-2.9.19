package method;

public class RenameMethodTest {

	public RenameMethodTest() {
	}

	public RenameMethodTest(int x) {
      code();
      code(x);
      height();
      height(x);
	}


	public void withinMethod(int x) {
		code();
		code(x);
		height();
		height(x);
	}

   public int code() {
   }

   public int code(int x) {
   }

   public static int height() {
   }

   public static void height(int x) {
   }

   protected void protectedMethod(int x) {
   }

   void packageProtectedMethod(int x) {
   }

   private void privateMethod(int x) {
   }


}
