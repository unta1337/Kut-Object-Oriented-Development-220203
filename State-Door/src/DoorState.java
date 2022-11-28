/**
 * @copyright 한국기술교육대학교 컴퓨터공학부 객체지향개발론및실습
 * @version 2022년도 2학기
 * @author 김상진, 김성녕
 * 상태 패턴
 * 상태 interface
 */
public enum DoorState {
	OPENED {
		@Override
		public void open(Door door) {
			System.out.println("The door is already opened.");
		}

		@Override
		public void close(Door door) {
			System.out.println("Closing the door.");
			door.SetState(CLOSED);
		}

		@Override
		public void lock(Door door) {
			System.out.println("Opened door is cannot be locked.");
		}

		@Override
		public void unlock(Door door) {
			System.out.println("Opened door is cannot be unlocked.");
		}
	},

	CLOSED {
		@Override
		public void open(Door door) {
			System.out.println("Opening the door.");
			door.SetState(OPENED);
		}

		@Override
		public void close(Door door) {
			System.out.println("The door is already closed.");
		}

		@Override
		public void lock(Door door) {
			System.out.println("Locking the door.");
			door.SetState(LOCKED);
		}

		@Override
		public void unlock(Door door) {
			System.out.println("The door is already unlocked.");
		}
	},

	LOCKED {
		@Override
		public void open(Door door) {
			System.out.println("Locked door is cannot be opened.");
		}

		@Override
		public void close(Door door) {
			System.out.println("The door is already closed.");
		}

		@Override
		public void lock(Door door) {
			System.out.println("The door is already locked.");
		}

		@Override
		public void unlock(Door door) {
			System.out.println("Unlocking the door.");
			door.SetState(CLOSED);
		}
	};

	public abstract void open(Door door);
	public abstract void close(Door door);
	public abstract void lock(Door door);
	public abstract void unlock(Door door);
}
