package core.vigenere;

public class Vigenere {
	//mode 0 =  26 character (a - z), mode 1 = 256 ASCII
	private int mode;
	//constructor
	public Vigenere() {
		mode = 1;
	}
	
	public Vigenere(int mode) {
		this.mode = mode;
	}
	
	//getter and setter
	public int getMode() {
		return mode;
	}
	
	public void setMode(int mode) {
		this.mode = mode;
	}
	
	//operation
	public String encrypt(String text, String key) {
		String result  = "";
		if(mode == 0) {
			text = text.toLowerCase();
			key = key.toLowerCase();
			int j = 0;
			for (int i = 0; i < text.length(); i++) {
				char c = text.charAt(i);
				if (c >= 'a' && c <= 'z') {
					result += (char)((c + key.charAt(j) - 2 * 'a') % 26 + 'a');
					j = (j+1) % key.length();
				} else {
					result += c;
				}
			}
		} else {
			int j = 0;
			for (int i = 0; i < text.length(); i++) {
				char c = text.charAt(i);
				result += (char)((c + key.charAt(j)) % 256);
				j = (j+1) % key.length();
			
			}
		}
		return result;
	}
	
	public int[][] encryptArr(int[][] arr, String key, int range) {
		int[] bkey = byteArrToInt(key);
		int[][] result = new int[arr.length][arr[0].length];
		int k = 0;
		for (int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[0].length; j++) {
				int c = arr[i][j];
				result[i][j] = (int)((c + bkey[k]) % range);
				k = (k+1) % bkey.length;	
			}		
		}
		return result;
	}
	
	public int[][] decryptArr(int[][] arr, String key, int range) {
		int[] bkey = byteArrToInt(key);
		int[][] result = new int[arr.length][arr[0].length];
		int k = 0;
		for (int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[0].length; j++) {
				int c = arr[i][j];
				result[i][j] = (int)((c - bkey[k]) % range);
				k = (k+1) % bkey.length;	
			}		
		}
		return result;
	}
	
	private int[] byteArrToInt(String str) {
		int[] res = new int[str.length()];
		for(int i = 0; i < str.length(); i++) {
			res[i] = (int) (str.charAt(i));
		}
		return res;
	}
	public int[] encrypt(int[] text, String key) {
		int[] bkey = byteArrToInt(key);
		int[] result = new int[text.length];
		
		if(mode == 0) {
			key = key.toLowerCase();
			int[] bkey2 = byteArrToInt(key);
			int j = 0;
			int a = (int)('a' & 0x00FF);
			int z = (int)('z' &  0x00FF);
			for (int i = 0; i < text.length; i++) {
				int c = text[i];
				if (c >= a && c <= z) {
					result[i] = (int) ((c + bkey2[j] - 2 * a) % 26 + a);
					j = (j+1) % bkey2.length;
				} else {
					result[i] = c;
				}
			}
		} else {
			int j = 0;
			for (int i = 0; i < text.length; i++) {
				int c = text[i];
				result[i] = (int)((c + bkey[j]) % 256);
				j = (j+1) % bkey.length;
			
			}
		}
		return result;
	}
	
	
	public String decrypt(String chipertext, String key) {
		String result  = "";
		if(mode == 0) {
			chipertext = chipertext.toLowerCase();
			key = key.toLowerCase();
			int j = 0;
			for (int i = 0; i < chipertext.length(); i++) {
				char c = chipertext.charAt(i);
				if (c >= 'a' && c <= 'z') {
					result += (char)((c - key.charAt(j) + 26) % 26 + 'a');
					j = (j+1) % key.length();
				} else {
					result += c;
				}
			}
		} else {
			int j = 0;
			for (int i = 0; i < chipertext.length(); i++) {
				char c = chipertext.charAt(i);
				result += (char)((c - key.charAt(j)) % 256);
				j = (j+1) % key.length();
			
			}
		}
		return result;
	}
	
	public int[] decrypt(int[] text, String key) {
		int[] bkey = byteArrToInt(key);
		int[] result = new int[text.length];
		
		if(mode == 0) {
			key = key.toLowerCase();
			int[] bkey2 = byteArrToInt(key);
			int j = 0;
			int a = (int)('a');
			int z = (int)('z');
			for (int i = 0; i < text.length; i++) {
				int c = text[i];
				if (c >= a && c <= z) {
					result[i] = (int)((c - bkey2[j] + 26) % 26 + a);
					j = (j+1) % bkey2.length;
				} else {
					result[i] = c;
				}
			}
		} else {
			int j = 0;
			for (int i = 0; i < text.length; i++) {
				int c = text[i];
				result[i] = (int)((c - bkey[j]) % 256);
				j = (j+1) % bkey.length;
			
			}
		}
		return result;
	}
}
