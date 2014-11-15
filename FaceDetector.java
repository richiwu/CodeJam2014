import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;
 


public class FaceDetector {
	
	public static String convertToJPG(String inputFile){
		File input = new File(inputFile);  
		BufferedImage im = null;
		try {
			im = ImageIO.read(input);
		} catch (IOException e) {
			System.out.println("Error converting gif to jpg: error reading input");
			e.printStackTrace();
		} 
		String fileOutput = inputFile.replaceAll("gif", "jpg");
		File output = new File(fileOutput);
		try {
			ImageIO.write(im, "jpg", output);
		} catch (IOException e) {
			System.out.println("Error converting gif to jpg: error writing to output");
			e.printStackTrace();
		} 
		
		
		
		return output.getPath();
	}
	
	public  static String getFace(String inputFile) throws IOException{
		
		
		String inFile = convertToJPG(inputFile);
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		System.out.println("\nRunning FaceDetector");
		
		CascadeClassifier faceDetector = new CascadeClassifier();
		faceDetector.load("C:\\Users\\Torbir\\lib\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt.xml"); // change this
        Mat image = Highgui.imread(inFile);
        
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);
 
        System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
        Mat croppedImage = null;
        for (Rect rect : faceDetections.toArray()) {
        
            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
            croppedImage = new Mat ( image, rect );
        }
		
        String filename = inFile.replaceAll(".jpg","-rect.jpg");
        System.out.println(String.format("Writing %s", filename));
        Highgui.imwrite(filename, croppedImage);
        
        
		return filename;
	}
	
	
	public static void main(String[] args) throws IOException{
		//System.out.println(convertToJPG("2_2_.gif"));
		
		String a = getFace("2_2_.gif");
		System.out.println(a);
		
	}
	
	
}
