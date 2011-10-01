package net.untoldwind.moredread.ui.options.generator;

import java.lang.reflect.Constructor;

import net.untoldwind.moredread.ui.MoreDreadUI;

import org.eclipse.core.runtime.IAdapterFactory;

public class GeneratorOptionViewAdapterFactory implements IAdapterFactory {
	private final Constructor<IGeneratorOptionView> constructor;

	@SuppressWarnings("unchecked")
	public GeneratorOptionViewAdapterFactory(final Class<?> generatorClass,
			final Class<?> optionViewClass) throws SecurityException,
			NoSuchMethodException {
		constructor = (Constructor<IGeneratorOptionView>) optionViewClass
				.getDeclaredConstructor(generatorClass);
		constructor.setAccessible(true);
	}

	@Override
	public Object getAdapter(final Object adaptableObject,
			@SuppressWarnings("rawtypes") final Class adapterType) {
		if (adapterType == IGeneratorOptionView.class) {
			try {
				return constructor.newInstance(adaptableObject);
			} catch (final Exception e) {
				MoreDreadUI.getDefault().log(e);
			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class[] getAdapterList() {
		return new Class[] { IGeneratorOptionView.class };
	}

}
