package net.untoldwind.moredread.model.op.triangulator.fist;

class Triangle extends Object {
	int v1, v2, v3; // This store the index into the list array.

	// Not the index into vertex pool yet!

	Triangle(int a, int b, int c) {
		v1 = a;
		v2 = b;
		v3 = c;
	}
}
