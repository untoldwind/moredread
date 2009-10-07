package net.untoldwind.moredread.model.renderer;

public class SolidNodeRendererParam {
	private final boolean showNormalsOnSelected;

	public SolidNodeRendererParam(final boolean showNormalsOnSelected) {
		this.showNormalsOnSelected = showNormalsOnSelected;
	}

	public boolean isShowNormalsOnSelected() {
		return showNormalsOnSelected;
	}

}
