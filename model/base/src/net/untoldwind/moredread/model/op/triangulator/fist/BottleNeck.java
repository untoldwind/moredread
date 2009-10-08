package net.untoldwind.moredread.model.op.triangulator.fist;

import static net.untoldwind.moredread.model.op.triangulator.fist.Triangulator.ZERO;

class BottleNeck {

	static boolean checkArea(Triangulator triRef, int ind4, int ind5) {
		int ind1, ind2;
		int i0, i1, i2;
		double area = 0.0, area1 = 0, area2 = 0.0;

		i0 = triRef.fetchData(ind4);
		ind1 = triRef.fetchNextData(ind4);
		i1 = triRef.fetchData(ind1);

		while (ind1 != ind5) {
			ind2 = triRef.fetchNextData(ind1);
			i2 = triRef.fetchData(ind2);
			area = Numerics.stableDet2D(triRef, i0, i1, i2);
			area1 += area;
			ind1 = ind2;
			i1 = i2;
		}

		if (Numerics.le(area1, ZERO))
			return false;

		ind1 = triRef.fetchNextData(ind5);
		i1 = triRef.fetchData(ind1);
		while (ind1 != ind4) {
			ind2 = triRef.fetchNextData(ind1);
			i2 = triRef.fetchData(ind2);
			area = Numerics.stableDet2D(triRef, i0, i1, i2);
			area2 += area;
			ind1 = ind2;
			i1 = i2;
		}

		if (Numerics.le(area2, ZERO))
			return false;
		else
			return true;
	}

	// Yet another check needed in order to handle degenerate cases!
	static boolean checkBottleNeck(Triangulator triRef, int i1, int i2, int i3,
			int ind4) {
		int ind5;
		int i4, i5;
		boolean flag;

		i4 = i1;

		ind5 = triRef.fetchPrevData(ind4);
		i5 = triRef.fetchData(ind5);
		if ((i5 != i2) && (i5 != i3)) {
			flag = Numerics.pntInTriangle(triRef, i1, i2, i3, i5);
			if (flag)
				return true;
		}

		if (i2 <= i3) {
			if (i4 <= i5)
				flag = Numerics.segIntersect(triRef, i2, i3, i4, i5, -1);
			else
				flag = Numerics.segIntersect(triRef, i2, i3, i5, i4, -1);
		} else {
			if (i4 <= i5)
				flag = Numerics.segIntersect(triRef, i3, i2, i4, i5, -1);
			else
				flag = Numerics.segIntersect(triRef, i3, i2, i5, i4, -1);
		}
		if (flag)
			return true;

		ind5 = triRef.fetchNextData(ind4);
		i5 = triRef.fetchData(ind5);

		if ((i5 != i2) && (i5 != i3)) {
			flag = Numerics.pntInTriangle(triRef, i1, i2, i3, i5);
			if (flag)
				return true;
		}

		if (i2 <= i3) {
			if (i4 <= i5)
				flag = Numerics.segIntersect(triRef, i2, i3, i4, i5, -1);
			else
				flag = Numerics.segIntersect(triRef, i2, i3, i5, i4, -1);
		} else {
			if (i4 <= i5)
				flag = Numerics.segIntersect(triRef, i3, i2, i4, i5, -1);
			else
				flag = Numerics.segIntersect(triRef, i3, i2, i5, i4, -1);
		}

		if (flag)
			return true;

		ind5 = triRef.fetchNextData(ind4);
		i5 = triRef.fetchData(ind5);
		while (ind5 != ind4) {
			if (i4 == i5) {
				if (checkArea(triRef, ind4, ind5))
					return true;
			}
			ind5 = triRef.fetchNextData(ind5);
			i5 = triRef.fetchData(ind5);
		}

		return false;
	}
}
