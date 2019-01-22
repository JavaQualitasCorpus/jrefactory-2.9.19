package jdk1_5;

public class Enums {

    public enum Quarks { up, down, top, bottom, charm, strange };

    public enum Coin {
        penny(1), nickel(5), dime(10), quarter(25);
    
        Coin(int value) { this.value = value; }
    
        private final int value;
    
        public int value() { return value; }
    }

    public class CoinTest {
        public void CoinTest() {
            X:  // fixed ":" after labels
            for (Iterator<Coin> i = Coin.VALUES.iterator(); i.hasNext(); ) {
                Coin c = i.next();
                System.out.println(c + ":   \t"+ c.value() +"c \t" + color(c));
            }
        }
    
        private enum CoinColor { copper, nickel, silver }
    
        private static CoinColor color(Coin c) {
            if (c == null)
                throw new NullPointerException();
    
            switch(c) {
              case Coin.penny:
                return CoinColor.copper;
              case Coin.nickel:
                return CoinColor.nickel;
              case Coin.dime:
                return CoinColor.silver;
              case Coin.quarter:
                return CoinColor.silver;
            }
    
            throw new AssertionError("Unknown coin: " + c);
        }
    }
    
    public static void bar(Object[] x) {
        for (Object o : x) {
            System.out.println(o);
        }
        
        int [][] y;
        for (int[] z : y) {
           for (int i : z) {
              System.out.println(i);
           }
        }
    }


    public static void main(String[] argv) {
        CoinTest();
        bar( 1,"a",'z');
    }

}

