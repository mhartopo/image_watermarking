package utils;

public class BitUtils {
	public static int setLSB(int val, int bit) {
		return val & 0xFFFFFFFE | bit;
	}
	
	public static byte setLSB(byte bval, byte bit) {
		return (byte) (bval & 0xFE | bit);
	}
	
	public static String getBits(int val) {
		return String.format("%32s", Integer.toBinaryString(val)).replace(' ', '0');
	}
	
	public static String getBits(byte bval) {
		return String.format("%8s", Integer.toBinaryString(bval & 0xFF)).replace(' ', '0');
	}
	
	public static int getLSB(int val) {
		return val & 1;
	}
	
	public static int getLSB(byte bval) {
		return bval & 0x01;
	}
	
	public static String extractLSB(int[] arr) {
		String bits = "";
		for(int i = 0; i < arr.length; i++) {
			bits += Integer.toString(getLSB(arr[i]));
		}
		return bits;
	}
	
	public static String extractLSB(byte[] arr) {
		String bits = "";
		for(int i = 0; i < arr.length; i++) {
			bits += Integer.toString(getLSB(arr[i]));
		}
		return bits;
	}
}
