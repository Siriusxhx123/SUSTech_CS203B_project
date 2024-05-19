import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class AddDemo {

//    public static boolean[][] middleImageIsSeam;
//    public static boolean[][] finalImageIsSeam;
    public static double[][] energyMatrix;


    public static void main(String[] args) {
//        if (args.length != 3) {
//            StdOut.println("Usage:\njava AddDemo [image filename] [num cols to add] [num rows to add]");
//            return;
//        }

        Picture inputImg = new Picture("logo.png");
        int addColumns = 0;
        int addRows = 100;

        StdOut.printf("Original image size: %d columns by %d rows\n", inputImg.width(), inputImg.height());

        Picture finalImage = inputImg;


//        middleImageIsSeam = new boolean[finalImage.height()][finalImage.width()];

        for (int i = 0; i < addColumns; i++) {
            SeamCarver scForVertical = new SeamCarver(finalImage);
            int[] verticalSeam = scForVertical.findVerticalSeam();
            finalImage = scForVertical.addVerticalSeam(finalImage, verticalSeam);
        }



        for (int i = 0; i < addRows; i++) {
            SeamCarver scForHorizontal = new SeamCarver(finalImage);

            energyMatrix = new double[finalImage.height()][finalImage.width()];
            energyMatrix = scForHorizontal.calculateEnergyMatrix(finalImage);
            int[] horizontalSeam = scForHorizontal.findHorizontalSeam();
            finalImage = scForHorizontal.addHorizontalSeam(finalImage, horizontalSeam);
        }

        StdOut.printf("New image size: %d columns by %d rows\n", finalImage.width(), finalImage.height());
        inputImg.show();
        finalImage.show();
    }
}
