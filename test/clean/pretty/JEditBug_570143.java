package pretty;

public class JEditBug_570143 {

    void toto() {
        if (a) {
        }
        else 
        
        // a comment
        
        if (b) {
            toto();
        }
        
        if (c) {
        }
        else 
            if (d) {
            toto();
        }
    }
}

