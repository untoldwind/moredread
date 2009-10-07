package net.untoldwind.moredread.ui.canvas;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.opengl.GLData;
import org.eclipse.swt.widgets.Composite;
import org.lwjgl.LWJGLException;

import com.jme.system.DisplaySystem;
import com.jme.system.JmeException;
import com.jme.system.canvas.CanvasConstructor;
import com.jme.system.canvas.JMECanvas;
import com.jmex.swt.lwjgl.LWJGLSWTConstants;

public class MDCanvasConstructor implements CanvasConstructor {

	public JMECanvas makeCanvas(final HashMap<String, Object> props) {
		try {
			final Composite parent = (Composite) props
					.get(LWJGLSWTConstants.PARENT);

			final GLData data = new GLData();
			data.doubleBuffer = true;

			Integer style = (Integer) props.get(LWJGLSWTConstants.STYLE);
			if (style == null) {
				style = SWT.NONE;
			}

			final Integer depthBits = (Integer) props
					.get(LWJGLSWTConstants.DEPTH_BITS);
			if (depthBits != null) {
				data.depthSize = depthBits;
			} else {
				data.depthSize = DisplaySystem.getDisplaySystem()
						.getMinDepthBits();
			}

			final Integer alphaBits = (Integer) props
					.get(LWJGLSWTConstants.ALPHA_BITS);
			if (alphaBits != null) {
				data.alphaSize = alphaBits;
			} else {
				data.alphaSize = DisplaySystem.getDisplaySystem()
						.getMinAlphaBits();
			}

			final Integer stencilBits = (Integer) props
					.get(LWJGLSWTConstants.STENCIL_BITS);
			if (alphaBits != null) {
				data.stencilSize = stencilBits;
			} else {
				data.stencilSize = DisplaySystem.getDisplaySystem()
						.getMinStencilBits();
			}

			// NOTE: SWT does not actually implement samples yet.
			final Integer samples = (Integer) props
					.get(LWJGLSWTConstants.SAMPLES);
			if (samples != null) {
				data.samples = samples;
			} else {
				data.samples = DisplaySystem.getDisplaySystem()
						.getMinStencilBits();
			}

			final MDCanvas canvas = new MDCanvas(parent, style, data);

			canvas.addPaintListener(new PaintListener() {
				public void paintControl(final PaintEvent e) {
					canvas.makeDirty();
				}
			});

			return canvas;
		} catch (final LWJGLException e) {
			e.printStackTrace();
			throw new JmeException("Unable to create lwjgl-swt canvas: "
					+ e.getLocalizedMessage());
		}
	}

}
