package core;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import core.vigenere.Vigenere;
import utils.ImageUtils;

public class ImageCipher {
	private Vigenere vigenere;
	
	public ImageCipher() {
		vigenere = new Vigenere();
	}
	
	public BufferedImage encrypt(BufferedImage image, String key) {	
		BufferedImage result;
		int[] plain = ImageToBytes(image);
		int[] cipher = vigenere.encrypt(plain, key);
		result = BytesToImage(cipher, image.getWidth(), image.getHeight(), image.getType());
		return result;
	}
	
	public BufferedImage BytesToImage(int[] bytes, int width, int height, int type) {
		BufferedImage result = new BufferedImage(width, height, type);
		int k = 0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int a = bytes[k];
				int r = bytes[k+1];
				int g = bytes[k+2];
				int b = bytes[k+3];
				k = k + 4;
				result.setRGB(i, j, colorToRGB(a,r,g,b));
			}
		}
		return result;
	}
	
	public int[] ImageToBytes(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		int[] bytes = new int[width*height*4];
		int k = 0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int a = new Color(image.getRGB(i, j)).getAlpha();
				int r = new Color(image.getRGB(i, j)).getRed();
				int g = new Color(image.getRGB(i, j)).getGreen();
				int b = new Color(image.getRGB(i, j)).getBlue();
				bytes[k] = a;
				bytes[k+1] = r;
				bytes[k+2] = g;
				bytes[k+3] = b;
				k = k + 4;
			}
		}
		return bytes;
	}
	
	private int colorToRGB(int alpha, int red, int green, int blue) {
	       int newPixel = 0;
	       newPixel += alpha; newPixel = newPixel << 8;
	       newPixel += red; newPixel = newPixel << 8;
	       newPixel += green; newPixel = newPixel << 8;
	       newPixel += blue;
	       return newPixel;
	   }
	
	public BufferedImage decrypt(BufferedImage image, String key) {
		BufferedImage result;
		int[] plain = ImageToBytes(image);
		int[] cipher = vigenere.decrypt(plain, key);
		result = BytesToImage(cipher, image.getWidth(), image.getHeight(), image.getType());
		return result;
	}
	
	public static void main(String[] args) throws IOException {
		ImageCipher ic = new ImageCipher();
		BufferedImage img = ImageIO.read(new File("C:/Users/muhtarh/Desktop/result.png"));
		BufferedImage enc = ic.encrypt(img, "muhtarh/@dsd221dncbmcnqhagasgjfg");
		ImageUtils.writeImage(enc, "C:/Users/muhtarh/Desktop/imgenc", "png");
		BufferedImage dec = ic.decrypt(enc, "muhtarh/@dsd221dncbmcnqhagasgjfg");
		ImageUtils.writeImage(dec, "C:/Users/muhtarh/Desktop/imgdec", "png");
		System.out.println((int)('a'));
	}
}
