package org.springframework.samples.petclinic.exception;

public class FeatureDisabledException extends RuntimeException {

	public FeatureDisabledException(String message) {
		super(message);
	}

}
