import java.io.File;
import java.io.FileWriter;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;

public class Image {
    public static BufferedImage getImage(String filePath) {
        try{
            File file = new File(filePath);
            BufferedImage img = ImageIO.read(file);
            return(img);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            return(null);
         }
    }
   public static int[] getDim(String filePath){
    BufferedImage img = getImage(filePath);
    int[] dim = new int[]{img.getHeight(), img.getWidth()};
    return(dim);
  }

  public static ArrayList<int[]> getArray(String filePath){
    BufferedImage img = getImage(filePath);
    ArrayList<int[]> pixelArray = new ArrayList<int[]>();
    for (int y = 0; y < img.getHeight(); y++) {
        for (int x = 0; x < img.getWidth(); x++) {
            int pixel = img.getRGB(x,y);
            Color color = new Color(pixel, true);
            int red = color.getRed();
            int green = color.getGreen();
            int blue = color.getBlue();
            int[] temp = new int[]{red, green, blue}; 
            pixelArray.add(temp);
        }
    }
    return(pixelArray);
}

public static void makeImage(int[][] pixels, int[] dim) throws IOException{
    int h = dim[0];
    int w = dim[1];
    BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    for(int i = 0; i < h; i++){
        for(int j = 0; j < w; j++){
            int place = i*w + j;
            Color myColour = new Color(pixels[place][0], pixels[place][1], pixels[place][2]);
            int rgb = myColour. getRGB();
            img.setRGB(j, i, rgb);
        }
    }
    File f = new File("images/MyFile.png");
    ImageIO.write(img, "PNG", f);
} 

  public static void main(String[] args){
    try{
        Pixels p = new Pixels();
        int[][] pixels = p.kmeans(256, getArray("images/flower.jpeg"), 10);
        makeImage(pixels, getDim("images/flower.jpeg"));
    }
    catch(IOException ex){
    }
    
}
}


// https://www.tutorialspoint.com/how-to-get-pixels-rgb-values-of-an-image-using-java-opencv-library