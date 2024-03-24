public class Test {
	static beeColony bee=new beeColony();
	
	public static void main(String[] args) {
		int iter=0;
		
		bee.initial();
		bee.MemorizeBestSource();
		System.out.println("initial x="+bee.GlobalParams);
		System.out.println("initial GlobalMax="+bee.GlobalMax);
		
		for (iter=0;iter<bee.maxCycle;iter++)
		{
			bee.SendEmployedBees();
			bee.CalculateProbabilities();
			bee.SendOnlookerBees();
			bee.MemorizeBestSource();
			bee.SendScoutBees();
		}
		System.out.println("Final x="+bee.GlobalParams);
		System.out.println("Final Global Max="+bee.GlobalMax);
		
	}

}