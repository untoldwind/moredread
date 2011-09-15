package net.untoldwind.moredread.ui.dnd;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import net.untoldwind.moredread.model.scene.INode;
import net.untoldwind.moredread.model.state.BinaryStateReader;
import net.untoldwind.moredread.model.state.BinaryStateWriter;
import net.untoldwind.moredread.ui.MoreDreadUI;

import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;

public class NodeTransfer extends ByteArrayTransfer {
	private static NodeTransfer INSTANCE = new NodeTransfer();
	private static final String TYPE_NAME = "moredread-node-transfer-format";
	private static final int TYPEID = registerType(TYPE_NAME);

	public static NodeTransfer getInstance() {
		return INSTANCE;
	}

	private NodeTransfer() {

	}

	@Override
	protected int[] getTypeIds() {
		return new int[] { TYPEID };
	}

	@Override
	protected String[] getTypeNames() {
		return new String[] { TYPE_NAME };
	}

	@Override
	protected void javaToNative(final Object object,
			final TransferData transferData) {
		try {
			if (object instanceof INode) {
				final INode node = (INode) object;
				final ByteArrayOutputStream out = new ByteArrayOutputStream();
				final BinaryStateWriter writer = new BinaryStateWriter(out);
				writer.writeLong("nodeId", node.getNodeId());

				super.javaToNative(out.toByteArray(), transferData);
			}
		} catch (final Exception e) {
			MoreDreadUI.getDefault().log(e);
		}
	}

	@Override
	protected Object nativeToJava(final TransferData transferData) {
		try {
			final byte[] bytes = (byte[]) super.nativeToJava(transferData);
			final ByteArrayInputStream in = new ByteArrayInputStream(bytes);
			final BinaryStateReader reader = new BinaryStateReader(in);

			final long nodeId = reader.readLong();

			return MoreDreadUI.getDefault().getSceneHolder().getScene()
					.getNode(nodeId);
		} catch (final Exception e) {
			MoreDreadUI.getDefault().log(e);
		}

		return null;
	}

}
