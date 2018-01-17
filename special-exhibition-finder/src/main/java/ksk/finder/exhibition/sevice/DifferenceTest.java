package ksk.finder.exhibition.sevice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DifferenceTest {
	public static void main(String[] args) {
		String period = "2018.01.16";

		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
			Date currentDate = new Date(cal.getTimeInMillis());
			Date periodDate = sdf.parse(period);
			long closingDate = currentDate.getTime() - periodDate.getTime();

			long calDateDays = closingDate / (24 * 60 * 60 * 1000);
			calDateDays = Math.abs(calDateDays) + 1;

			System.out.println(calDateDays);
		} catch (ParseException e) {
			System.out.println("Fail");
		}
	}
}
