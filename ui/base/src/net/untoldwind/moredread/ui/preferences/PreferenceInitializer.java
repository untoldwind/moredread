package net.untoldwind.moredread.ui.preferences;

import net.untoldwind.moredread.ui.MoreDreadUI;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.SWT;
import org.eclipse.ui.PlatformUI;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initializeDefaultPreferences() {
		final IPreferenceStore store = MoreDreadUI.getDefault()
				.getPreferenceStore();

		store.setDefault(
				IPreferencesConstants.MODEL3DVIEW_SELECTED_SHOW_NORMALS, false);

		store.setDefault(
				IPreferencesConstants.MODEL3DVIEW_SELECTED_SHOW_BOUNDINGBOX,
				true);

		PreferenceConverter.setDefault(store,
				IPreferencesConstants.MODEL3DVIEW_BACKGROUND_COLOR, PlatformUI
						.getWorkbench().getDisplay().getSystemColor(
								SWT.COLOR_GRAY).getRGB());
	}

}
