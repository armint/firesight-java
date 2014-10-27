package org.firepick.firesight;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.junit.Test;

public class TestPipelineAccess {

private static final Logger log = Logger.getLogger(TestPipelineAccess.class);
	
	@Test
	public void testNormalize() throws IOException {
		String op = "[\n" + 
				"  {\"op\":\"normalize\",\n" + 
				"    \"normType\":\"NORM_L2\",\n" + 
				"    \"domain\":\"[20,52]\"" +
				"  }\n" + 
				"]";
		BufferedImage image = ImageIO.read(TestPipelineAccess.class.getResourceAsStream("/abc.png"));
		String result = FireSight.process(image, op);
		ImageIO.write(image, "png", new File("testNormalize.png"));
		log.info("Result: " + result);
	}
}
