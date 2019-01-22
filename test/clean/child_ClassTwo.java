package abstracter;

import java.util.Date;
import java.util.Calendar;

public abstract class ClassTwo extends ClassOne {
	public Date getTime() {
		return new Date();
	}
	public abstract Calendar getCalendar(Date time);
}
