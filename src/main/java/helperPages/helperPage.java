package helperPages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class helperPage {
	ExtentTest logger;

	public helperPage(ExtentTest logger) {
		this.logger = logger;
	}

	// Method to generate a random age
	public String generateRandomAge(int min, int max) {
		logger.log(LogStatus.INFO, "generate a random age between : " + min + " and " + max);
		Random random = new Random();
		int number = random.nextInt(max - min + 1) + min;
		String age = String.valueOf(number);
		return age;
	}

	// Method to generate a random job
	public String generateRandomJob() {
		String[] jobs = { "Software Engineer", "Data Scientist", "Product Manager", "DevOps Engineer", "Designer",
				"QA Engineer" };
		logger.log(LogStatus.INFO, "generate a random job  " );
		Random random = new Random();
		return jobs[random.nextInt(jobs.length)];
	}

	// Method to generate a random name
	public String generateRandomName() {
		String[] names = { "Ali", "Sara", "Hadi", "Karim", "Dina", "Yara" };
		logger.log(LogStatus.INFO, "generate a random name ");
		Random random = new Random();
		return names[random.nextInt(names.length)];
	}

	// method to validate date-time
	public boolean isValidDate(String dateTime) {
		try {
			logger.log(LogStatus.INFO, "vailidate the date time : " + dateTime);
			DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
			LocalDateTime.parse(dateTime, formatter);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}
}
