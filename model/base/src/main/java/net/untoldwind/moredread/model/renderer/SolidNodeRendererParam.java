package net.untoldwind.moredread.model.renderer;

public class SolidNodeRendererParam {
	private final boolean showNormalsOnSelected;
	private final boolean showBoundingBoxOnSelected;

	public SolidNodeRendererParam(final boolean showNormalsOnSelected,
			final boolean showBoundingBoxOnSelected) {
		this.showNormalsOnSelected = showNormalsOnSelected;
		this.showBoundingBoxOnSelected = showBoundingBoxOnSelected;
	}

	public boolean isShowNormalsOnSelected() {
		return showNormalsOnSelected;
	}

	public boolean isShowBoundingBoxOnSelected() {
		return showBoundingBoxOnSelected;
	}

}
