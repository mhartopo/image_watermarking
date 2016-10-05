package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.Raster;

public class ImageUtils {
	public static void writeImage(BufferedImage BI, String name, String format) throws IOException{
	       File file = new File(name + "." + format);
	       ImageIO.write(BI, format, file);
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
