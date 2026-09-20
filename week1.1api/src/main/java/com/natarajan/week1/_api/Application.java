/*
 Natarajan, C. (2026). CIS530-T301 Server-Side Development (2271-1). Bellevue University.
*/
package com.natarajan.week1._api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	/**
	 * Starts the Week 1 REST API application.
	 * @param args String[] command-line arguments supplied when the application starts.
	 * @return No value; the method starts the application process.
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	} // end main

} // end Application
