package task1;




public abstract class Interface {
	// State of Pet and Prey
	public static final int DEAD = -1;
		
		public static final int HUNGRY = 1;
		
		public static final int MOVING = 0;
		
		protected static final int SPEED = 8;
		
		protected String name;
		
		protected int state;
		
		protected int size;
		
		protected void delay(int m) {
			try {
				Thread.sleep(m);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		abstract void changeState(int state);
		
		
	}
