import javax.imageio.ImageIO;
import javax.swing.*;

import java.io.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;

import com.sun.image.codec.jpeg.*;

public class ImageComparator {
	
	private BufferedImage imgA = null;
	private BufferedImage imgB = null;
	private int horz = 5;  // # horizontal blocks
	private int vert = 5;  // # vertical blocks
	private int sens = 1;  // sensitivity
	
	public static void main(String[] args){
	
		
		
	}
	
	
	public ImageComparator(String file1, String file2) {
		String temp1 = convertToJPG(file1);
		String temp2 = convertToJPG(file2);
		this.imgA = imageToBufferedImage(loadJPG(temp1));
		this.imgB = imageToBufferedImage(loadJPG(temp2));
	}
	public ImageComparator(String file1) {
		String temp = convertToJPG(file1);
		System.out.println("conv : " + temp);
		this.imgA = imageToBufferedImage(loadJPG(temp));
	}

	public boolean compare(){
		
		
		scaleImages();
		boolean match = true;
		
		imgA = imageToBufferedImage(GrayFilter.createDisabledImage(imgA));
		imgB = imageToBufferedImage(GrayFilter.createDisabledImage(imgB));
		int vertblocks = (int)(imgA.getWidth() / vert);
		int horzblocks = (int)(imgA.getHeight() / horz);
		
		for (int i = 0; i < vert; i++){
			for (int j = 0; j < horz; j++){
				int brightA = getAverageBrightness(imgA.getSubimage(j*horz, i*vert, horz - 1, vert - 1));
				int brightB = getAverageBrightness(imgB.getSubimage(j*horz, i*vert, horz - 1, vert - 1));
				int diff = Math.abs(brightA - brightB);
				if (diff > sens){
					match = false;			
				}
			}	
		}
		return match;
	}
	
	public static String convertToJPG(String inputFile){
		File input = new File(inputFile);  
		BufferedImage im = null;
		try {
			im = ImageIO.read(input);
		} catch (IOException e) {
			System.out.println("Error converting gif to jpg: error reading input");
			e.printStackTrace();
		} 
		String fileOutput = inputFile.replaceAll(".gif", ".jpg");
		File output = new File(fileOutput);
		try {
			ImageIO.write(im, "jpg", output);
		} catch (IOException e) {
			System.out.println("Error converting gif to jpg: error writing to output");
			e.printStackTrace();
		} 
		return output.getPath();
	}
	public void setComparisonImage(String fileName){
		String temp = convertToJPG(fileName);
		this.imgB = imageToBufferedImage(loadJPG(temp));
	}
	protected void scaleImages(){
		
		 int imgAw = imgA.getWidth();
		 int imgAh = imgA.getHeight();
		 int imgBw = imgB.getWidth();
		 int imgBh = imgB.getHeight();
		 
		 double scaleX = (double)imgAw/imgBw;
		 double scaleY = (double)imgAh/imgBh;
		 
		 AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
		 AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);
		 
		 imgB = bilinearScaleOp.filter( imgB, new BufferedImage(imgAw, imgAh, imgB.getType()));
		
		
		
		
		
		
	}	
	protected int getAverageBrightness(BufferedImage img) {
		Raster r = img.getData();
		int total = 0;
		for (int y = 0; y < r.getHeight(); y++) {
			for (int x = 0; x < r.getWidth(); x++) {
				total += r.getSample(r.getMinX() + x, r.getMinY() + y, 0);
			}
		}
		return (int)(total / ((r.getWidth()/10)*(r.getHeight()/10)));
	}
	private static BufferedImage imageToBufferedImage(Image img) {
		BufferedImage buffim = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = buffim.createGraphics();
		g2.drawImage(img, null, null);
		return buffim;
	}	
	private static Image loadJPG(String filename) {
		FileInputStream input = null;
		try { 
			input = new FileInputStream(filename);
		} catch (java.io.FileNotFoundException io) { 
			System.out.println("File Not Found"); 
		}
		JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(input);
		BufferedImage bi = null;
		try { 
			bi = decoder.decodeAsBufferedImage(); 
			input.close(); 
		} catch (java.io.IOException io) {
			System.out.println("IOException");
		}
		return bi;
	}
	
}
