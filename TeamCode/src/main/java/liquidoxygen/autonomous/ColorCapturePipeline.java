package liquidoxygen.autonomous;

import com.pocolifo.robobase.vision.AbstractResultCvPipeline;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

public class ColorCapturePipeline extends AbstractResultCvPipeline<DetectedColor> {
	/**
	 * The width of the color capture region.
	 */
	private static final int REGION_WIDTH = 20;

	/**
	 * The height of the color capture region.
	 */
	private static final int REGION_HEIGHT = 20;

	/**
	 * The top left most point of the color capture region.
	 */
	private static final Point TOP_LEFT_POINT = new Point(300, 200);

	/**
	 * The bottom right most point of the color capture region.
	 */
	private static final Point BOTTOM_RIGHT_POINT = new Point(TOP_LEFT_POINT.x + REGION_WIDTH,
						TOP_LEFT_POINT.y + REGION_HEIGHT);

	/**
	 * The color capture region.
	 */
	private static final Rect COLOR_CAPTURE_REGION = new Rect(TOP_LEFT_POINT, BOTTOM_RIGHT_POINT);

	@Override
	public Mat processFrame(Mat input) {
		// Capture colors
		Mat colorCapture = input.submat(COLOR_CAPTURE_REGION);

		// Find averages
		int redAvg = (int) Core.mean(colorCapture).val[0];
		int greenAvg = (int) Core.mean(colorCapture).val[1];
		int blueAvg = (int) Core.mean(colorCapture).val[2];

		// Find the max of all the colors
		int max = Math.max(Math.max(blueAvg, greenAvg), redAvg);

		if (max == redAvg) {
			// red
			this.result = DetectedColor.RED;
		} else if (max == greenAvg) {
			// green
			this.result = DetectedColor.GREEN;
		} else {
			// blue
			this.result = DetectedColor.BLUE;
		}

		// Visual indicator
		Imgproc.rectangle(input, TOP_LEFT_POINT, BOTTOM_RIGHT_POINT, this.result.color, 2);

		return input;
	}
}
