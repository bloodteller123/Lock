import java.util.concurrent.ThreadLocalRandom;
//import java.util.concurrent.Semaphore;

public class Clerk extends Thread {
	
	
	Office office;
	final int timeWork_max = 10000;
	final int timeWork_min = 5000;
	private int ID;
	
	//private Semaphore number;
	
	public Clerk(Office office, int ID ) {
		this.office = office;
		this.ID = ID;
		setName("clerk "+ ID);
	}
	
	
	public void run() {
		while(true) {
			
			
			
			if(ThreadLocalRandom.current().nextBoolean()) {	// to simulate at any time
				Desk desk = office.arrive();

				// sleep 5~10 sec to simulate work
				try {
					System.out.println("Clerk" + ID +" is working");
					sleep((long)(ThreadLocalRandom.current().nextLong(timeWork_min,timeWork_max)));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Clerk" + ID +" finishes working");
				
				
				office.leave(desk);
			}
		}
	}

}
