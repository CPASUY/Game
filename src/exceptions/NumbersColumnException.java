package exceptions;

public class NumbersColumnException extends Exception{
	//Constant
		private static final long serialVersionUID = 1L;
		//Method
		public NumbersColumnException() {
			super("The number of columns must be less than 26");
		}
}
