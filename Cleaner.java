//import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
public class Cleaner extends Thread{
	
	Office office;
	final int timeClean_max = 5000;
	final int timeClean_min = 1000;
	private int ID;
	
	
	public Cleaner(Office office,int ID) {
		this.office = office;
		this.ID = ID;

		setName("cleaner "+ ID);
		
		
	}
	
	public void run(){
		while(true) {
			Desk desk = office.service();

			
			try {
				System.out.println("Cleaner" + ID +" is cleaning");

				sleep((long)(ThreadLocalRandom.current().nextLong(timeClean_min,timeClean_max)));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Cleaner " + ID +" finishes cleaning");
			office.cleaned(desk);
			
		}
	}
}
