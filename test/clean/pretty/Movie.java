//  Refactoring, a First Example, step1, (~p5)

import java.util.*;

public class Movie {
  public static final int CHILDRENS = 2;
  public static final int REGULAR = 0;
  public static final int NEW_RELEASE = 1;

  private String _title;        // 名稱
  private int _priceCode;       // 價格

  public Movie(String title, int priceCode){
      _title = title;
      _priceCode = priceCode;
  }

   public int getPriceCode() {
      return _priceCode;
  }

  public void setPriceCode(int arg) {
      _priceCode = arg;
  }

  public String getTitle() {
      return _title;
  }

  // jjhou add
  public static void main(String[] args) {
      System.out.println("Refactoring, a First Example, step1");

      Movie m1 = new Movie("Seven", Movie.NEW_RELEASE);
      Movie m2 = new Movie("Terminator", Movie.REGULAR);
      Movie m3 = new Movie("Star Trek", Movie.CHILDRENS);

      Rental r1 = new Rental(m1, 4);
      Rental r2 = new Rental(m1, 2);
      Rental r3 = new Rental(m3, 7);
      Rental r4 = new Rental(m2, 5);
      Rental r5 = new Rental(m3, 3);

      Customer c1 = new Customer("jjhou");
      c1.addRental(r1);
      c1.addRental(r4);

      Customer c2 = new Customer("gigix");
      c2.addRental(r1);
      c2.addRental(r3);
      c2.addRental(r2);

      Customer c3 = new Customer("jiangtao");
      c3.addRental(r3);
      c3.addRental(r5);

      Customer c4 = new Customer("yeka");
      c4.addRental(r2);
      c4.addRental(r3);
      c4.addRental(r5);

      System.out.println(c1.statement());
      System.out.println(c2.statement());
      System.out.println(c3.statement());
      System.out.println(c4.statement());
  }
}

class Rental {
  private Movie _movie;         // 影片
  private int _daysRented;              // 租期

  public Rental(Movie movie, int daysRented) {
    _movie = movie;
    _daysRented = daysRented;
  }

  public int getDaysRented() {
    return _daysRented;
  }

  public Movie getMovie() {
    return _movie;
  }
}

class Customer {
  private String _name;                                 // 姓名
  private Vector _rentals = new Vector();               // 租借記錄

  public Customer(String name) {
      _name = name;
  }

  public void addRental(Rental arg) {
      _rentals.addElement(arg);
  }

  public String getName() {
      return _name;
  }

  public String statement() {
    double totalAmount = 0;                     // 總消費金額
    int frequentRenterPoints = 0;       // 常客積點
    Enumeration rentals = _rentals.elements();
    String result = "Rental Record for " + getName() + "\n";

    while(rentals.hasMoreElements()){
      double thisAmount = 0;
      Rental each = (Rental) rentals.nextElement(); // 取得一筆租借記錄

      //determine amounts for each line
      switch(each.getMovie().getPriceCode()){   // 取得影片出租價格
        case Movie.REGULAR:                     // 普通片
          thisAmount += 2;
          if(each.getDaysRented()>2)
            thisAmount += (each.getDaysRented()-2)*1.5;
          break;

        case Movie.NEW_RELEASE:         // 新片
          thisAmount += each.getDaysRented()*3;
          break;

        case Movie.CHILDRENS:           // 兒童片
          thisAmount += 1.5;
          if(each.getDaysRented()>3)
            thisAmount += (each.getDaysRented()-3)*1.5;
          break;
      }

      // add frequent renter points（累加 常客積點）
      frequentRenterPoints ++;
      // add bonus for a two day new release rental
      if ((each.getMovie().getPriceCode() == Movie.NEW_RELEASE) &&
         each.getDaysRented() > 1)
        frequentRenterPoints ++;

      // show figures for this rental（顯示此筆租借資料）
      result += "\t" + each.getMovie().getTitle() + "\t" +
          String.valueOf(thisAmount) + "\n";
      totalAmount += thisAmount;
    }

    // add footer lines（結尾列印）
    result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
    result += "You earned " + String.valueOf(frequentRenterPoints) +
            " frequent renter points";
    return result;
  }
}
