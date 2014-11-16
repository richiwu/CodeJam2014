import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class splitAndAverage {
	
	private static imageToArray imageToArray = new imageToArray();
	private static int height = 243;
	private static int width = 320;
	
	public static void main(String[] args) throws IOException {
		
		String inputFileName = "1_2_.gif";
//		int period = 0;
//		for (int a=0; a<inputFileName.length(); a++) {
//			if (inputFileName.charAt(a) == '.') {
//				period = a;
//			}
//		}
		File inputFile = new File(inputFileName);
		int[][] outputArray;
		outputArray = imageToArray.convertImageToArray(inputFile);
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
				"data.txt", true)));
		
		int height10 = height/10;
		int width10 = width/10;
		
		int sum = 0;
		int average = 0;
//		int counter = 0; //counter for testing
		
		for (int i=0; i<10; i++) { //row fraction
			for (int x=0; x<10; x++) { //column fraction
				for (int j=i*height10; j<((i+1)*height10); j++) {
					for (int y=x*width10; y<((x+1)*width10); y++) {
						sum += Math.abs(outputArray[j][y]);
					}
				}
				average = sum/100;
//				counter++;
//				out.println(counter);
				out.println(inputFileName + "," + i + "," + x + "," + average);
				sum = 0;
				average = 0;
			}
		}
		
		for (int i=0; i<height; i++) {
			for (int j=0; j<width; j++) {
				System.out.print(outputArray[i][j]);
			}
			System.out.println();
		}
		
		out.close();
		
	}
}