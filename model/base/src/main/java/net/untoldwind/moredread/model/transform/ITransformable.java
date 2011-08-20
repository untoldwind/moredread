package net.untoldwind.moredread.model.transform;

public interface ITransformable<T> {
	T transform(ITransformation transformation);
}
