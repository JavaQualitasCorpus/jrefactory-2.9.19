package test;

import java.io.*;

public class FTest extends Object implements Serializable
{
	/**  Here is a method with an @ejb:artificial artificial tag and an @real tag in the middle
	     of an extended comment.  Part of this will be broken out<p> @since this is what
	     we are trying to test */
	public void firstMethod()
	{
		System.out.println("Stuff here");
		System.out.println("More stuff here");
	}

	public void secondMethod()
	{

		System.out.println("Second method");
		System.out.println("Additional data here");
	}

	public void thirdMethod()
	{
		System.out.println("Simple simon");
	}

	public void fourthMethod()
	{

		System.out.println("More than one");
	}
}
