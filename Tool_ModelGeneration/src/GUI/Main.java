package GUI;

import java.io.File;
import java.io.IOException;

public class Main {

	private static FirstInputWindow window;
	
	public static void main (String[] args) throws IOException {
		
		System.out.print("Hi!");
		//window = new SecondWindow();
		//window = new FirstInputWindow();
		window = new FirstInputWindow();
	}
}