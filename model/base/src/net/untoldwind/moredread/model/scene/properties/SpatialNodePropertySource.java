package net.untoldwind.moredread.model.scene.properties;

import net.untoldwind.moredread.model.scene.Scene;
import net.untoldwind.moredread.model.scene.AbstractSpatialNode;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class SpatialNodePropertySource implements IPropertySource {
	private final AbstractSpatialNode node;

	protected static final String PROPERTY_NAME = "name";

	private final Object PropertiesTable[][] = { { PROPERTY_NAME,
			new TextPropertyDescriptor(PROPERTY_NAME, "Name") }, };

	public SpatialNodePropertySource(final AbstractSpatialNode node) {
		this.node = node;
	}

	@Override
	public Object getEditableValue() {
		return node;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		// Create the property vector.
		final IPropertyDescriptor[] propertyDescriptors = new IPropertyDescriptor[PropertiesTable.length];

		for (int i = 0; i < PropertiesTable.length; i++) {
			// Add each property supported.

			PropertyDescriptor descriptor;

			descriptor = (PropertyDescriptor) PropertiesTable[i][1];
			descriptor.setCategory("Basic");
			propertyDescriptors[i] = descriptor;
		}

		// Return it.
		return propertyDescriptors;
	}

	@Override
	public Object getPropertyValue(final Object id) {
		if (PROPERTY_NAME.equals(id)) {
			return node.getName();
		}
		return null;
	}

	@Override
	public boolean isPropertySet(final Object id) {
		if (PROPERTY_NAME.equals(id)) {
			return true;
		}
		return false;
	}

	@Override
	public void resetPropertyValue(final Object id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPropertyValue(final Object id, final Object value) {
		final Scene scene = node.getScene();

		scene.getSceneChangeHandler().begin(true);

		try {
			if (PROPERTY_NAME.equals(id)) {
				node.setName(value.toString());
			}
		} finally {
			scene.getSceneChangeHandler().commit();
		}
	}
}
