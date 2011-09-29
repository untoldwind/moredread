package net.untoldwind.moredread.ui.canvas;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.untoldwind.moredread.model.math.Camera;
import net.untoldwind.moredread.ui.input.event.ICameraUpdate;
import net.untoldwind.moredread.ui.input.event.IUIInputReceiver;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.opengl.GLCanvas;
import org.eclipse.swt.opengl.GLData;
import org.eclipse.swt.widgets.Composite;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ARBMultisample;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import com.jme.input.InputSystem;
import com.jme.system.DisplaySystem;
import com.jme.system.canvas.JMECanvas;
import com.jme.system.canvas.JMECanvasImplementor;
import com.jme.system.lwjgl.LWJGLDisplaySystem;

public class MDCanvas extends GLCanvas implements JMECanvas, IUIInputReceiver {

	private static final Logger logger = Logger.getLogger(MDCanvas.class
			.getName());

	private final AtomicInteger renderQueueCount = new AtomicInteger(0);
	private MDCanvasImplementor impl;

	private boolean updateInput = false;

	private boolean inited = false;

	private final Runnable renderRunner;

	private boolean shouldAutoKillContext = true;
	private boolean glInitialized = false;
	private boolean drawWhenDirty = false;
	private boolean dirty = true;
	private final boolean doUpdateOnly = false;

	public MDCanvas(final Composite parent, final int style, final GLData data)
			throws LWJGLException {
		super(parent, style, data);
		renderRunner = new Runnable() {

			public void run() {
				try {
					if (!inited) {
						init();
					}
					doRender();
				} finally {
					renderQueueCount.decrementAndGet();
				}
			}
		};
	}

	public void init() {

		if (glInitialized) {
			return;
		}
		glInitialized = true;

		try {
			final LWJGLDisplaySystem display = (LWJGLDisplaySystem) DisplaySystem
					.getDisplaySystem();
			display.switchContext(this);
			setCurrent();
			GLContext.useContext(this);

			// Complete canvas initialization.
			final Point size = this.getSize();
			display.initForCanvas(Math.max(size.x, 1), Math.max(size.y, 1));

			// Perform game initialization.
			impl.doSetup();

			// TODO Should this be moved into initForCanvas?
			if (DisplaySystem.getDisplaySystem().getMinSamples() != 0
					&& GLContext.getCapabilities().GL_ARB_multisample) {
				GL11.glEnable(ARBMultisample.GL_MULTISAMPLE_ARB);
			}

			inited = true;
		} catch (final Exception e) {
			logger.log(Level.SEVERE, "Exception in initGL()", e);
		}
	}

	private void doRender() {
		if (!isDisposed()) {

			try {
				setCurrent();

				((LWJGLDisplaySystem) DisplaySystem.getDisplaySystem())
						.switchContext(this);
				GLContext.useContext(this);

				if (!doUpdateOnly) {
					if (updateInput) {
						InputSystem.update();
					}

					impl.getTaskQueue().executeAll();

					impl.doUpdate();

					if (!drawWhenDirty || dirty) {
						impl.doRender();

						swapBuffers();
					}
				} else {
					impl.doUpdate();
				}
				dirty = false;
			} catch (final LWJGLException e) {
				logger.log(Level.SEVERE, "Exception in render()", e);
			}
		}
	}

	@Override
	public void dispose() {
		if (shouldAutoKillContext) {
			glInitialized = false;
			super.dispose();
		}
	}

	public void setAutoKillGfxContext(final boolean shouldAutoKillGfxContext) {
		this.shouldAutoKillContext = shouldAutoKillGfxContext;
	}

	public boolean shouldAutoKillGfxContext() {
		return shouldAutoKillContext;
	}

	public void killGfxContext() {
		glInitialized = false;
		super.dispose();
	}

	public int getTargetSyncRate() {
		return 0;
	}

	public void makeDirty() {
		dirty = true;
	}

	public void queueRender() {
		if (renderQueueCount.get() < 2) {
			renderQueueCount.incrementAndGet();

			getDisplay().asyncExec(renderRunner);
		}
	}

	public void queueCameraUpdate(final ICameraUpdate update) {
		final Callable<?> exe = new Callable<Object>() {
			public Object call() {
				if (impl != null && impl.getRenderer() != null) {
					final Camera camera = impl.getCamera();

					update.updateComera(camera);
					camera.apply();
				}
				return null;
			}
		};
		impl.getTaskQueue().enqueue(exe);

		queueRender();
	}

	public void setDrawWhenDirty(final boolean whenDirty) {
		this.drawWhenDirty = whenDirty;
	}

	public boolean isDrawWhenDirty() {
		return drawWhenDirty;
	}

	public void setTargetRate(final int fps) {
	}

	public boolean isUpdateInput() {
		return updateInput;
	}

	public void setUpdateInput(final boolean doUpdate) {
		updateInput = doUpdate;
	}

	public void setImplementor(final JMECanvasImplementor impl) {
		this.impl = (MDCanvasImplementor) impl;
	}
}
