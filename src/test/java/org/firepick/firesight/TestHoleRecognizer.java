package org.firepick.firesight;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.imageio.ImageIO;

import org.junit.Test;

public class TestHoleRecognizer {

//private static final Logger log = Logger.getLogger(TestHoleRecognizer.class);
	
	@Test
	public void test() throws IOException {
		BufferedImage image = ImageIO.read(TestHoleRecognizer.class.getResourceAsStream("/cam.jpg"));
		HoleRecognizer holeRecognizer = new HoleRecognizer(22.6f, 29.9f);
		holeRecognizer.showMatches(1);
		Collection<MatchedRegion> scan = holeRecognizer.scan(image);
		ImageIO.write(image, "jpg", new File("testHoleRecognizer.jpg"));
	}
	
	public static void main(String[] args) throws IOException {
		new TestHoleRecognizer().test();
	}
}
