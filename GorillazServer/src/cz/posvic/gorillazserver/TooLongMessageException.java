package cz.posvic.gorillazserver;

public class TooLongMessageException extends Exception {

	private static final long serialVersionUID = 1L;

	public TooLongMessageException() {

	}

	public TooLongMessageException(String msg) {
		super(msg);
	}
}
