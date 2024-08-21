package vehicles.application;

public class VehicleException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String code;
	
	public VehicleException(String code, String message) {
		super(message);
		this.code = code;
	}
	
	public VehicleException(String message) {
		super(message);
	}
	
	public VehicleException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}
	
	public VehicleException(String message, Throwable cause) {
		super(message, cause);
	}
}
