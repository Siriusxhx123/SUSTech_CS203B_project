public class testSeamMatrix {
    public static int[][] seamMatrix = new int[0][];


    public static void main(String[] args) {

        int[] out = {1,2,3};
//        int[] out2 = {4,5,6};
        seamMatrix = addRow(seamMatrix,out);
//        seamMatrix = addRow(seamMatrix,out2);
//        for (int i = 0; i < seamMatrix.length; i++) {
//            for (int j = 0; j < seamMatrix[0].length; j++) {
//                System.out.print(seamMatrix[i][j] + " ");
//            }
//            System.out.println();
//        }
        System.out.println(seamMatrix.length);
    }

    public static int[][] addRow(int[][] original, int[] newRow) {
        // Create a new array with an additional row
        int[][] newArray = new int[original.length + 1][];

        // Copy existing rows to the new array
        for (int i = 0; i < original.length; i++) {
            newArray[i] = original[i];
        }

        // Add the new row
        newArray[original.length] = newRow;

        return newArray;
    }
}
