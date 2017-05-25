package world;

import patch.Patch;
import person.Agent;
import person.Cop;
import person.Person;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.util.*;
import java.nio.file.Paths;
import java.util.logging.Logger;

/**
 * The driver used to simulate the NetLogo_Rebellion.
 * This class describes the attributes and contains the behaviors of
 * the world of the Netlogo model.
 */

public class World {


		//The number of patches in a row.
		public static int numOfPathes;

		//The number of cops.
		private int numOfCops;

		//The number of agents.
		private int numOfAgents;

		//The governmentLegitimacy.
		public static double governmentLegitimacy;

		//The maxJailTerm.
		public static int maxJailTerm;

		//Whether the agents can move or not.
		public static boolean movement;

		//The distance of the vision.
		public static int vision;

		//The number of ticks.
		private int ticks;

		//Whether the extension is turned on.
		private boolean extension;

		//Whether the graduallyChangeGov is turned on.
		private boolean graduallyChangeGov;

		//Whether the sharplyChangGov is turned on.
		private boolean sharplyChangGov;

		//The patches in the world.
		public static Patch[][] patches;

		//The agents in the world.
		public static Agent[] agents;

		//The cops in the world.
		public static Cop[] cops;

		//The number of quietAgents.
		public static int quietAgent = 0;

		//The number of jailedAgents.
		public static int jailedAgent = 0;

		//The number of activeAgents.
		public static int activeAgent = 0;

		Logger logger = Logger.getLogger("World");

		/**
		 * The constructor of world class.
		 * @param numOfPathes
		 * @param numOfAgents
		 * @param numOfCops
		 * @param governmentLegitimacy
		 * @param maxJailTerm
		 * @param movement
		 * @param vision
		 * @param ticks
		 */
		public World(int numOfPathes, int numOfAgents, int numOfCops,
					 double governmentLegitimacy, int maxJailTerm,
					 boolean movement, int vision, int ticks, boolean extension,
					 boolean graduallyChangeGov, boolean sharplyChangGov)
					 throws IOException {
			this.numOfPathes = numOfPathes;
			this.numOfAgents = numOfAgents;
			this.numOfCops = numOfCops;
			this.governmentLegitimacy = governmentLegitimacy;
			this.maxJailTerm = maxJailTerm;
			this.movement = movement;
			this.vision = vision;
			this.ticks = ticks;
			this.extension = extension;
			this.graduallyChangeGov = graduallyChangeGov;
			this.sharplyChangGov = sharplyChangGov;
		}

		/**
		 * To setup the model to make it ready to run.
		 */
		public void setup(){

			logger.info("begin setting up the patches");

			//Initialize the patches in the world.
			patches = new Patch[numOfPathes][numOfPathes];
			for(int i = 0; i < patches.length; i ++){
				for(int j = 0; j < patches[i].length; j ++){
					patches[i][j] = new Patch(i,j);
					//logger.info("generated x = " + i + "y = " + j);
				}
			}

			logger.info("begin setting up the agents");

			//Generate agents and cops.
			agents = new Agent[numOfAgents];
			for(int k = 0; k < numOfAgents; k ++){
				agents[k] = generateAgent();
			}

			logger.info("finished setting up the agents");

			logger.info("begin setting up the cops");
			cops = new Cop[numOfCops];
			for(int k = 0; k < numOfCops; k ++){
				cops[k] = generateCop();
			}

			logger.info("finished setting up the cops");

		}

