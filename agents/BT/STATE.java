package agents.BT;

public enum STATE {
	SUCCESS(1), RUNNING(2), FAILURE(3);

	private int id;

	STATE(int id) {
		this.id = id;
	}

	public int value() {
		return id;
	}
}
