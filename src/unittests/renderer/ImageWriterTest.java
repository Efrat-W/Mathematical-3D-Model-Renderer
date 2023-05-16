package unittests.renderer;

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
		final int width = 801;
		final int height = 501;
		final int interval = 50;
		ImageWriter imageWriter = new ImageWriter("img1", width, height);
		
		Color color = new Color(0, 191, 255);
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
					imageWriter.writePixel(x, y, x % interval == 0 || y % interval == 0 ? color : Color.BLACK);
		imageWriter.writeToImage();
	}

}
