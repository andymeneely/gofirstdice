package org.chaoticbits.gofirst.genetic;

import java.util.ArrayList;
import java.util.List;

public class PermutationCrossover {

	public List<Integer> crossOver(List<Integer> first, List<Integer> second, int cut) {
		List<Integer> child = new ArrayList<Integer>();
		for (int i = 0; i < cut; i++) {
			child.add(first.get(i));
		}
		for (int j = 0; j < second.size(); j++) {
			if (!child.contains(second.get(j)))
				child.add(second.get(j));
		}
		return child;
	}
}
