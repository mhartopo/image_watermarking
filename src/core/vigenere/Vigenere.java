package core.vigenere;

public class Vigenere {
	//mode 0 =  26 character (a - z), mode 1 = 256 ASCII
	private int mode;
	//constructor
	public Vigenere() {
		mode = 0;
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
	
	public byte[] encrypt(byte[] text, String key) {
		byte[] bkey = key.getBytes();
		byte[] result = new byte[text.length];
		
		if(mode == 0) {
			key = key.toLowerCase();
			byte[] bkey2 = key.getBytes();
			int j = 0;
			byte a = (byte)('a' & 0x00FF);
			byte z = (byte)('z' &  0x00FF);
			for (int i = 0; i < text.length; i++) {
				byte c = text[i];
				if (c >= a && c <= z) {
					result[i] = (byte) ((c + bkey2[j] - 2 * a) % 26 + a);
					j = (j+1) % bkey2.length;
				} else {
					result[i] = c;
				}
			}
		} else {
			int j = 0;
			for (int i = 0; i < text.length; i++) {
				byte c = text[i];
				result[i] = (byte)((c + bkey[j]) % 256);
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
	
	public byte[] decrypt(byte[] text, String key) {
		byte[] bkey = key.getBytes();
		byte[] result = new byte[text.length];
		
		if(mode == 0) {
			key = key.toLowerCase();
			byte[] bkey2 = key.getBytes();
			int j = 0;
			byte a = (byte)('a' & 0x00FF);
			byte z = (byte)('z' &  0x00FF);
			for (int i = 0; i < text.length; i++) {
				byte c = text[i];
				if (c >= a && c <= z) {
					result[i] = (byte)((c - bkey2[j] + 26) % 26 + a);
					j = (j+1) % bkey2.length;
				} else {
					result[i] = c;
				}
			}
		} else {
			int j = 0;
			for (int i = 0; i < text.length; i++) {
				byte c = text[i];
				result[i] = (byte)((c - bkey[j]) % 256);
				j = (j+1) % bkey.length;
			
			}
		}
		return result;
	}
}
