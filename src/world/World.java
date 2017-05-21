package world;

import patch.Patch;
import person.Agent;
import person.Cop;
import person.Person;

import java.util.HashSet;
import java.util.Random;

/**
 * The driver used to simulate the NetLogo_Rebellion.
 * This class describes the attributes and contains the behaviors of
 * the world of the Netlogo model.
 */

public class World {


		//
		public static int numOfPathes;

		//
		private int numOfCops;

		//
		private int numOfAgents;

		//
		public static double governmentLegitimacy;

		//
		public static int maxJailTerm;

		//
		public static boolean movement;

		//
		public static int vision;

		//
		private boolean watchOne;

		//
		private int ticks;

		//
		public static Patch[][] patches;

		//
		public static Agent[] agents;

		public static Cop[] cops;

		/**
		 * The constructor of world class.
		 * @param numOfPathes
		 * @param numOfAgents
		 * @param numOfCops
		 * @param governmentLegitimacy
		 * @param maxJailTerm
		 * @param movement
		 * @param vision
		 * @param watchOne
		 * @param ticks
		 */
		public World(int numOfPathes, int numOfAgents, int numOfCops,
					 double governmentLegitimacy, int maxJailTerm, boolean movement,
					 int vision, boolean watchOne, int ticks) {
			this.numOfPathes = numOfPathes;
			this.numOfAgents = numOfAgents;
			this.numOfCops = numOfCops;
			this.governmentLegitimacy = governmentLegitimacy;
			this.maxJailTerm = maxJailTerm;
			this.movement = movement;
			this.vision = vision;
			this.watchOne = watchOne;
			this.ticks = ticks;
		}

		/**
		 * To setup the model to make it ready to run.
		 */
		public void setup(){
			//Initialize the patches in the world.
			patches = new Patch[numOfPathes][numOfPathes];
			for(int i = 0; i < patches.length; i ++){
				for(int j = 0; j < patches[i].length; j ++){
					patches[i][j] = new Patch(i+1,j+1);
				}
			}

			//Generate agents and cops.
			agents = new Agent[numOfAgents];
			for(int k = 0; k < agents.length; k ++){
				agents[k] = generateAgent();
			}

			cops = new Cop[numOfCops];
			for(int k = 0; k < agents.length; k ++){
				cops[k] = generateCop();
			}
		}

		public void go(int ticks){

			for(int i = ticks; i > 0; i--){

				for(Agent agent : agents){
					if(agent.getJailTerm() == 0) agent.determinBehavior();
					else agent.reduceJailTerm();
				}

				for(Cop cop : cops){
					cop.enforce();
				}

				randMove(agents);

				randMove(cops);

			}

		}

		public Agent generateAgent(){

			Patch currentPatch = randPatch();

			Agent agent= new Agent(currentPatch, false);

			currentPatch.setPerson(agent);

			return agent;
		}

		public Cop generateCop(){

			Patch currentPatch = randPatch();

			Cop cop= new Cop(currentPatch);

			currentPatch.setPerson(cop);

			return cop;
		}

		public static Patch randPatch(){

			Patch patch;

			while(true) {
				patch = patches[randInt(0,numOfPathes-1)][randInt(0,numOfPathes-1)];
				if(patch.getPerson() == null) break;
			}

			return patch;
		}


		public static int randInt(int min, int max) {

			// Usually this can be a field rather than a method variable
			Random rand = new Random();

			// nextInt is normally exclusive of the top value,
			// so add 1 to make it inclusive
			int randomNum = rand.nextInt((max - min) + 1) + min;

			return randomNum;
		}

		private void randMove(Person[] array){

			HashSet<Integer> remaining = new HashSet<Integer>();
			for (int i = 0; i < array.length; i++) {
				remaining.add(i);
			}
			int step = 0;
			boolean isAgentArray = false;
			if(array[0] instanceof Agent) isAgentArray = true;
			//select from remaining index when remaining is not empty
			while (!remaining.isEmpty()) {
				int index = randInt(0,remaining.size()-1);
				//move agents and cops here
				if(isAgentArray)
				agents[index].move();
				else cops[index].move();
				remaining.remove(index);
				step++;
			}
			System.out.println("Finished after " + step + " iterations.");
		}



}
