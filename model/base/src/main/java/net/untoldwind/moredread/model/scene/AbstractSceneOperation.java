package net.untoldwind.moredread.model.scene;

public abstract class AbstractSceneOperation implements ISceneOperation {
	protected String label;

	public AbstractSceneOperation(final String label) {
		this.label = label;
	}

	@Override
	public String getLabel() {
		return label;
	}
}
