package net.untoldwind.moredread.model.scene;

import java.util.LinkedHashMap;
import java.util.Map;

import net.untoldwind.moredread.model.scene.change.CompositeChangeCommand;
import net.untoldwind.moredread.model.scene.change.ISceneChangeCommand;

import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.OperationHistoryFactory;

public class SceneChangeHandler {
	private final Scene scene;

	transient Thread lockOwner;
	transient boolean collectChanges;
	transient Map<String, ISceneChangeCommand> stage;

	SceneChangeHandler(final Scene scene) {
		this.scene = scene;
	}

	public synchronized void begin(final boolean collectChanges) {
		if (lockOwner != null) {
			throw new RuntimeException(
					"Scene is already manipulated by thread: " + lockOwner);
		}
		lockOwner = Thread.currentThread();
		this.collectChanges = collectChanges;
		if (stage == null) {
			this.stage = new LinkedHashMap<String, ISceneChangeCommand>();
		}
	}

	public synchronized void savepoint() {
		if (lockOwner == null) {
			throw new RuntimeException("Scene is not in change");
		} else if (lockOwner != Thread.currentThread()) {
			throw new RuntimeException(
					"Scene is manipulated by anowher thread: " + lockOwner);
		}

		lockOwner = null;
		for (final ISceneChangeCommand cmd : stage.values()) {
			cmd.updateCurrentValues(scene);
		}
	}

	public synchronized void commit() {
		if (lockOwner == null) {
			throw new RuntimeException("Scene is not in change");
		} else if (lockOwner != Thread.currentThread()) {
			throw new RuntimeException(
					"Scene is manipulated by anowher thread: " + lockOwner);
		}
		lockOwner = null;
		for (final ISceneChangeCommand cmd : stage.values()) {
			cmd.updateCurrentValues(scene);
		}

		if (stage.size() == 1) {
			queueCommand(stage.values().iterator().next());
		} else if (stage.size() > 1) {
			queueCommand(new CompositeChangeCommand(stage.values()));
		}
		stage = null;
	}

	public boolean isChangeAllowed() {
		return lockOwner == Thread.currentThread();
	}

	public void checkChangeAllowed() {
		if (lockOwner != Thread.currentThread()) {
			throw new RuntimeException(
					"Current thread is not allowed to perform changes");
		}
	}

	public void registerCommand(final ISceneChangeCommand command) {
		checkChangeAllowed();

		if (collectChanges) {
			synchronized (stage) {
				if (!stage.containsKey(command.getStageId())) {
					stage.put(command.getStageId(), command);
				}
			}
		}
	}

	void queueCommand(final ISceneChangeCommand command) {
		command.addContext(IOperationHistory.GLOBAL_UNDO_CONTEXT);
		getOperationHistory().add(command);
	}

	private IOperationHistory getOperationHistory() {
		return OperationHistoryFactory.getOperationHistory();
	}
}
