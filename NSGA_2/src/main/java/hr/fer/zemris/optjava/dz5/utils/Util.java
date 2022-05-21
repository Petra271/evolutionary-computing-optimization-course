package hr.fer.zemris.optjava.dz5.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import hr.fer.zemris.optjava.dz5.solution.Solution;

public class Util {

	static public void writeToFile(List<Solution> solutions, String solFilePath, String objFilePath)
			throws IOException {
		Path solFile = Paths.get(solFilePath);
		Path objFile = Paths.get(objFilePath);

		List<String> sols = solutions.stream().map(s -> s.toString()).collect(Collectors.toList());
		List<String> objs = solutions.stream().map(s -> Arrays.toString(s.getObjective())).collect(Collectors.toList());

		Files.write(solFile, sols, StandardCharsets.UTF_8);
		Files.write(objFile, objs, StandardCharsets.UTF_8);
	}
}
