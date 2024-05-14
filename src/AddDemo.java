import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class AddDemo {
    public static void main(String[] args) {
//        if (args.length != 3) {
//            StdOut.println("Usage:\njava AddDemo [image filename] [num cols to add] [num rows to add]");
//            return;
//        }

        Picture inputImg = new Picture("7x10.png");
        int addColumns = 0;
        int addRows = 5;

        StdOut.printf("Original image size: %d columns by %d rows\n", inputImg.width(), inputImg.height());

        SeamCarver sc = new SeamCarver(inputImg);

        int[][] verticalSeams = sc.findMultipleVerticalSeams(addColumns);
        Picture finalImage = sc.addVerticalSeams(inputImg, verticalSeams);

//        SeamCarver in = new SeamCarver(finalImage);

        for (int i = 0; i < addRows; i++) {
            int[] horizontalSeams = sc.findHorizontalSeam();
            finalImage = sc.addHorizontalSeams(finalImage, horizontalSeams);
        }


        StdOut.printf("New image size: %d columns by %d rows\n", finalImage.width(), finalImage.height());
        inputImg.show();
        finalImage.show();
    }
}
