public class Clock {
	private long m_start;

	public Clock() {
		m_start = System.currentTimeMillis();
	}

	public void restart() {
		m_start = System.currentTimeMillis();
	}

	public long elapsed() {
		return System.currentTimeMillis() - m_start;
	}
}
