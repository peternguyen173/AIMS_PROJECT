package com.example.demo.exception;

public class UnrecognizedException extends RuntimeException {
	public UnrecognizedException() {
		super("ERROR: Something went wrowng!");
	}
}
