package liquidoxygen.autonomous;

import org.opencv.core.Scalar;

public enum DetectedColor {
	RED(new Scalar(255, 0, 0)),
	GREEN(new Scalar(0, 255, 0)),
	BLUE(new Scalar(0, 0, 255));

	/**
	 * The RGB color associated with this {@link ColorCapturePipeline}.
	 */
	public final Scalar color;

	DetectedColor(Scalar color) {
		this.color = color;
	}
}
