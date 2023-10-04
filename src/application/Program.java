package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Sale;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		System.out.print("Enter full file path: ");
		String path = sc.nextLine();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			List<Sale> sales = new ArrayList<>();

			String line = br.readLine();

			while (line != null) {
				String[] fields = line.split(",");
				sales.add(new Sale(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2],
						Integer.parseInt(fields[3]), Double.parseDouble(fields[4])));
				line = br.readLine();
			}
			System.out.println();

			System.out.println("First five sales of 2016 with the highest average price: ");
			Comparator<Sale> comparator = (x, y) -> x.averagePrice().compareTo(y.averagePrice());

			sales.stream()
					.filter(s -> s.getYear() == 2016)	
					.sorted(comparator.reversed())
					.limit(5)
					.forEach(System.out::println);

			double sum = sales.stream()
					.filter(s -> (s.getMonth() == 1 || s.getMonth() == 7) && s.getSeller().toUpperCase().equals("LOGAN"))
					.map(s -> s.getTotal())
					.reduce(0.0, (x, y) -> x + y);

			System.out.println();

			System.out.print("Total value sold by seller Logan in months 1 and 7 = = " + sum);

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}

		sc.close();

	}

}
