package _GA_TESTS;

public class DecisionLogger {
	
	private Action prevAction = null;
	private Observation prevObservation = null;
	private Action nowAction = null;
	private Observation nowObservation = null;
	
	private boolean isFirstDecision() {
		if(prevAction == null) {
			prevAction = nowAction.copyAction();
			prevObservation = nowObservation.copyObservation();
			return true;
		}
		return false;
	}
	
	private boolean isNewGapEvent() {
		boolean prevGap = prevObservation.isGapDetectedToTheRight();
		boolean nowGap = nowObservation.isGapDetectedToTheRight();
		return (!prevGap && nowGap);
	}
	
	private boolean isNewCloseGapEvent() {
		boolean prevGap = prevObservation.isGapDetectedCloseToTheRight();
		boolean nowGap = nowObservation.isGapDetectedCloseToTheRight();
		return (!prevGap && nowGap);
	}
	
	private boolean isNewStompableEnemyEvent() {
		boolean prevStompEnemy = prevObservation.isStompableEnemyDetectedToTheRight();
		boolean nowStompEnemy = nowObservation.isStompableEnemyDetectedToTheRight();
		return (!prevStompEnemy && nowStompEnemy);
	}
	
	private boolean isNewNonStompableEnemyEvent() {
		boolean prevNonStompEnemy = prevObservation.isNonStompableEnemyDetectedToTheRight();
		boolean nowNonStompEnemy = nowObservation.isNonStompableEnemyDetectedToTheRight();
		return (!prevNonStompEnemy && nowNonStompEnemy);
	}	
	
	
	private boolean isNewCloseStompableEnemyEvent() {
		boolean prevStompEnemy = prevObservation.isStompableEnemyDetectedCloseToTheRight();
		boolean nowStompEnemy = nowObservation.isStompableEnemyDetectedCloseToTheRight();
		return (!prevStompEnemy && nowStompEnemy);
	}
	
	private boolean isNewCloseNonStompableEnemyEvent() {
		boolean prevNonStompEnemy = prevObservation.isNonStompableEnemyDetectedCloseToTheRight();
		boolean nowNonStompEnemy = nowObservation.isNonStompableEnemyDetectedCloseToTheRight();
		return (!prevNonStompEnemy && nowNonStompEnemy);
	}	
	
	
	public boolean isActionChangeMoveRight() {
		return !prevAction.isMovingRight() && nowAction.isMovingRight();
	}
	public boolean isActionChangeMoveLeft() {
		return !prevAction.isMovingLeft() && nowAction.isMovingLeft();
	}
	public boolean isActionChangeMoveNone() {
		return !prevAction.isNotMovingLeftOrRight() && nowAction.isNotMovingLeftOrRight();
	}
	public boolean isActionChangeShoot() {
		return !prevAction.shoot && nowAction.shoot;
	}
	public boolean isActionChangeRun() {
		return !prevAction.isRunning() && nowAction.isRunning();
	}
	public boolean isActionChangeJump() {
		return !prevAction.isJumping() && nowAction.isJumping();
	}
	
	public void printEvent(String event) {
		System.out.print("EVENT(" + event + ") ");
	}
	public void printActionChange() {
		System.out.print("RIGHT(" + isActionChangeMoveRight() +") ");
		System.out.print("LEFT(" + isActionChangeMoveLeft() +") ");
		System.out.print("NONE(" + isActionChangeMoveNone() +") ");
		System.out.print("SHOOT(" + isActionChangeShoot() +") ");
		System.out.print("RUN(" + isActionChangeRun() +") ");
		System.out.print("JUMP(" + isActionChangeJump() +") ");
	}
	
	public void checkDecisions() {
		
		boolean isAnyEvent = false;
		
		if(isNewGapEvent()) {
			printEvent("GAP");
			isAnyEvent = true;
		}
		
		if(isNewCloseGapEvent()) {
			printEvent("CLOSE-GAP");
			isAnyEvent = true;
		}
		
		if(isNewStompableEnemyEvent()) {
			printEvent("STOMPABLE-ENEMY");
			isAnyEvent = true;
		}
		
		if(isNewNonStompableEnemyEvent()) {
			printEvent("NONSTOMPABLE-ENEMY");
			isAnyEvent = true;
		}
		
		if(isNewCloseStompableEnemyEvent()) {
			printEvent("CLOSE-STOMPABLE-ENEMY");
			isAnyEvent = true;
		}
		
		if(isNewCloseNonStompableEnemyEvent()) {
			printEvent("CLOSE-NONSTOMPABLE-ENEMY");
			isAnyEvent = true;
		}
		
		if(isAnyEvent) {
			printActionChange();
			System.out.println();
		}
		
	}
	
	public void logDecision(Action action, Observation observation) {
		nowAction = action.copyAction();
		nowObservation = observation.copyObservation();
		if(isFirstDecision())
			return;
		checkDecisions();
		prevAction = nowAction.copyAction();
		prevObservation = nowObservation.copyObservation();
	}

}
