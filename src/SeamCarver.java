import edu.princeton.cs.algs4.Picture;

import java.awt.*;

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
            return 1000.00; // 处理边缘情况，返回固定的边缘能量值
        }

        double rx = (picture.getRGB(x + 1, y) >> 16) & 0xFF;
        double gx = (picture.getRGB(x + 1, y) >> 8) & 0xFF;
        double bx = picture.getRGB(x + 1, y) & 0xFF;

        double deltaxR = rx - ((picture.getRGB(x - 1, y) >> 16) & 0xFF);
        double deltaxG = gx - ((picture.getRGB(x - 1, y) >> 8) & 0xFF);
        double deltaxB = bx - (picture.getRGB(x - 1, y) & 0xFF);

        double deltaxSquared = deltaxR * deltaxR + deltaxG * deltaxG + deltaxB * deltaxB;

        double ry = (picture.getRGB(x, y + 1) >> 16) & 0xFF;
        double gy = (picture.getRGB(x, y + 1) >> 8) & 0xFF;
        double by = picture.getRGB(x, y + 1) & 0xFF;

        double deltayR = ry - ((picture.getRGB(x, y - 1) >> 16) & 0xFF);
        double deltayG = gy - ((picture.getRGB(x, y - 1) >> 8) & 0xFF);
        double deltayB = by - (picture.getRGB(x, y - 1) & 0xFF);

        double deltaySquared = deltayR * deltayR + deltayG * deltayG + deltayB * deltayB;

        double energy = Math.sqrt(deltaxSquared + deltaySquared);

        return Math.round(energy * 100.0) / 100.0; // 保留两位小数
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