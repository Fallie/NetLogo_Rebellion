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

		private boolean extension;

		private boolean graduallyChangeGov;

		private boolean sharplyChangGov;

		//
		public static Patch[][] patches;

		//
		public static Agent[] agents;

		public static Cop[] cops;

		Logger logger = Logger.getLogger("World");

		public static int quietAgent = 0;

		public static int jailedAgent = 0;

		public static int activeAgent = 0;




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
					 int vision, boolean watchOne, int ticks, boolean extension,
					 boolean graduallyChangeGov, boolean sharplyChangGov) throws IOException {
			this.numOfPathes = numOfPathes;
			this.numOfAgents = numOfAgents;
			this.numOfCops = numOfCops;
			this.governmentLegitimacy = governmentLegitimacy;
			this.maxJailTerm = maxJailTerm;
			this.movement = movement;
			this.vision = vision;
			this.watchOne = watchOne;
			this.ticks = ticks;
			this.extension = extension;
			this.graduallyChangeGov = graduallyChangeGov;
			this.sharplyChangGov = sharplyChangGov;
		}

		/**
		 * To setup the model to make it ready to run.
		 */
		public void setup(){

			logger.info("begin setting up the world");
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
			logger.info("begin setting up the cops");
			cops = new Cop[numOfCops];
			for(int k = 0; k < numOfCops; k ++){
				cops[k] = generateCop();
			}
			logger.info("finished setting up the cops");
			logger.info("begin updating neighbors");
			for(int k = 0; k < patches.length; k ++){
				for(int j = 0; j < patches[k].length; j ++){
					patches[k][j].updateNeighborhood();
				}
			}
			logger.info("finished updating neighbors");
		}

		public void go(int ticks) throws IOException {
			logger.info("begin run the world");

			//create the excel file
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Rebellion");
			Map<Integer, Object[]> data = new HashMap<>();
			data.put(1, new Object[] {"Tick No.", "Quiet Agents", "Jailed Agents","Active Agents"});

			//start running the world
			int it= 0;
			for(int i = ticks; i > 0; i--){

				resetCount();

				if(it > 50 && this.graduallyChangeGov){
					graduallyChange();
				}

				if(it == 50 && this.sharplyChangGov){
					sharplyChange();
				}

				for(Agent agent : agents){
					if(agent.getJailTerm() == 0){
						if(this.extension) agent.extensionBehavior();
						else agent.determinBehavior();
					}
				}

				for(Cop cop : cops){
					cop.enforce();
				}

				for(Agent agent : agents){
					if(agent.getJailTerm() > 0) agent.reduceJailTerm();
				}

				countAgents();

				for(int k = 0; k < patches.length; k ++){
					for(int j = 0; j < patches[k].length; j ++){
						patches[k][j].updateNeighborhood();
					}
				}

				logger.info("agents are moving randomly");
				randMove(agents);
				logger.info("cops are moving randomly");
				randMove(cops);

				it ++;
				logger.info("world running iteration it :" + it);

				//put the data into the row
				data.put(it+1, new Object[] {it, quietAgent, jailedAgent ,activeAgent});



				//logger.info("goverment value: " + governmentLegitimacy);
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
					new FileOutputStream(new File(Paths.get(".").toAbsolutePath().normalize().toString(),
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

		private void graduallyChange() {
			if(governmentLegitimacy> 0.2) governmentLegitimacy -= 0.01;
		}

		private void sharplyChange(){
			governmentLegitimacy -= 0.3;
		}

		public Agent generateAgent(){

			logger.info("generateAgent method");

			Patch currentPatch = randPatch();

			logger.info("random patch: x = " + currentPatch.getX()+" y = " + currentPatch.getY());


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

		public Patch randPatch(){

			logger.info("randPatch method");
			Patch patch;
			logger.info("start randomly finding patch");

			while(true) {
				patch = patches[randInt(0,numOfPathes-1)][randInt(0,numOfPathes-1)];
				if(patch.getPerson().size() == 0) break;
			}
			logger.info("finished randomly finding patch");

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
					if(isAgentArray)
						agents[index].move();
					else cops[index].move();
					remaining.set(index,-1);
					step++;
					//logger.info("this is the number " + step + "iteration.");
				}
			}
			logger.info("randmove finished after " + step + "iterations.");
		}

		private void countAgents(){
			for(Agent agent : agents){
				if(agent.isActive()) activeAgent ++;
				else if(agent.getJailTerm() > 0) jailedAgent ++;
				else quietAgent ++;
			}
		}

		private void resetCount(){
			activeAgent = 0;
			jailedAgent = 0;
			quietAgent = 0;
		}




}
