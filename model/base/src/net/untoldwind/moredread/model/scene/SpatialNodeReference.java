package net.untoldwind.moredread.model.scene;

import java.io.IOException;
import java.lang.ref.WeakReference;

import com.jme.util.export.JMEExporter;
import com.jme.util.export.JMEImporter;
import com.jme.util.export.Savable;

public class SpatialNodeReference implements Savable {
	WeakReference<AbstractSpatialNode> nodeRef;

	public SpatialNodeReference(AbstractSpatialNode node) {
		nodeRef = new WeakReference<AbstractSpatialNode>(node);
	}

	public AbstractSpatialNode getNode() {
		return nodeRef.get();
	}

	public Class<?> getClassTag() {
		return getClass();
	}

	public void read(JMEImporter im) throws IOException {
	}

	public void write(JMEExporter ex) throws IOException {
	}

}
