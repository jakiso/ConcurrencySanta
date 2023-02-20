import Santa.SantaState;

//import com.sun.org.apache.xml.internal.security.utils.HelperNodeList;


public class Santa implements Runnable {

	enum SantaState {SLEEPING, READY_FOR_CHRISTMAS, WOKEN_UP_BY_ELVES, WOKEN_UP_BY_REINDEER};
	private SantaState state;
	private boolean kill;
	private SantaScenario scenario;

	public Santa(SantaScenario scenario) 
	{
		this.state = SantaState.SLEEPING;
		this.scenario = scenario;
		setKill(false);
	}
	// Setters and getters for kill
	public boolean isKill() 
	{
		return kill;
	}
	public void setKill(boolean kill) 
	{
			this.kill = kill;
	}
	public SantaState getState()
	{
		return state;
	}
	public void ElvesWakeUpSanta()
	{
		this.state = SantaState.WOKEN_UP_BY_ELVES;
	}
	public void setState(SantaState state) {
		this.state = state;
	}
	public void sKill(boolean santa)
	{
		this.kill = santa;
		System.out.println("Santa killed \n");
	}


	@Override
	public void run() {
		while(this.kill == false) {
			// wait a day...
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			switch(state) {
			case SLEEPING: // if sleeping, continue to sleep
				
				break;
			case WOKEN_UP_BY_ELVES:
			for(Elf elf :scenario.elves)
			{
				if(elf.getState() == Elf.ElfState.AT_SANTAS_DOOR)
				{
					elf.setState(Elf.ElfState.WORKING);
				}
				scenario.elvesintrouble = 0;
				scenario.elfsemaphore.release(scenario.max);
				state = SantaState.SLEEPING;
			}
				break;
			case WOKEN_UP_BY_REINDEER:

				break;
			case READY_FOR_CHRISTMAS: // nothing more to be done
				break;
			}
		}
	}


	/**
	 * Report about my state
	 */
	public void report() {
		System.out.println("Santa : " + state);
	}
	public boolean sleepyboi()
	{
		if(state == Santa.SantaState.SLEEPING)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
