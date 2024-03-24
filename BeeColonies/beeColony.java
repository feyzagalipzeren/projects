import java.lang.Math;

public  class beeColony {



	/* Control Parameters of ABC algorithm*/
	int NP=50; /* The number of colony size (employed bees+onlooker bees)*/
	int FoodNumber = NP/2; /*The number of food sources equals the half of the colony size*/
	int limit = 200;  /*A food source which could not be improved through "limit" trials is abandoned by its employed bee*/
	int maxCycle = 5000; /*The number of cycles for foraging {a stopping criteria}*/
	/* Problem specific variables*/

	double lb = 0.0; /*lower bound of the parameters. */
	double ub = 1.0; /*upper bound of the parameters. lb and ub can be defined as arrays for the problems of which parameters have different bounds*/




	int dizi1[]=new int[10];
	double Foods[]=new double[FoodNumber];        /*Foods is the population of food sources. Each row of Foods matrix is a vector holding D parameters to be optimized. The number of rows of Foods matrix equals to the FoodNumber*/
	double f[]=new double[FoodNumber];        /*f is a vector holding objective function values associated with food sources */
	double fitness[]=new double[FoodNumber];      /*fitness is a vector holding fitness (quality) values associated with food sources*/
	double trial[]=new double[FoodNumber];         /*trial is a vector holding trial numbers through which solutions can not be improved*/
	double prob[]=new double[FoodNumber];          /*prob is a vector holding probabilities of food sources (solutions) to be chosen*/
	double solution;            /*New solution (neighbour) produced by v_{ij}=x_{ij}+\phi_{ij}*(x_{kj}-x_{ij}) j is a randomly chosen parameter and k is a randomlu chosen solution different from i*/
	
                   
	double ObjValSol;              /*Objective function value of new solution*/
	double FitnessSol;              /*Fitness value of new solution*/
	int neighbour, param2change;                   /*param2change corrresponds to j, neighbour corresponds to k in equation v_{ij}=x_{ij}+\phi_{ij}*(x_{kj}-x_{ij})*/

	double GlobalMax;                       /*Optimum solution obtained by ABC algorithm*/
	double GlobalParams;                   /*Parameters of the optimum solution*/   
	         /*GlobalMaxs holds the GlobalMax of each run in multiple runs*/
	double r; /*a random number in the range [0,1)*/

	/*Fitness function*/
	double CalculateFitness(double fun) 
	 {
		 double result=0;
		 if(fun>=0)
		 {
			 result=1/(fun+1);
		 }
		 else
		 {
			 
			 result=1+Math.abs(fun);
		 }
		 return result;
	 }

	/*The best food source is memorized*/
	void MemorizeBestSource() // food sourceların içinden evali en buyuk olanı bulur. global max en iyi çözüm.
	{
	   int i;
	    
		for(i=0;i<FoodNumber;i++)
		{
			if(f[i] > GlobalMax)
			{
		        GlobalMax=f[i];
		        GlobalParams=Foods[i];// lokasyon tuttar . hangi x değeri için en büyük sonuca ulaştk
		    }
		}
		
	 }

	/*Variables are initialized in the range [lb,ub]. If each parameter has different range, use arrays lb[j], ub[j] instead of lb and ub */
	/* Counters of food sources are also initialized in this function*/


	void init(int index)
	{
	    r = ( (double)Math.random()*32767 / ((double)32767+(double)(1)) );
	    Foods[index]=r*(ub-lb)+lb;		//food source location
		solution=Foods[index];
		
		f[index]=calculateFunction(solution);		//eval()
		fitness[index]=CalculateFitness(f[index]);	//çözümün kalitesi. küçük çıkmalı iyi olması için???
		trial[index]=0;
	}


	/*All food sources are initialized */
	void initial()
	{
		int i;
		for(i=0;i<FoodNumber;i++)
		{
			init(i);
		}
		GlobalMax=f[0];		// initial global max degerim
	    GlobalParams=Foods[0];
	}

