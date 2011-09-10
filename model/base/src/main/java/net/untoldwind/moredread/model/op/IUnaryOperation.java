package net.untoldwind.moredread.model.op;

import net.untoldwind.moredread.model.mesh.IMesh;
import net.untoldwind.moredread.model.mesh.Mesh;

/**
 * Common interface of all unary mesh operations.
 * 
 * A unary mesh operation takes a single mesh and converts it into another.
 */
public interface IUnaryOperation {
	Mesh<?> perform(IMesh mesh);
}
