import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class SantaScenario {

	public Santa santa;
	public List<Elf> elves;
	public List<Reindeer> reindeers;
	public boolean isDecember;
	public int elvesintrouble;
	public int max =3;
	Semaphore elfsemaphore = new Semaphore(max,true);
	private boolean lock;
	public SantaScenario()
	{
		this.elvesintrouble = 0;
		this.lock = false;
	}
	
	public static void main(String args[]) {
		SantaScenario scenario = new SantaScenario();
		scenario.isDecember = false;
		// create the participa Santa
		scenario.santa = new Santa(scenario);
		Thread th = new Thread(scenario.santa);
		th.start();
		// The elves: in this case: 10
		scenario.elves = new ArrayList<>();
		for(int i = 0; i != 10; i++) 
		{
			Elf elf = new Elf(i+1, scenario);
			scenario.elves.add(elf);
			th = new Thread(elf);
			th.start();
		}
		// The reindeer: in this case: 0
		scenario.reindeers = new ArrayList<>();
		for(int i=0; i != 0; i++) {
			Reindeer reindeer = new Reindeer(i+1, scenario);
			scenario.reindeers.add(reindeer);
			th = new Thread(reindeer);
			th.start();
		}
		// now, start the passing of time
		for(int day = 1; day < 500; day++)
		{
			// Terminate everyone at day 370
			if(day == 370)
			{
				for(Reindeer killreindeer: scenario.reindeers)
				{
					killreindeer.setKill(true);
				}
				for(Elf killelf: scenario.elves)
				{
					killelf.setKill(true);
				}
				scenario.santa.setKill(true);
				break;
		}
			// wait a day
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// turn on December
			if (day > (365 - 31)) {
				scenario.isDecember = true;
			}
			// print out the state:
			System.out.println("***********  Day " + day + " *************************");
			scenario.santa.report();
			for(Elf elf: scenario.elves) {
				elf.report();
			}
			for(Reindeer reindeer: scenario.reindeers) {
				reindeer.report();
			}
		}
	}

	public int getElvesintrouble() {
		return elvesintrouble;
	}

	public void setElvesintrouble(int elvesintrouble) 
	{
		this.elvesintrouble = elvesintrouble;
	}
	public boolean isLock() {
		return lock;
	}
	public void setLock(boolean lock) {
		this.lock = lock;
	}



}