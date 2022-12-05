package liquidoxygen.autonomous;

import lombok.RequiredArgsConstructor;
import org.opencv.core.Scalar;

@RequiredArgsConstructor
public enum DetectedColor {
	RED(new Scalar(255, 0, 0)),
	GREEN(new Scalar(0, 255, 0)),
	BLUE(new Scalar(0, 0, 255));

	public final Scalar color;
}
