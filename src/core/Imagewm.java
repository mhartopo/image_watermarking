package core;
/**
*
* @author Fikri Aulia
*/

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;

import utils.BitUtils;
import utils.ImageUtils;

public class Imagewm {

   private static int[][] intMark;

   public static void main(String[] args) throws IOException {
       Scanner scan = new Scanner(System.in);
       int n = scan.nextInt();
       switch (n){
           case 1 :{
               BufferedImage watermarkImage;
               createMark(ImageIO.read(new File("C:/Users/muhtarh/Desktop/wm.png")));
               watermarkImage = createWatermark(ImageIO.read(new File("C:/Users/muhtarh/Desktop/img.png")));
               ImageUtils.writeImage(watermarkImage, "C:/Users/muhtarh/Desktop/result", "png");
               break;
           }
           case 2 :{
               BufferedImage mark = getMark(ImageIO.read(new File("C:/Users/muhtarh/Desktop/result.png")));
               ImageUtils.writeImage(mark, "C:/Users/muhtarh/Desktop/imageMark", "png");
               break;
           }
       }
       scan.close();
   }
   

   private static BufferedImage createMark(BufferedImage image) throws IOException {
       BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
       intMark = new int[image.getWidth()][image.getHeight()];
       for(int i = 0; i < image.getWidth(); i++){
           for(int j = 0; j < image.getHeight(); j++){
               Color color = new Color(image.getRGB(i, j));
               
               int grey = (int) (0.3333 * color.getRed() + 0.3333 * color.getGreen() + 0.3333 * color.getBlue());
               if (grey >= 128){
                   Color s = new Color(255, 255, 255);
                   result.setRGB(i, j, s.getRGB());
                   intMark[i][j] = 1;
               } else {
                   Color s = new Color(0, 0, 0);
                   result.setRGB(i, j, s.getRGB());
                   intMark[i][j] = 0;
               }
           }
       }
       return result;
   }

   private static BufferedImage createWatermark(BufferedImage image) throws IOException {
       BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
       for(int i=0; i<image.getWidth(); i++){
           for(int j=0; j<image.getHeight(); j++){ 
               int a = new Color(image.getRGB(i, j)).getAlpha();
               int r = new Color(image.getRGB(i, j)).getRed();
               int g = new Color(image.getRGB(i, j)).getGreen();
               int b = new Color(image.getRGB(i, j)).getBlue();
               if (intMark[(i%intMark.length)][j%intMark[0].length] == 1){
                   if ((r % 2) == 0){
                       r = r+1;
                       if (r < 0)
                           r = 0;
                       if (r > 255)
                           r = 255;
                       result.setRGB(i, j, colorToRGB(a,r,g,b));
                   } else {
                       result.setRGB(i, j, colorToRGB(a,r,g,b));
                   }
               } else {
                  
                   if ((r % 2) == 1){
                       r = r-1;
                       if (r < 0)
                           r = 0;
                       if (r > 255)
                           r = 255;
                       result.setRGB(i, j, colorToRGB(a,r,g,b));
                   } else {
                       result.setRGB(i, j, colorToRGB(a,r,g,b));
                   }
               }
           }
       }
       //writeImage("watermark", watermark);
       return result; 
   }

   private static BufferedImage getMark(BufferedImage image) {
       BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
       for(int i=0; i<image.getWidth(); i++){
           for(int j = 0; j < image.getHeight(); j++){
               Color color = new Color(image.getRGB(i, j));
               int red = color.getRed();
               int redLSB = BitUtils.getLSB(red);
               if (redLSB == 1){
                   result.setRGB(i, j, colorToRGB(color.getAlpha(), 255, 255, 255));
               } else {
                   result.setRGB(i, j, colorToRGB(color.getAlpha(), 0, 0, 0));
               }
           }
       }
       return result;
   }

   private static int colorToRGB(int alpha, int red, int green, int blue) {
       int newPixel = 0;
       newPixel += alpha; newPixel = newPixel << 8;
       newPixel += red; newPixel = newPixel << 8;
       newPixel += green; newPixel = newPixel << 8;
       newPixel += blue;
       return newPixel;
   }
}
