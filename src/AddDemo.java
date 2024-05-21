import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

import java.awt.*;

public class AddDemo {
    
    public static void main(String[] args) {

        Picture inputImg = new Picture("路飞头像.jpg");
        int addColumns = 50;
        int addRows = 30;

        StdOut.printf("Original image size: %d columns by %d rows\n", inputImg.width(), inputImg.height());
        inputImg.show();

        Picture finalImage = inputImg;

        //add vertical seam
        for (int i = 0; i < addColumns; i++) {
            SeamCarver scForVertical = new SeamCarver(finalImage);

            int[] verticalSeam = scForVertical.findVerticalSeam();
            finalImage = scForVertical.addVerticalSeam(finalImage, verticalSeam);
        }

        finalImage.show();

        //change vertical seam
        Picture middlePicture = finalImage;
        Color seamColor = new Color(255, 103, 102);
        boolean con1;

        do {
            con1 = false;
            outerLoop:
            for (int i = 0; i < middlePicture.height(); i++) {
                for (int j = 0; j < middlePicture.width(); j++) {
                    if (middlePicture.get(j, i).equals(seamColor)) {
                        con1 = true;
                        break outerLoop;
                    }
                }
            }

            if (con1) {
                SeamCarver scForFinalImage = new SeamCarver(middlePicture);
                middlePicture = scForFinalImage.changeVerticalSeam(middlePicture);
            }

        } while (con1);

        middlePicture.show();
        finalImage = middlePicture;

        //add horizontal seam
        for (int i = 0; i < addRows; i++) {
            SeamCarver scForHorizontal = new SeamCarver(finalImage);

            int[] horizontalSeam = scForHorizontal.findHorizontalSeam();
            finalImage = scForHorizontal.addHorizontalSeam(finalImage, horizontalSeam);
        }

        finalImage.show();

        //change horizontal seam
        Picture finalPicture = finalImage;
        boolean con;

        do {
            con = false;
            outerLoop:
            for (int i = 0; i < finalPicture.height(); i++) {
                for (int j = 0; j < finalPicture.width(); j++) {
                    if (finalPicture.get(j, i).equals(seamColor)) {
                        con = true;
                        break outerLoop;
                    }
                }
            }

            if (con) {
                SeamCarver scForFinalImage = new SeamCarver(finalPicture);
                finalPicture = scForFinalImage.changeHorizontalSeam(finalPicture);
            }

        } while (con);


        StdOut.printf("New image size: %d columns by %d rows\n", finalImage.width(), finalImage.height());

        finalPicture.show();
    }
}
