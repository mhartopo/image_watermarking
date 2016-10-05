package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.Raster;

public class ImageUtils {
	public static void writeImage(BufferedImage BI, String name) throws IOException{
	       File file = new File(name);
	       String format = name.substring(name.length()-3);
	       ImageIO.write(BI, format, file);
	}
	
	public static BufferedImage loadImage(String fileName) throws IOException {
		return ImageIO.read(new File(fileName));
	}
	
	public static int colorToRGB(int alpha, int red, int green, int blue) {
	       int newPixel = 0;
	       newPixel += alpha; newPixel = newPixel << 8;
	       newPixel += red; newPixel = newPixel << 8;
	       newPixel += green; newPixel = newPixel << 8;
	       newPixel += blue;
	       return newPixel;
	   }
	
	public static BufferedImage makeTilesFit(BufferedImage img1, BufferedImage imgtarget) {
		   int h = imgtarget.getHeight();
		   int w = imgtarget.getWidth();
		   BufferedImage result = new BufferedImage(w, h, img1.getType());
		   int x = 0;
		   int y = 0;
		   for(int i = 0; i < w; i++) {
			   for(int j = 0; j < h; j++) {
				   result.setRGB(i, j, img1.getRGB(x, y));
				   y = (y + 1) % img1.getHeight(); 
			   }
			 x = (x + 1) % img1.getWidth();
			 y = 0;
		   }
		   return result;
	   }
	
	public static double getPSNR(BufferedImage im1, BufferedImage im2) {
		assert(
			im1.getType() == im2.getType()
				&& im1.getHeight() == im2.getHeight()
				&& im1.getWidth() == im2.getWidth());

		double mse = 0;
		int width = im1.getWidth();
		int height = im1.getHeight();
		Raster r1 = im1.getRaster();
		Raster r2 = im2.getRaster();
		for (int j = 0; j < height; j++)
			for (int i = 0; i < width; i++)
				mse += Math.pow(r1.getSample(i, j, 0) - r2.getSample(i, j, 0), 2);
		mse /= (double) (width * height);
		int maxVal = 255;
		double x = Math.pow(maxVal, 2) / mse;
		double psnr = 10.0 * logbase10(x);
		return psnr;
	}

	public static double logbase10(double x) {
		return Math.log(x) / Math.log(10);
	}
}
