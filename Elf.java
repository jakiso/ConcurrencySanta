import java.util.Random;

public class Elf implements Runnable {

	enum ElfState {
		WORKING, TROUBLE, AT_SANTAS_DOOR
	};

	private ElfState state;
	/**
	 * The number associated with the Elf
	 */
	private int number;
	private Random rand = new Random();
	private SantaScenario scenario;
	private boolean kill;
	public boolean lock;

	public Elf(int number, SantaScenario scenario) {
		this.number = number;
		this.scenario = scenario;
		this.state = ElfState.WORKING;
		setKill(false);
	}
	public void setlock(boolean lock)
	{
		this.lock = lock;
	}
	public boolean getlock()
	{
		return lock;
	}
	// Setters and getters for kill
	public boolean isKill() 
	{
		return kill;
	}
	public void setKill(boolean kill) {
		this.kill = kill;
	}

	public ElfState getState()
	{
		return state;
	}

	/**
	 * Santa might call this function to fix the trouble
	 * @param state
	 */
	public void setState(ElfState state) {
		this.state = state;
	}

	@Override
	public void run() {
		while (this.kill == false) {
      // wait a day
  		try {
  			Thread.sleep(100);
  		} catch (InterruptedException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
  		
  		Elf bufferElf = scenario.elves.get(this.number - 1);
			switch (state) {
			case WORKING: {
				// at each day, there is a 1% chance that an elf runs into
				// trouble.
				if (rand.nextDouble() < 0.01) 
				{
					state = ElfState.TROUBLE;
					try
					{
						scenario.elfsemaphore.acquire();
						scenario.elvesintrouble++;
					}
					catch (InterruptedException e) {
			  			// TODO Auto-generated catch block
			  			e.printStackTrace();
					}
					
				}
				break;
			}
			// Trouble, go to santa door
			case TROUBLE:
			if(scenario.elvesintrouble >= 3)
			{
				bufferElf.setState(Elf.ElfState.AT_SANTAS_DOOR);
			}
				break;
			// Wake up santa immediately if at door
			case AT_SANTAS_DOOR:
				if(scenario.santa.sleepyboi() == true && scenario.elvesintrouble == scenario.max)
				{
					scenario.santa.ElvesWakeUpSanta();
				}
				break;
			}
		}
		}
	

	/**
	 * Report about my state
	 */
	public void report() {
		System.out.println("Elf " + number + " : " + state);
	}

}