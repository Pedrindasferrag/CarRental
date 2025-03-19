package model.services;

import java.time.Duration;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {

	private Double pricePerHour;
	private Double pricePerDay;
	
	private BrazilTaxService taxService;
	
	
	public RentalService(Double pricePerHour, Double pricePerDay, BrazilTaxService taxService) {
		this.pricePerHour = pricePerHour;
		this.pricePerDay = pricePerDay;
		this.taxService = taxService;
	}

	public Double getPricePerHour() {
		return pricePerHour;
	}

	public Double getPricePerDay() {
		return pricePerDay;
	}

	public BrazilTaxService getTaxService() {
		return taxService;
	}
	
	public void ProcessInvoice(CarRental cr) {
		
		double minutes = Duration.between(cr.getStart(), cr.getFinish()).toMinutes();
		double hours = minutes / 60.0;
		
		double basicPayment;
		if( hours <= 12.0) {
			basicPayment = pricePerHour * Math.ceil(hours);
		}else {
			basicPayment = pricePerDay * Math.ceil(hours / 24); 
		}
		
		double tax = taxService.taxService(basicPayment);
		
		cr.setInvoice(new Invoice(basicPayment , tax));
	}

	
	
}
