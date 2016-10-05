package core;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import core.vigenere.Vigenere;
import utils.BitUtils;
import utils.ImageUtils;

public class Imagewm {

   private BufferedImage watermark_img;
   
   public Imagewm(){}
   
   private int[][] createMark(BufferedImage hostimage, BufferedImage watermark, String key) throws IOException {
       Vigenere vigenere = new Vigenere();
       watermark_img = new BufferedImage(hostimage.getWidth(), hostimage.getHeight(), hostimage.getType());
       BufferedImage image = ImageUtils.makeTilesFit(watermark, hostimage);
       int[][] MarkTemp = new int[image.getWidth()][image.getHeight()];
       for(int i = 0; i < image.getWidth(); i++){
           for(int j = 0; j < image.getHeight(); j++){
               Color color = new Color(image.getRGB(i, j));               
               int grey = (int) (color.getRed()/3 + color.getGreen()/3 + color.getBlue()/3);
               if (grey >= 128){
                   MarkTemp[i][j] = 1;
                   watermark_img.setRGB(i, j, ImageUtils.colorToRGB(255, 255, 255, 255));
               } else {
                   MarkTemp[i][j] = 0;
                   watermark_img.setRGB(i, j, ImageUtils.colorToRGB(0, 0, 0, 0));
               }
           }
       }
       int[][] intMark = vigenere.encryptArr(MarkTemp, key, 2);
       return intMark;
   }

   public BufferedImage addWatermark(BufferedImage image, BufferedImage watermark, String key) throws IOException {
       BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
       int[][] intMark = createMark(image, watermark, key);
       for(int i=0; i<image.getWidth(); i++){
           for(int j=0; j<image.getHeight(); j++){
        	   int bit = intMark[(i%intMark.length)][j%intMark[0].length];
               int color = image.getRGB(i, j);
               color = BitUtils.setLSB(color, bit);
               result.setRGB(i, j, color);
           }
       }
       return result; 
   }

   public BufferedImage getWatermark(BufferedImage image, String key) {
	   BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
       int[][] mark = new int[image.getWidth()][image.getHeight()];
       for(int i=0; i<image.getWidth(); i++){
           for(int j = 0; j < image.getHeight(); j++){
               int color = image.getRGB(i, j);
               int redLSB = BitUtils.getLSB(color);
               mark[i][j] = redLSB;
           }
       }
       Vigenere vigenere = new Vigenere();
       int[][] watermarkbin = vigenere.decryptArr(mark, key, 2);
       
       for(int i = 0; i < watermarkbin.length; i++) {
    	   for(int j = 0; j < watermarkbin[0].length; j++) {
    		   if(watermarkbin[i][j] == 0) {
    			   result.setRGB(i,j, ImageUtils.colorToRGB(0,0,0,0));
    		   } else {
    			   result.setRGB(i,j, ImageUtils.colorToRGB(255,255,255,255));
    		   }
    	   }
       }
       return result;
   }
   
	public BufferedImage getWatermark_img() {
		return watermark_img;
	}
	
	public void setWatermark_img(BufferedImage watermark_img) {
		this.watermark_img = watermark_img;
	}

}
