package IC.LIR;

public enum OptLirBlockFlag {
		DEFAULT(""),
		LITERAL("Move "),
		REGISTER("Move "),
		VARIABLE("Move "),
		EXTERNAL_FIELD("MoveField "),
		ARRAY_LOCATION("MoveArray ");
		
		private final String moveCmd;
		OptLirBlockFlag(String cmd) {
			moveCmd = cmd;
		}
		
		public String getMove() {
			return moveCmd;
		}
		
}
