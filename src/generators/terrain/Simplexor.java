package generators.terrain;

public class Simplexor {
	private int octaves;
	private double roughness;
	private double scale;

	public Simplexor(int octaves, double roughness, double scale) {
		this.octaves = octaves; // Number of Layers combined together to get a natural looking surface
		this.roughness = roughness; // Increasing the of the range between -1 and 1, causing higher values eg more
									// rough terrain
		this.scale = scale; // Overall scaling of the terrain
	}
	
	public double[][] createWorld(int width, int height) {
		return generateOctavedSimplexNoise(width, height);
	}

	private double[][] generateOctavedSimplexNoise(int width, int height) {
		double[][] totalNoise = new double[width][height];
		double layerFrequency = scale;
		double layerWeight = 1;

		// Summing up all octaves, the whole expression makes up a weighted average
		// computation where the noise with the lowest frequencies have the least effect

		for (int octave = 0; octave < octaves; octave++) {
			// Calculate single layer/octave of simplex noise, then add it to total noise
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					totalNoise[x][y] += SimplexNoise.noise(x * layerFrequency, y * layerFrequency) * layerWeight;
				}
			}
		
			// Increase variables with each incrementing octave
			layerFrequency *= 2;
			layerWeight *= roughness;

		}
		return totalNoise;
	}
	
	public double genPoint(int x, int y) {
		double total = 0;
		double layerFrequency = scale;
		double layerWeight = 1;
		for(int octave = 0; octave < octaves; octave++) {
			total += SimplexNoise.noise(x * layerFrequency, y * layerFrequency) * layerWeight;
			layerFrequency *= 2;
			layerWeight *= roughness;
		}
		return total;
	}
}
