package net.untoldwind.moredread.ui.tools;

public class ActiveToolChangedEvent {
	private final IToolController toolController;
	private final IToolDescriptor activeTool;

	public ActiveToolChangedEvent(final IToolController toolController,
			final IToolDescriptor activeTool) {
		this.toolController = toolController;
		this.activeTool = activeTool;
	}

	public IToolController getToolController() {
		return toolController;
	}

	public IToolDescriptor getActiveTool() {
		return activeTool;
	}

}
