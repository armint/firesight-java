package org.firepick.firesight;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.junit.Test;

public class TestAbsDiff {
	private static final Logger log = Logger.getLogger(TestAbsDiff.class);
	
	@Test
	public void testAbsDiff() throws IOException {
		String op = "[\n" + 
				"  {\"op\":\"absdiff\", \"path\":\"{{img}}\"},\n" + 
				"  {\"op\":\"calcHist\"}\n" + 
				"]";
		BufferedImage image = ImageIO.read(TestPipelineAccess.class.getResourceAsStream("/pcb.jpg"));
		HashMap<String, String> argMap = new HashMap<String, String>();
		argMap.put("img", "src/test/resources/mog2.jpg");
		String result = FireSight.process(image, op, argMap);
		ImageIO.write(image, "png", new File("testAbsDiff.png"));
		log.info("Result: " + result);
	}
}
