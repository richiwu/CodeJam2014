import java.util.Arrays;

public class ImageCompare2 {
	
	public static int findLargest(){
		int [] A = new int[8], B = new int [8], C = new int [8], D = new int [8], E = new int [8],
				F = new int [8], G = new int [8], H = new int [8], I = new int [8], J = new int [8],
				K = new int [8], L = new int [8], M = new int [8], N = new int [8], O = new int [8];
		
		int sum[] = new int [15];
		
		for(int i = 0; i< 3; i++){
			sum[0] += A[i];
			sum[1] += B[i];
			sum[2] += C[i];
			sum[3] += D[i];
			sum[4] += E[i];
			sum[5] += F[i];
			sum[6] += G[i];
			sum[7] += H[i];
			sum[8] += I[i];
			sum[9] += J[i];
			sum[10] += K[i];
			sum[11] += L[i];
			sum[12] += M[i];
			sum[13] += N[i];
			sum[14] += O[i];
			
		}
		
		Arrays.sort(sum);
		/*for(int k = 0; k< 15; k++){
			System.out.println(sum[k]);
		}*/
		return sum[14];
		
	}
	
	public static void main(String[]args){
		
		System.out.print(findLargest());
	}
}