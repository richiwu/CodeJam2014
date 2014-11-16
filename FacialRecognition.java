import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStreamReader;


public class FacialRecognition {
	private final String databaseFolderLocation;
	private ImageComparator comparator;
	
	
	public FacialRecognition(String databaseFolder){
		this.databaseFolderLocation = databaseFolder;	
	}
	
	public String recognize(String fileURL){
		comparator = new ImageComparator(fileURL);
		File fl = new File(databaseFolderLocation);
		File[] files = fl.listFiles();
		
		for (File file : files) {
			System.out.println("file : " + file.getName());
			comparator.setComparisonImage(file.getAbsolutePath());
			if(comparator.compare()){
				return file.getName();
			}
		}	
		return null;
	}
	
	
	
	public static void main(String[] args) throws IOException{
		 FacialRecognition fr = new FacialRecognition("C:\\Users\\Torbir\\Desktop\\codejam\\training dataset\\training dataset");
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		 System.out.println("Please enter the file url of the picture to be recognized (must be gif) : ");
	     String fileName = br.readLine();
	     System.out.println(fr.recognize(fileName));
	      
	      
		
	
	}
		
		
		
	
	
	
}
