package net.untoldwind.moredread.model.properties;

import net.untoldwind.moredread.model.scene.SpatialNode;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class SpatialNodePropertySource implements IPropertySource {
	private final SpatialNode node;

	protected static final String PROPERTY_TEXT = "text"; //$NON-NLS-1$

	private final Object PropertiesTable[][] = { { PROPERTY_TEXT,
			new TextPropertyDescriptor(PROPERTY_TEXT, "Name") }, //$NON-NLS-1$
	};

	public SpatialNodePropertySource(final SpatialNode node) {
		this.node = node;
	}

	@Override
	public Object getEditableValue() {
		return this;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		// Create the property vector.
		final IPropertyDescriptor[] propertyDescriptors = new IPropertyDescriptor[PropertiesTable.length];

		for (int i = 0; i < PropertiesTable.length; i++) {
			// Add each property supported.

			IPropertyDescriptor descriptor;

			descriptor = (IPropertyDescriptor) PropertiesTable[i][1];
			propertyDescriptors[i] = descriptor;
		}

		// Return it.
		return propertyDescriptors;
	}

	@Override
	public Object getPropertyValue(final Object id) {
		if (id.equals(PROPERTY_TEXT)) {
			return node.getName();
		}
		return null;
	}

	@Override
	public boolean isPropertySet(final Object id) {
		if (id.equals(PROPERTY_TEXT)) {
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
		// TODO Auto-generated method stub

	}

}
