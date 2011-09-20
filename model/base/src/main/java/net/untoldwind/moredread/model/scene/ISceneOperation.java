package net.untoldwind.moredread.model.scene;

public interface ISceneOperation {
	String getLabel();

	void perform(Scene scene);
}
