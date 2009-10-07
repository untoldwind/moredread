package net.untoldwind.moredread.model.triangulator.fist;

/**
 * Original license information:
 * 
 * ----------------------------------------------------------------------
 * 
 * The reference to Fast Industrial Strength Triangulation (FIST) code in this
 * release by Sun Microsystems is related to Sun's rewrite of an early version
 * of FIST. FIST was originally created by Martin Held and Joseph Mitchell at
 * Stony Brook University and is incorporated by Sun under an agreement with The
 * Research Foundation of SUNY (RFSUNY). The current version of FIST is
 * available for commercial use under a license agreement with RFSUNY on behalf
 * of the authors and Stony Brook University. Please contact the Office of
 * Technology Licensing at Stony Brook, phone 631-632-9009, for licensing
 * information.
 * 
 * ----------------------------------------------------------------------
 */

class HeapNode {
	int index, prev, next;
	double ratio;

	HeapNode() {
	}

	void copy(HeapNode hNode) {
		index = hNode.index;
		prev = hNode.prev;
		next = hNode.next;
		ratio = hNode.ratio;
	}
}
