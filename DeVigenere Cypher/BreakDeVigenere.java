import java.util.Scanner;

public class BreakDeVigenere{

  // Used to find the index of coincidence
  public static double ICCalc(String cipherTxt){
    int charFreq[] = new int[26];
    for(int i = 0; i < 26; i++)charFreq[i] = 0;

    // Calculating character frequency
    int n = cipherTxt.length();
    for(int i = 0; i < cipherTxt.length(); i++){
      charFreq[cipherTxt.charAt(i) - 'A']++;
    }

    //Calculating sum
    double icFirst = 0;
    for(int i = 0; i < 26; i++){
      icFirst += (charFreq[i] * (charFreq[i] - 1));
    }

    // Divide sum by N(N-1)
    icFirst = icFirst/(cipherTxt.length() * (cipherTxt.length() - 1));
    return icFirst;
  }

  //  Used to find the key period of a cipher
  public static int findKeyPeriod(double ic){
    double icValue = 1;
    double icDiff = 1;
    int d = 1;
    for(int i = 1; ic <= icValue; i++){
      double val1 = ( ((double)1/i) * ( ((double)26 - i)/(double)25 ) * (double)0.066);
      double val2 = ( ((i - (double)1)/i) * ( (double)26/(double)25 ) * (double)0.038);
      icValue = val1 + val2;

      if( ic >= icValue && icDiff > java.lang.Math.abs(icValue - ic)){
        icDiff = java.lang.Math.abs(icValue - ic);
        d = i;
      }
    }
    return d;
  }

  public static double[][] findOccurenceFreq(int d, double [][]cipherFreq, String cipherTxt){
      int index = 0;
      for(int j = 0; j < (cipherTxt.length()/d) + 1; j++){
        for(int i = 0; i < d; i++){
          if(index < cipherTxt.length()){
            cipherFreq[i][cipherTxt.charAt(index) - 'A']++;
            index++;
          }
        }
      }
      for(int j = 0; j < 26; j++){
        for(int i = 0; i < d; i++){
          cipherFreq[i][j] = (double)cipherFreq[i][j]/cipherTxt.length();
        }
      }
      return cipherFreq;
  }

  public static int[] findKey(int d, double cipherFreqs[][], int cMax[]){
    double plainTextFreq[] = {0.078, 0.013, 0.029, 0.041, 0.131, 0.029, 0.014, 0.059, 0.068, 0.002, 0.004, 0.036, 0.026, 0.073, 0.082, 0.022, 0.001, 0.066, 0.065, 0.09, 0.028, 0.01, 0.015, 0.003, 0.015, 0.001};

    double c[][] = new double[d][26];
    for(int j = 0; j < d; j++){
      for(int k = 0; k < 26; k++){
        for(int i = 0; i < 26; i++){
          c[j][k] += cipherFreqs[j][i] * plainTextFreq[((i+k)%26)];
        }
      }
    }

    for(int i = 0; i < 3; i++){
      cMax[i] = 0;
      for(int j = 0; j < 26; j++){
        if(c[i][cMax[i]] < c[i][j]){
          cMax[i] = j;
        }
      }
    }
    return cMax;
  }

  public static void main(String []args){
    Scanner sc = new Scanner(System.in);

    // getting the inputed cipher
    System.out.println("Input Cipher Text: ");
    String cipherTxt = sc.next();
    cipherTxt = cipherTxt.toUpperCase();

    // Find the index of coincidence
    double ic = ICCalc(cipherTxt);

    // Find the key period
    int d = findKeyPeriod(ic);

    // finding the ciphers occurence frequency
    double cipherFreqs[][] = new double[d][26];
    cipherFreqs = findOccurenceFreq(d, cipherFreqs, cipherTxt);


    int cMax[] = new int[d];
    cMax = findKey(d, cipherFreqs, cMax);


    System.out.println("\nDecrypted Text:");
    for(int i = 0; i < cipherTxt.length(); i++){
      char temp = (char)(cipherTxt.charAt(i) + cMax[i%d]);
      if(temp > 'Z'){
        temp = (char)(temp-26);
      }
      System.out.print(temp);
    }


  }
}
