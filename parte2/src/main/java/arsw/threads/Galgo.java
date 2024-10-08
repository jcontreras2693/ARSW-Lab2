package arsw.threads;

/**
 * Un galgo que puede correr en un carril
 * 
 * @author rlopez
 * 
 */
public class Galgo extends Thread {
	private int paso;
	private Carril carril;
	RegistroLlegada regl;
	private Object lock;
	private boolean shouldPause = false;

	public Galgo(Carril carril, String name, RegistroLlegada reg, Object lock) {
		super(name);
		this.carril = carril;
		paso = 0;
		this.regl=reg;
		this.lock = lock;
	}

	public void corra() throws InterruptedException {
		while (paso < carril.size()) {
			synchronized (lock) {
				while (shouldPause) {
					lock.wait();
				}
			}
			Thread.sleep(100);
			carril.setPasoOn(paso++);
			carril.displayPasos(paso);
			
			if (paso == carril.size()) {						
				carril.finish();
				synchronized (regl){
					int ubicacion=regl.getUltimaPosicionAlcanzada();
					regl.setUltimaPosicionAlcanzada(ubicacion+1);
					System.out.println("El galgo "+this.getName()+" llego en la posicion "+ubicacion);
					if (ubicacion==1){
						regl.setGanador(this.getName());
					}
				}
			}
		}
	}


	@Override
	public void run() {
		
		try {
			corra();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void pause() {
		synchronized (lock) {
			shouldPause = true;
		}
	}

	public void resumeRace() {
		synchronized (lock) {
			shouldPause = false;
			lock.notifyAll();
		}
	}

}
