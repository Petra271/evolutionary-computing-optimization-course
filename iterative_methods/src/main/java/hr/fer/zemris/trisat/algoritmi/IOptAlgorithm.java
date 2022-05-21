package hr.fer.zemris.trisat.algoritmi;

import java.util.Optional;
import hr.fer.zemris.trisat.BitVector;

public interface IOptAlgorithm {

	Optional<BitVector> solve(Optional<BitVector> initial);

}