	void SendEmployedBees()
	{
	  int i,j;
	  /*Employed Bee Phase*/
	   for (i=0;i<FoodNumber;i++)
	   {
	        /*A randomly chosen solution is used in producing a mutant solution of the solution i*/
	        r = ((double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
	        neighbour=(int)(r*FoodNumber);

	        /*v_{ij}=x_{ij}+\phi_{ij}*(x_{kj}-x_{ij}) */
	        r = ( (double)Math.random()*32767 / ((double)(32767)+(double)(1)));
	        solution=Foods[i]+(Foods[i]-Foods[neighbour])*(r-0.5)*2;

	        /*if generated parameter value is out of boundaries, it is shifted onto the boundaries*/
	        if (solution<lb)
	           solution=lb;
	        if (solution>ub)
	           solution=ub;
	        ObjValSol=calculateFunction(solution);
	        FitnessSol=CalculateFitness(ObjValSol);
	      
	        /*a greedy selection is applied between the current solution i and its mutant*/
	        if (FitnessSol<fitness[i])
	        {
		        /*If the mutant solution is better than the current solution i, replace the solution with the mutant and reset the trial counter of solution i*/
		        trial[i]=0;
		        Foods[i]=solution;
		        f[i]=ObjValSol;
		        fitness[i]=FitnessSol;
	        }
	        else
	        {   /*if the solution i can not be improved, increase its trial counter*/
	            trial[i]=trial[i]+1;
	        }


	        }

	        /*end of employed bee phase*/

	}

	/* A food source is chosen with the probability which is proportioal to its quality*/
	/*Different schemes can be used to calculate the probability values*/
	/*For example prob(i)=fitness(i)/sum(fitness)*/
	/*or in a way used in the metot below prob(i)=a*fitness(i)/max(fitness)+b*/
	/*probability values are calculated by using fitness values and normalized by dividing maximum fitness value*/
	void CalculateProbabilities()
	{
	     int i;
	     double minfit;
	     minfit=fitness[0];
	     for (i=1;i<FoodNumber;i++)
	     {
	        if (fitness[i]<minfit)
	           minfit=fitness[i];
	      }

	 for (i=0;i<FoodNumber;i++)
	 {
	     prob[i]=(0.9*(minfit/fitness[i]))+0.1;//yani fitness ı küçük olanın prob u küçük çıkcak ama prob yüksek olmalı fitness küçük için aslında
	 }

	}

	void SendOnlookerBees()
	{

	  int i,j,t;
	  i=0;
	  t=0;
	  /*onlooker Bee Phase*/
	  while(t<FoodNumber)
	  {

	        r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
	        if(r<prob[i]) /*choose a food source depending on its probability to be chosen*/
	        {        
	        t++;
	        /*A randomly chosen solution is used in producing a mutant solution of the solution i*/
	        r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
	        neighbour=(int)(r*FoodNumber);

	        /*Randomly selected solution must be different from the solution i*/        
	        while(neighbour == i)
	        {
	        r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
	        neighbour=(int)(r*FoodNumber);
	        }
	       

	        /*v_{ij}=x_{ij}+\phi_{ij}*(x_{kj}-x_{ij}) */
	        r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
	        solution=Foods[i]+(Foods[i]-Foods[neighbour])*(r-0.5)*2;

	        /*if generated parameter value is out of boundaries, it is shifted onto the boundaries*/
	        if (solution<lb)
	           solution=lb;
	        if (solution>ub)
	           solution=ub;
	        ObjValSol=calculateFunction(solution);
	        FitnessSol=CalculateFitness(ObjValSol);
	        
	        /*a greedy selection is applied between the current solution i and its mutant*/
	        if (FitnessSol<fitness[i])
	        {
	        /*If the mutant solution is better than the current solution i, replace the solution with the mutant and reset the trial counter of solution i*/
		        trial[i]=0;
		        
		        Foods[i]=solution;
		        f[i]=ObjValSol;
		        fitness[i]=FitnessSol;
	        }
	        else
	        {   /*if the solution i can not be improved, increase its trial counter*/
	            trial[i]=trial[i]+1;
	        }
	        } /*if */
	        i++;
	        if (i==FoodNumber)
	     	  i=0;
	        }/*while*/
	        /*end of onlooker bee phase     */
	}

	/*determine the food sources whose trial counter exceeds the "limit" value. In Basic ABC, only one scout is allowed to occur in each cycle*/
	void SendScoutBees()
	{
		int maxtrialindex,i;
		maxtrialindex=0;
		for (i=1;i<FoodNumber;i++)
		{
		   if (trial[i]>trial[maxtrialindex])
		         maxtrialindex=i;
		}
		if(trial[maxtrialindex]>=limit)
		{
			init(maxtrialindex);
		}
	}


	
	

double calculateFunction(double sol)
{
	return Function(sol);	
}

double Function(double sol)
{
	 double top=0;
	 double degrees = 45.0;
	    double radians = Math.toRadians(degrees);
	
	
		 top= Math.pow(2,-2*(Math.pow((sol-0.1)/0.9,2)))*Math.pow(Math.sin(5*radians*sol),6);
				 
	 return top;
}
	 

}