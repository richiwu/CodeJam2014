import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
 
public class cropImage {
 
	static Rectangle clip;
	 
	public static void main(String args[]) throws Exception {
		String inputFileLocation = "C:/temp/cole.jpg";
		String outputFileLocation = "C:/temp/test.jpg";
		 
		System.out.println("Reading Original File : " + inputFileLocation);
		 
		BufferedImage originalImage = readImage(inputFileLocation);
		 
		/**
		* Image Cropping Parameters
		*/
		int cropHeight = 850;
		int cropWidth = 850;
		int cropStartX = 50;
		int cropStartY = 50;
		 
		BufferedImage processedImage = cropMyImage(originalImage, cropWidth, cropHeight, cropStartX, cropStartY);
		
		System.out.println("Writing the cropped image to: " + outputFileLocation);
		writeImage(processedImage, outputFileLocation, "jpg");
		System.out.println("...Done");
	}
	 
		private static void writeImage(BufferedImage processedImage, String outputFileLocation, String string) {
			BufferedImage image = processedImage;

			try {
//				String inputFileLocation = "";
//				URL url = new URL (inputFileLocation );
//				image = ImageIO.read(url);
				File f =  new File (outputFileLocation);
				//ImageIO.write(image, "gif", ));
				ImageIO.write(image, "jpg", f );
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	
	public static BufferedImage cropMyImage(BufferedImage img, int cropWidth,
	int cropHeight, int cropStartX, int cropStartY) throws Exception {
		BufferedImage clipped = null;
		Dimension size = new Dimension(cropWidth, cropHeight);
		 
		createClip(img, size, cropStartX, cropStartY);
		 
		try {
			int w = clip.width;
			int h = clip.height;
			 
			System.out.println("Crop Width " + w);
			System.out.println("Crop Height " + h);
			System.out.println("Crop Location " + "(" + clip.x + "," + clip.y
			+ ")");
			 
			clipped = img.getSubimage(clip.x, clip.y, w, h);
			 
			System.out.println("Image Cropped. New Image Dimension: "
			+ clipped.getWidth() + "w X " + clipped.getHeight() + "h");
		} catch (RasterFormatException rfe) {
			System.out.println("Raster format error: " + rfe.getMessage());
			return null;
		}
		return clipped;
	}
	 
	/**
	* This method crops an original image to the crop parameters provided.
	*
	* If the crop rectangle lies outside the rectangle (even if partially),
	* adjusts the rectangle to be included within the image area.
	*
	* @param img = Original Image To Be Cropped
	* @param size = Crop area rectangle
	* @param clipX = Starting X-position of crop area rectangle
	* @param clipY = Strating Y-position of crop area rectangle
	* @throws Exception
	*/
	private static void createClip(BufferedImage img, Dimension size, int clipX, int clipY) throws Exception {
		/**
		* Some times clip area might lie outside the original image,
		* fully or partially. In such cases, this program will adjust
		* the crop area to fit within the original image.
		*
		* isClipAreaAdjusted flas is used to denote if there was any
		* adjustment made.
		*/
		boolean isClipAreaAdjusted = false;
		 
		/**Checking for negative X Co-ordinate**/
		if (clipX < 0) {
			clipX = 0;
			isClipAreaAdjusted = true;
		}
		/**Checking for negative Y Co-ordinate**/
		if (clipY < 0) {
			clipY = 0;
			isClipAreaAdjusted = true;
		}
		 
		/**Checking if the clip area lies outside the rectangle**/
		if ((size.width + clipX) <= img.getWidth()
		&& (size.height + clipY) <= img.getHeight()) {
			 
			/**
			* Setting up a clip rectangle when clip area
			* lies within the image.
			*/
			 
			clip = new Rectangle(size);
			clip.x = clipX;
			clip.y = clipY;
		} else {
			 
				/**
				* Checking if the width of the clip area lies outside the image.
				* If so, making the image width boundary as the clip width.
				*/
			if ((size.width + clipX) > img.getWidth())
			size.width = img.getWidth() - clipX;
			 
			/**
			* Checking if the height of the clip area lies outside the image.
			* If so, making the image height boundary as the clip height.
			*/
			if ((size.height + clipY) > img.getHeight())
			size.height = img.getHeight() - clipY;
			 
			/**Setting up the clip are based on our clip area size adjustment**/
			clip = new Rectangle(size);
			clip.x = clipX;
			clip.y = clipY;
			 
			isClipAreaAdjusted = true;
		 
		}
			if (isClipAreaAdjusted)
			System.out.println("Crop Area Lied Outside The Image."
			+ " Adjusted The Clip Rectangle\n");
	}
	 
	/**
	* This method reads an image from the file
	*
	* @param fileLocation -- >
	*            eg. "C:/testImage.jpg"
	* @return BufferedImage of the file read
	*/
	public static BufferedImage readImage(String fileLocation) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(fileLocation));
			System.out.println("Image Read. Image Dimension: " + img.getWidth()
			+ "w X " + img.getHeight() + "h");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
}
 