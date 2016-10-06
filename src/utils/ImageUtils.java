package utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageUtils {

	public static void writeImage(BufferedImage BI, String name) throws IOException{
	    if(name != null && !name.equals("")) {
	    	if(name.length() > 3 && name.indexOf('.') > 0) {
				  File file = new File(name);
			      String format = name.substring(name.length()-3);
			      ImageIO.write(BI, format, file);  
			  } else {
				  File file = new File(name+".png");
			      ImageIO.write(BI, "png", file);  
			  }
	    }  
				
	}
	
	public static BufferedImage loadImage(String fileName)  {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
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
	public static BufferedImage substract(String img1, String img2) {
		return substract(loadImage(img1), loadImage(img2));
	}
	public static BufferedImage substract(BufferedImage img1, BufferedImage img2) {
		assert(img1.getHeight() == img2.getHeight()	&& img1.getWidth() == img2.getWidth());
		
		BufferedImage res = new BufferedImage(img1.getWidth(), img1.getHeight(), img1.getType());
		for(int i = 0; i < img1.getWidth(); i++) {
			for(int j =0 ; j< img1.getHeight(); j++) {
				int a = img1.getRGB(i, j);
				int b = img2.getRGB(i, j);
				res.setRGB(i, j, 0xFF000000 & (a-b));
			}
		}
		return res;
	}
	
	public static BufferedImage scaleImage(int WIDTH, int HEIGHT, BufferedImage image) {
	    BufferedImage bi = null;
	    try {
	        ImageIcon ii = new ImageIcon(image);//path to image
	        bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	        Graphics2D g2d = (Graphics2D) bi.createGraphics();
	        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY));
	        g2d.drawImage(ii.getImage(), 0, 0, WIDTH, HEIGHT, null);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	    return bi;
	}
	
	public static double getPSNR(BufferedImage im1, BufferedImage im2) {
		assert(
			im1.getType() == im2.getType()
				&& im1.getHeight() == im2.getHeight()
				&& im1.getWidth() == im2.getWidth());

		double mse = 0;
		int width = im1.getWidth();
		int height = im1.getHeight();
		for (int j = 0; j < height; j++)
			for (int i = 0; i < width; i++)
				mse += Math.pow(im1.getRGB(i, j) - im2.getRGB(i, j), 2);
		mse /= (double) (width * height);
		System.err.println("MSE = " + mse);
		int maxVal = 255;
		double x = Math.pow(maxVal, 2) / mse;
		double psnr = 10.0 * logbase10(x);
		System.err.println("PSNR = " + psnr);
		return psnr;
	}

	public static double logbase10(double x) {
		return Math.log(x) / Math.log(10);
	}
}
