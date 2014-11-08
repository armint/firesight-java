package org.firepick.firesight;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

public class TestHoughCircle {

//private static final Logger log = Logger.getLogger(TestHoleRecognizer.class);
	
	@Test
	public void test() throws IOException {
		BufferedImage image = ImageIO.read(TestHoughCircle.class.getResourceAsStream("/cam.jpg"));
		HoughCircle houghCircle = new HoughCircle(22, 30);
		houghCircle.setShowCircles(1);
		houghCircle.scan(image);
		ImageIO.write(image, "jpg", new File("testHoughCircle.jpg"));
	}
}
