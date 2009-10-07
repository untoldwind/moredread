package net.untoldwind.moredread.model.scene;

import java.io.IOException;
import java.lang.ref.WeakReference;

import com.jme.util.export.JMEExporter;
import com.jme.util.export.JMEImporter;
import com.jme.util.export.Savable;

public class SpatialNodeReference implements Savable {
	WeakReference<SpatialNode> nodeRef;

	public SpatialNodeReference(SpatialNode node) {
		nodeRef = new WeakReference<SpatialNode>(node);
	}

	public SpatialNode getNode() {
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
