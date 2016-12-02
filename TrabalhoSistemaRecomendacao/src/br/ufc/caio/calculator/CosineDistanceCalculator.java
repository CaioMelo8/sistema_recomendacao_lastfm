package br.ufc.caio.calculator;

public class CosineDistanceCalculator {
	public double calculateDistance(Double[] vector1, Double [] vector2) throws ArithmeticException{
		double playCount1, playCount2;
		Double sumProduct = 0d, sumSqrtV1 = 0d, sumSqrtV2 = 0d;		
		
		for (int i = 0; i < vector1.length && i < vector2.length; i++){
			playCount1 = vector1[i];
			playCount2 = vector2[i];
			
			sumProduct += playCount1 * playCount2;
			sumSqrtV1 += Math.pow(playCount1, 2);
			sumSqrtV2 += Math.pow(playCount2, 2);
		}
		
		sumSqrtV1 = Math.sqrt(sumSqrtV1);
		sumSqrtV2 = Math.sqrt(sumSqrtV2);
		
		if (sumSqrtV1.equals(0d) || sumSqrtV2.equals(0d))
			throw new ArithmeticException("Division by zero!");
		
		return sumProduct / (sumSqrtV1 * sumSqrtV2);
	}
}