		/**
		 * Run the world.
		 * @param ticks
		 * @throws IOException
		 */
		public void go(int ticks) throws IOException {

			logger.info("begin run the world");

			//create the excel file
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Rebellion");
			Map<Integer, Object[]> data = new HashMap<>();
			data.put(1, new Object[] {"Tick No.", "Quiet Agents",
				   					  "Jailed Agents","Active Agents"});

			//start running the world
			int it= 0;
			for(int i = ticks; i > 0; i--){
				// print out the pic
				printPatch();

				//calculate the agents for the output excel.
				resetCount();
				countAgents();

				// M rule
				logger.info("agents are moving randomly");
				randMove(agents);

				logger.info("cops are moving randomly");
				randMove(cops);

				// special modes, reduce gov gradually
				if(it > 50 && this.graduallyChangeGov){
					graduallyChange();
				}

				// special modes, reduce gov sharply
				if(it == 50 && this.sharplyChangGov){
					sharplyChange();
				}

				//shuffle the agents and cops
				Collections.shuffle(Arrays.asList(agents));
				Collections.shuffle(Arrays.asList(cops));

				// A rule
				for(Agent agent : agents){
					if(agent.getJailTerm() == 0){
						if(this.extension) agent.extensionBehavior();
						else agent.determinBehavior();
					}
				}

				// C rule
				for(Cop cop : cops){
					cop.enforce();
				}
				//Reduce the jail term of jailed agents.
				for(Agent agent : agents){
					if(agent.getJailTerm() > 0) agent.reduceJailTerm();
				}
				
				it ++;
				//logger.info("world running iteration it :" + it);

				//put the data into the row
				data.put(it+1, new Object[] {it, quietAgent, jailedAgent
											,activeAgent});

			}

			logger.info("finished running the world");


			//set up rows and cells and sort keys
			List<Integer> keys = new ArrayList<>(data.keySet());
			Collections.sort(keys);
			int rownum = 0;
			for (Integer key : keys) {
				Row row = sheet.createRow(rownum++);
				Object [] objArr = data.get(key);
				int cellnum = 0;
				for (Object obj : objArr) {
					Cell cell = row.createCell(cellnum++);
					if(obj instanceof Date)
						cell.setCellValue((Date)obj);
					else if(obj instanceof Boolean)
						cell.setCellValue((Boolean)obj);
					else if(obj instanceof String)
						cell.setCellValue((String)obj);
					else if(obj instanceof Double)
						cell.setCellValue((Double)obj);
					else if(obj instanceof Integer)
						cell.setCellValue((int)obj);
				}
			}

			//write data to the excel file
			try {
				FileOutputStream out =
					new FileOutputStream(new File(Paths.get(".")
						.toAbsolutePath().normalize().toString(),
						"Rebellion"), false);
				workbook.write(out);
				out.close();
				System.out.println("Excel written successfully..");

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		/**
		 * Print the patch map with people on it.
		 */
		public void printPatch() {
			for(int i =0; i <numOfPathes; i++)
			{
				for(int j =0; j <numOfPathes; j++)
				{				
					if (patches[i][j].getPerson().size() > 0) {
						if (patches[i][j].isCop()) {
							System.out.print("\t*");
							continue;
						} else if (patches[i][j].isActiveAgent()) {
							System.out.print("\tA");
							continue;
						}
						int jail = ((Agent)patches[i][j].getPerson().get(0))
									.returnJailTerm();
						System.out.print("\t" + jail);
					}
					else
					{
						System.out.print("\t ");
					}
				}
				System.out.println(" ");
			}
			System.out.println("------------------------------------");
		}

		/**
		 * Reduce the governmentLegitimacy by 0.01 each tick.
		 */
		private void graduallyChange() {
			if(governmentLegitimacy> 0.2) governmentLegitimacy -= 0.01;
		}

		/**
		 * Reduce the governmentLegitimacy by 0.3 in one tick.
		 */
		private void sharplyChange(){
			governmentLegitimacy -= 0.3;
		}

		/**
		 * Generate an agent and assign it to a random movable patch.
		 * @return Agent
		 */
		public Agent generateAgent(){

			logger.info("generateAgent method");

			Patch currentPatch = randPatch();

			logger.info("random patch: x = " + currentPatch.getX()
						+ " y = " + currentPatch.getY());


			Agent agent= new Agent(currentPatch, false);

			currentPatch.setPerson(agent);

			return agent;
		}

		/**
		 * Generate a cop and assign it to a random movable patch.
		 * @return Cop
		 */
		public Cop generateCop(){

			Patch currentPatch = randPatch();

			Cop cop= new Cop(currentPatch);

			currentPatch.setPerson(cop);

			return cop;
		}

		/**
		 * Find a random patch with no person on it..
		 * @return Patch
		 */
		public Patch randPatch(){

			logger.info("randPatch method");
			Patch patch;
			logger.info("start randomly finding patch");

			while(true) {
				patch = patches[randInt(0,numOfPathes-1)]
						[randInt(0,numOfPathes-1)];
				if(patch.getPerson().size() == 0) break;
			}
			logger.info("finished randomly finding patch");

			return patch;
		}

		/**
		 * Return a random int between min and max.
		 * @param min
		 * @param max
		 * @return int
		 */
		public static int randInt(int min, int max) {

			// Usually this can be a field rather than a method variable
			Random rand = new Random();

			// nextInt is normally exclusive of the top value,
			// so add 1 to make it inclusive
			int randomNum = rand.nextInt((max - min) + 1) + min;

			return randomNum;
		}

		/**
		 * Move an array of person randomly to random patch.
		 * @param array
		 */
		private void randMove(Person[] array){
			logger.info("randMoving starts");
			ArrayList<Integer> remaining = new ArrayList<>();
			for (int i = 0; i < array.length; i++) {
				remaining.add(i);
			}
			int step = 0;
			boolean isAgentArray = false;
			if(array[0] instanceof Agent) isAgentArray = true;

			//select from remaining index when remaining is not empty
			while (step<array.length) {
				int index = randInt(0,remaining.size()-1);
				if(remaining.get(index)>=0){
					//move agents and cops here
					if(isAgentArray){
						if(agents[index].getJailTerm() == 0)
							agents[index].move();
					}
					else cops[index].move();
					remaining.set(index,-1);
					step++;
				}
			}
			logger.info("randmove finished after " + step + "iterations.");
		}

		/**
		 * Count the agents for the output excel.
		 */
		private void countAgents(){
			for(Agent agent : agents){
				if(agent.isActive()) activeAgent ++;
				else if(agent.getJailTerm() > 0) jailedAgent ++;
				else quietAgent ++;
			}
		}

		/**
		 * Reset the count of agents.
		 */
		private void resetCount(){
			activeAgent = 0;
			jailedAgent = 0;
			quietAgent = 0;
		}




}
