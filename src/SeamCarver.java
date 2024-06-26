// -*- coding: utf-8 -*-

import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {

    // create a seam carver object based on the given picture
    private Picture picture;

    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1) {
            return 1000.00;
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

        return Math.round(energy * 100.0) / 100.0;
    }



    //sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int W = picture.height();
        int H = picture.width();
        double[][] energy = new double[H][W];
        int[][] res = new int[H][W];
        double[] temp1 = new double[W];
        double[] temp2 = new double[W];
        for (int j = 0; j < W; j++) {
            temp1[j] = 1000;
            for (int i = 0; i < H; i++) {
                energy[i][j] = energy(i,j);
            }
        }

        for (int i = 1; i < H; i++) {
            for (int j = 0; j < W; j++) {
                int o = j;
                double min = temp1[j];
                if (j + 1 < W && temp1[j + 1] < min) {
                    min = temp1[j + 1];
                    o = j + 1;
                }
                if (j > 0 && temp1[j - 1] <= min) {
                    min = temp1[j - 1];
                    o = j - 1;
                }
                temp2[j] = energy[i][j] + min;
                res[i][j] = o;
            }
            temp1 = temp2.clone();
        }
        int oo = 0;
        double mm = temp1[0];
        for (int i = 1; i < W; i++) {
            if (temp1[i] < mm) {
                mm = temp1[i];
                oo = i;
            }
        }
        int[] out = new int[H];
        out[H - 1] = oo;
        for (int i = H - 2; i >= 0; i--) {
            out[i] = res[i + 1][out[i + 1]];
        }

        return out;
    }


    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int W = picture.width();
        int H = picture.height();
        double[][] energy = new double[H][W];
        int[][] res = new int[H][W];
        double[] temp1 = new double[W];
        double[] temp2 = new double[W];

        for (int j = 0; j < W; j++) {
            temp1[j] = 1000;
            for (int i = 0; i < H; i++) {
                energy[i][j] = energy(j,i);
            }
        }
        for (int i = 1; i < H; i++) {
            for (int j = 0; j < W; j++) {
                int o = j;
                double min = temp1[j];
                if (j + 1 < W && temp1[j + 1] < min) {
                    min = temp1[j + 1];
                    o = j + 1;
                }
                if (j > 0 && temp1[j - 1] <= min) {
                    min = temp1[j - 1];
                    o = j - 1;
                }
                temp2[j] = energy[i][j] + min;
                res[i][j] = o;
            }
            temp1 = temp2.clone();
        }
        int oo = 0;
        double mm = temp1[0];
        for (int i = 1; i < W; i++) {
            if (temp1[i] < mm) {
                mm = temp1[i];
                oo = i;
            }
        }
        int[] out = new int[H];
        out[H - 1] = oo;
        for (int i = H - 2; i >= 0; i--) {
            out[i] = res[i + 1][out[i + 1]];
        }
        return out;
    }


    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null || seam.length != width() || height() <= 1) {
            throw new IllegalArgumentException("Invalid seam.");
        }

        Picture newPicture = new Picture(width(), height() - 1);
        for (int col = 0; col < width(); col++) {
            int rowToRemove = seam[col];
            if (rowToRemove < 0 || rowToRemove >= height() - 1) {
                throw new IllegalArgumentException("Invalid seam value.");
            }
            for (int row = 0; row < height() - 1; row++) {
                if (row < rowToRemove) {
                    newPicture.set(col, row, picture.get(col, row));
                } else {
                    newPicture.set(col, row, picture.get(col, row + 1));
                }
            }
        }
        picture = newPicture;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null || seam.length != height() || width() <= 1) {
            throw new IllegalArgumentException("Invalid seam.");
        }

        Picture newPicture = new Picture(width() - 1, height());
        for (int row = 0; row < height(); row++) {
            int colToRemove = seam[row];
            if (colToRemove < 0 || colToRemove >= width() - 1) {
                throw new IllegalArgumentException("Invalid seam value.");
            }
            for (int col = 0; col < width() - 1; col++) {
                if (col < colToRemove) {
                    newPicture.set(col, row, picture.get(col, row));
                } else {
                    newPicture.set(col, row, picture.get(col + 1, row));
                }
            }
        }
        picture = newPicture;
    }

    public Picture addHorizontalSeam(Picture picture, int[] seam) {
        int width = picture.width();
        int height = picture.height();

        Picture expandedPicture = new Picture(width, height + 1);

        for (int col = 0; col < width; col++) {
            int originalRow = 0;
            for (int row = 0; row < height + 1; row++) {
                if (row < seam[col]) {
                    expandedPicture.set(col, row, picture.get(col, originalRow));
                    originalRow++;
                } else if (row == seam[col]) {
                    Color currentColor = picture.get(col, originalRow);
                    Color seamColor = new Color(255, 103, 102);
                    expandedPicture.set(col, row, currentColor);
                    row++;
                    expandedPicture.set(col, row, seamColor);
                    originalRow++;
                } else {
                    expandedPicture.set(col, row, picture.get(col, originalRow));
                    originalRow++;
                }
            }
        }
        return expandedPicture;
    }

    public Picture changeHorizontalSeam(Picture picture) {
        int width = picture.width();
        int height = picture.height();
        Color seamColor = new Color(255, 103, 102);
        Picture finalPicture = new Picture(picture);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (picture.get(col, row).equals(seamColor)) {
                    Color lastColor = (row > 0) ? picture.get(col, row - 1) : picture.get(col, row);
                    Color nextColor = (row + 1 < height) ? picture.get(col, row + 1) : picture.get(col, row);
                    Color averageColor = new Color(
                            (lastColor.getRed() + nextColor.getRed()) / 2,
                            (lastColor.getGreen() + nextColor.getGreen()) / 2,
                            (lastColor.getBlue() + nextColor.getBlue()) / 2
                    );
                    finalPicture.set(col, row, averageColor);
                } else if (row + 1 < height && picture.get(col, row+1).equals(seamColor)){
                    finalPicture.set(col, row, picture.get(col, row));
                    Color lastColor = (row > 0) ? picture.get(col, row - 1) : picture.get(col, row);
                    picture.set(col, row+1, lastColor);
                } else finalPicture.set(col, row, picture.get(col, row));
            }
        }
        return finalPicture;
    }

    public Picture addVerticalSeam(Picture picture, int[] seam) {
        int width = picture.width();
        int height = picture.height();

        Picture expandedPicture = new Picture(width+1, height);

        for (int row = 0; row < height; row++) {
            int originalcolumn = 0;
            for (int col = 0; col < width + 1; col++) {
                if (col < seam[row]) {
                    expandedPicture.set(col, row, picture.get( originalcolumn,row));
                    originalcolumn++;
                } else if (col == seam[row]) {
                    Color currentColor = picture.get(originalcolumn,row);
                    Color seamColor = new Color(255, 103, 102);
                    expandedPicture.set(col,row,currentColor);
                    col++;
                    expandedPicture.set(col,row,seamColor);
                    originalcolumn++;
                } else {
                    expandedPicture.set(col,row,picture.get(originalcolumn,row));
                    originalcolumn++;
                }
            }
        }
        return expandedPicture;
    }

    public Picture changeVerticalSeam(Picture picture) {
        int width = picture.width();
        int height = picture.height();
        Color seamColor = new Color(255, 103, 102);
        Picture finalPicture = new Picture(picture);

        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                if (picture.get(col,row).equals(seamColor)) {
                    Color lastColor = (col > 0) ? picture.get(col - 1,row) : picture.get(col,row);
                    Color nextColor = (col + 1 < width) ? picture.get(col + 1,row) : picture.get( col,row);
                    Color averageColor = new Color(
                            (lastColor.getRed() + nextColor.getRed()) / 2,
                            (lastColor.getGreen() + nextColor.getGreen()) / 2,
                            (lastColor.getBlue() + nextColor.getBlue()) / 2
                    );
                    finalPicture.set(col, row, averageColor);
                } else if (col + 1 < width && picture.get(col+1,row).equals(seamColor)){
                    finalPicture.set(col, row,picture.get( col,row));
                    Color lastColor = (col > 0) ? picture.get( col - 1,row) : picture.get( col,row);
                    picture.set(col+1, row, lastColor);
                } else finalPicture.set(col, row, picture.get(col,row));
            }
        }
        return finalPicture;
    }


    //  unit testing (optional)
}