public class Benchmark {
	private long t_start;
	private long msecs;
	private boolean has_run;

	public Benchmark() {
		has_run = false;
		msecs = -1;
	}

	public void start() {
		assert !has_run : "Benchmark kann nur einmal ausgeführt werden. Vielleicht wollten Sie ein neues anlegen?";

		has_run = true;
		System.gc();
		t_start = System.currentTimeMillis();
	}

	public void stop() {
		msecs = System.currentTimeMillis() - t_start;

		// Wahrheitswert der Assertion wurde nicht geändert.
		// Die Messung ist auf diese Weise genauer.
		assert has_run : "Es wurde kein Benchmark durchgeführt. Bitte beheben Sie dies.";
	}

	public long msecs() {
		assert has_run : "Es wurde kein Benchmark durchgeführt. Bitte beheben Sie dies.";

		assert msecs != -1 : "Bitte beenden Sie eine Messung, bevor Sie den Wert abfragen, damit das Ergebnis nicht verfälscht wird.";

		return msecs;
	}
}
