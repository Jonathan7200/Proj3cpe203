import java.util.*;

import processing.core.PApplet;
import processing.core.PImage;

public final class ImageStore {
    private Map<String, List<PImage>> images;
    private List<PImage> defaultImages;

    public ImageStore(PImage defaultImage) {
        this.images = new HashMap<>();
        defaultImages = new LinkedList<>();
        defaultImages.add(defaultImage);
    }

    public void processImageLine( String line, PApplet screen) {
        String[] attrs = line.split("\\s");
        if (attrs.length >= 2) {
            String key = attrs[0];
            PImage img = screen.loadImage(attrs[1]);
            if (img != null && img.width != -1) {
                List<PImage> imgs = Functions.getImages(images, key);
                imgs.add(img);

                if (attrs.length >= Functions.KEYED_IMAGE_MIN) {
                    int r = Integer.parseInt(attrs[Functions.getKeyedRedIdx()]);
                    int g = Integer.parseInt(attrs[Functions.getKeyedGreenIdx()]);
                    int b = Integer.parseInt(attrs[Functions.getKeyedBlueIdx()]);
                    Functions.setAlpha(img, screen.color(r, g, b), 0);
                }
            }
        }
    }


    public  List<PImage> getImageList( String key) {
        return this.images.getOrDefault(key, this.defaultImages);
    }

    public void loadImages(Scanner in,  PApplet screen) {
        int lineNumber = 0;
        while (in.hasNextLine()) {
            try {
                this.processImageLine( in.nextLine(), screen);
            } catch (NumberFormatException e) {
                System.out.printf("Image format error on line %d\n", lineNumber);
            }
            lineNumber++;
        }
    }
}


