import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.ConcurrentLinkedQueue;

public class openOffice implements Office{
	Queue<Desk> cleanDesks, dirtyDesks;
	static int DesksNumber = 100;
	static int ClerksNumber = 120;
	static int CleanersNumber = 20;
	
	Lock lock = new ReentrantLock();
	Condition clean = lock.newCondition();
	Condition dirty = lock.newCondition();
	
	
	
	public static void main(String[] args) {
		

		Office office = new openOffice();
		
		
		for(int i=0;i< ClerksNumber;i++) {
			Clerk temp = new Clerk(office,i);
			temp.start();
		}
		
		for(int i=0;i<CleanersNumber;i++) {
			Cleaner temp = new Cleaner(office,i);
			temp.start();
		}
		
	}
	
	
	/*
	 
	 constructor, 
	 initiate cleanDesk and dirty queue, pushing clean desks to queue.
	 **/
	
	public openOffice() {
		cleanDesks = new ConcurrentLinkedQueue<Desk>();
		
		dirtyDesks = new ConcurrentLinkedQueue<Desk>();
		
		// add clean desks to the queue
		for(int i=0;i<DesksNumber;i++) {
			Desk desk = new Desk();
			cleanDesks.add(desk);
			}
	}
	
	
	//use Lock and condition to implement a monito;r.
	public Desk arrive(){
		lock.lock();
		try {
			while(cleanDesks.size()==0) {
			clean.await();
			}
			//return cleanDesks.poll();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}

		return cleanDesks.poll();
	}
	
	
	public void leave(Desk desk) {
		
		lock.lock();
		try {
		// set state of is_clean to false;
		desk.work();
		dirtyDesks.add(desk);
		
		dirty.signal();
		}finally {
			lock.unlock();
		}
		
	}
		
	
	public Desk service() {
		
		lock.lock();
		try {
			while(dirtyDesks.size()==0)
				dirty.await();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
		return dirtyDesks.poll();
		
	}
	
	public void cleaned (Desk desk) {
		// set state of is_clean to true;
		lock.lock();
		try {
		
		desk.clean();
		
		cleanDesks.add(desk);
		clean.signal();
		}finally {
			lock.unlock();
		}	
		
	}


}
