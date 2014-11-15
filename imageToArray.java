import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class imageToArray {
	
	private static int height;
	private static int width;
	private static int[][] outputArray;
	
	public static void main(String[] args) throws IOException {
		String inputFileName = "1_2_.gif";
		File inputFile = new File(inputFileName);
		outputArray = convertImageToArray(inputFile);
		
		for (int i=0; i<height; i++) {
			for (int j=0; j<width; j++) {
				System.out.print(outputArray[i][j]);
			}
			System.out.println();
		}
	}
	
	public static int[][] convertImageToArray(File inputFile) throws IOException {
		BufferedImage image = ImageIO.read(inputFile);
		width = image.getWidth();
		height = image.getHeight();
	    int[][] result = new int[height][width];
	    
	    for (int i=0; i<height; i++) {
	    	for (int j=0; j<width; j++) {
	    		result[i][j] = image.getRGB(j, i);
	    	}
	    }
	    return result;
    }
	
}