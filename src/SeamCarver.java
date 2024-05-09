import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

    // create a seam carver object based on the given picture
    private Picture picture;
    public SeamCarver(Picture picture){
        this.picture = new Picture(picture);
    }

    // current picture
    public Picture picture(){
        return picture;
    }

    // width of current picture
    public int width(){
        return picture.width();
    }

    // height of current picture
    public int height(){
        return picture.height();
    }


    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1) {
            return 1000; // 处理边缘情况，返回固定的边缘能量值
        }

        int rgb = picture.getRGB(x, y);
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        int rx = picture.getRGB(Math.min(x + 1, width() - 1), y);
        int gx = picture.getRGB(Math.min(x + 1, width() - 1), y);
        int bx = picture.getRGB(Math.min(x + 1, width() - 1), y);

        int deltaxR = ((rx >> 16) & 0xFF) - r;
        int deltaxG = ((gx >> 8) & 0xFF) - g;
        int deltaxB = (bx & 0xFF) - b;

        int deltaxSquared = deltaxR * deltaxR + deltaxG * deltaxG + deltaxB * deltaxB;

        int ry = picture.getRGB(x, Math.min(y + 1, height() - 1));
        int gy = picture.getRGB(x, Math.min(y + 1, height() - 1));
        int by = picture.getRGB(x, Math.min(y + 1, height() - 1));

        int deltayR = ((ry >> 16) & 0xFF) - r;
        int deltayG = ((gy >> 8) & 0xFF) - g;
        int deltayB = (by & 0xFF) - b;

        int deltaySquared = deltayR * deltayR + deltayG * deltayG + deltayB * deltayB;

        return Math.sqrt(deltaxSquared + deltaySquared);
    }


    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam(){
        return new int[0];
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam(){
        return new int[0];
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam){}

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam){}

    //  unit testing (optional)
    public static void main(String[] args){}

}