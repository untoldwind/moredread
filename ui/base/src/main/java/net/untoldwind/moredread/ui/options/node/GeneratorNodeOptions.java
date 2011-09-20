package net.untoldwind.moredread.ui.options.node;

import net.untoldwind.moredread.model.scene.GeneratorNode;
import net.untoldwind.moredread.ui.options.IOptionView;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.swt.widgets.Composite;

public class GeneratorNodeOptions implements IOptionView {
	private final GeneratorNode node;

	IOptionView generatorOptions;

	GeneratorNodeOptions(final GeneratorNode node) {
		this.node = node;
	}

	@Override
	public void createControls(final Composite parent) {
		generatorOptions = (IOptionView) node.getMeshGenerator().getAdapter(
				IOptionView.class);

		if (generatorOptions != null) {
			generatorOptions.createControls(parent);
		}
	}

	@Override
	public void dispose() {
		if (generatorOptions != null) {
			generatorOptions.dispose();
		}
	}

	public static class Factory implements IAdapterFactory {

		@Override
		public Object getAdapter(final Object adaptableObject,
				@SuppressWarnings("rawtypes") final Class adapterType) {
			if (adapterType == IOptionView.class) {
				if (adaptableObject instanceof GeneratorNode) {
					return new GeneratorNodeOptions(
							(GeneratorNode) adaptableObject);
				}

			}
			return null;
		}

		@SuppressWarnings("rawtypes")
		@Override
		public Class[] getAdapterList() {
			return new Class[] { IOptionView.class };
		}
	}
}
