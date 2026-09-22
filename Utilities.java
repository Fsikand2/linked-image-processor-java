import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


public final class Utilities
{
   
    public static Image<Short> loadImage(String pgmFile) {
        Scanner scanner;
        try {
            scanner = new Scanner(new File(pgmFile));
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + pgmFile);
        }


        String magic = scanner.nextLine().trim();
        if (!magic.equals("P2")) {
            scanner.close();
            throw new RuntimeException("Invalid PGM format: expected P2, got " + magic);
        }


        String dimensions = scanner.nextLine().trim();
        Scanner dimScanner = new Scanner(dimensions);
        int width  = dimScanner.nextInt();
        int height = dimScanner.nextInt();
        dimScanner.close();


        scanner.nextLine();


        Image<Short> image = new Image<>(width, height);


        for (Node<Short> node : image)
        {
            node.setValue(scanner.nextShort());
        }

        scanner.close();
        return image;
    }


    public static void saveImage(Image<Short> image, String pgmFile) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(new File(pgmFile));
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException("Cannot create file: " + pgmFile);
        }


        writer.println("P2");
        writer.println(image.getWidth() + " " + image.getHeight());
        writer.println("255");


        for (Node<Short> node : image) {
            writer.print(node.getValue() + " ");
        }

        writer.println();
        writer.close();
    }
}