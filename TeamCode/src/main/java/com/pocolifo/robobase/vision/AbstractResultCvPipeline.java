package com.pocolifo.robobase.vision;

import lombok.Getter;
import org.openftc.easyopencv.OpenCvPipeline;

/**
 * An {@link OpenCvPipeline} which has a result.
 *
 * @param <T> The type of the result from the pipeline.
 * @author youngermax
 */
public abstract class AbstractResultCvPipeline<T> extends OpenCvPipeline {
	/**
	 * The result of the webcam processor.
	 */
	@Getter protected T result;
}
