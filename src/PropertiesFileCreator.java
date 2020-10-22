
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Scanner;

public class PropertiesFileCreator {
  public static void main(String[] args) {

	Properties prop = new Properties();
	OutputStream output = null;

	try {

		output = new FileOutputStream("config.properties");
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduza o endereco ip que vai usar");
		String addr=sc.nextLine();
		System.out.println("Introduza a porta que vai usar");
		String pt=sc.nextLine();
		// set the properties value
		prop.setProperty("endereco", addr);
		prop.setProperty("porta", pt);

		// save properties to project root folder
		prop.store(output, null);

	} catch (IOException io) {
		io.printStackTrace();
	} finally {
		if (output != null) {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
  }
}