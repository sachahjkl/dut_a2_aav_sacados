package sacADos;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeRecord {
	private static String file_name = "execution_time.txt";

	public static void addTime(String methode, long value) throws IOException {
		PrintWriter pw = new PrintWriter(new FileWriter(file_name, true));
		String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
		pw.println(methode + ": " + value + "ns " + timeStamp);
		pw.close();
	}
}
