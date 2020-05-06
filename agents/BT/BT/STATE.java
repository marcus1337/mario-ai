package agents.BT.BT;

public enum STATE {
	SUCCESS(1), RUNNING(2), FAILURE(3), FRESH(4);

	private int id;

	STATE(int id) {
		this.id = id;
	}

	public int value() {
		return id;
	}
}
