package unittests;

import primitives.Color;

import org.junit.jupiter.api.Test;

import renderer.ImageWriter;

/**
 * Unit tests for image writer
 * 
 * @author Efrat Wexler and Sari Zilberlicht
 */
class ImageWriterTest {

    /**
     * Test for creating image Create image with a grid of 10x16 squares on the
     * screen (ViewPlane) 1000 x 1600 and a resolution of 500 x 800: Test method for
     * {@link renderer.ImageWriter#writePixel(int, int, Color)} Test method for
     * {@link renderer.ImageWriter#ImageWriter(String, int, int)} Test method for
     * {@link renderer.ImageWriter#writeToImage()}
     */
    @Test
    void writeToImageTest() {
	ImageWriter imageWriter = new ImageWriter("img1", 801, 501);
	Color color = new Color(0, 191, 255);
	for (int x = 0; x < 801; x++) {
	    for (int y = 0; y < 501; y++) {
		if (x % 50 == 0 || y % 50 == 0)
		    imageWriter.writePixel(x, y, color);
		else
		    imageWriter.writePixel(x, y, Color.BLACK);
	    }
	}
	imageWriter.writeToImage();
    }

}
