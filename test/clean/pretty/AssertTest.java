public class AssertTest {
    public AssertTest() {
        int x = 1;
        assert x > 0;
        assert (x > 0);
        assert x > 0 : "test";
        assert (x > 0) : "test";
    }
}

