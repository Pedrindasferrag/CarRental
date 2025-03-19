package application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

import model.entities.CarRental;
import model.entities.Vehicle;
import model.services.BrazilTaxService;
import model.services.RentalService;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		
		
		System.out.println("Entre com os dados do veiculo");
		System.out.print("Entre com o modelo do carro: ");
		Vehicle vehicle = new Vehicle(sc.nextLine());
		System.out.print("Retirada (dd/MM/yyyy hh:mm): ");
		LocalDateTime start = LocalDateTime.parse(sc.nextLine(), fmt);
		System.out.print("Retorno (dd/MM/yyyy hh:mm): ");
		LocalDateTime finish = LocalDateTime.parse(sc.nextLine(), fmt);
		
		CarRental cr = new CarRental(start, finish, vehicle);
		
		System.out.print("Entre com o preco por hora: ");
		double pricePerHour = sc.nextDouble();
		System.out.print("Entre com o preco por dia: ");
		double pricePerDay = sc.nextDouble();
		
		RentalService rs = new RentalService(pricePerHour, pricePerDay, new BrazilTaxService());
		
		rs.ProcessInvoice(cr);
		
		System.out.println("FATURA:");
		System.out.printf("Pagamento basico: %.2f%n", cr.getInvoice().getBasicPayment());
		System.out.printf("Imposto: %.2f%n", cr.getInvoice().getTax());
		System.out.printf("Pagamento total: %.2f%n", cr.getInvoice().getTotalPayment());
		sc.close();
	}
}
