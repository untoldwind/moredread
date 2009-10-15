/**
 * 
 */
package net.untoldwind.moredread.ui.preferences;

import net.untoldwind.moredread.ui.MoreDreadUI;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * @author junglas
 */
public class Model3DViewPreferencesPage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(final IWorkbench workbench) {
		setPreferenceStore(MoreDreadUI.getDefault().getPreferenceStore());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void createFieldEditors() {

		addField(new BooleanFieldEditor(
				IPreferencesConstants.MODEL3DVIEW_SELECTED_SHOW_NORMALS,
				"Show normals on selected objects", getFieldEditorParent()));
		addField(new BooleanFieldEditor(
				IPreferencesConstants.MODEL3DVIEW_SELECTED_SHOW_BOUNDINGBOX,
				"Show bounding box on selected objects", getFieldEditorParent()));
		addField(new ColorFieldEditor(
				IPreferencesConstants.MODEL3DVIEW_BACKGROUND_COLOR,
				"Background", getFieldEditorParent()));
	}

}
