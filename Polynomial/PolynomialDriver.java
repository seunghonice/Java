/*
 * (C) 2016 CSE2010 HW #2
 * 
 * Name: 
 * Student ID: 
 * 
 * DO NOT MODIFY THIS CLASS!
 */
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

// Whether to use singly-linked list or doubly-linked list.
enum Kind { SINGLE, DOUBLE };

// Factory object to create an implementation object with 
// a designated representation 
class PolyFactory {
	static Polynomial createPolyImpl(Kind type) {
		if (type == Kind.DOUBLE)
			return new DLinkedPolynomial();
		else { // Kind.SINGLE
			System.out.println("Singly-linked list currently not supported ...");
			System.exit(1);
			return null;
		}
	}
}

/*
 * Main driver class for HW #2
 */
public class PolynomialDriver {
	
	public static void main(String[] args) throws IOException {
		
		Polynomial[] poly = new Polynomial[2]; // f(x) and g(x)

		// Construct two Polynomials f(x) & g(x)
		// f(x) = poly[0], g(x) = poly[1];
		for (int i = 0; i < 2; i++) {
			poly[i] = PolyFactory.createPolyImpl(Kind.DOUBLE); // Create an empty list

			Path path = Paths.get(args[i]);
			Scanner scanner = new Scanner(path);
			while (scanner.hasNext()) {
				double coeff = scanner.nextDouble();
				int expo = scanner.nextInt();
				poly[i].addTerm(new Term(coeff, expo));
			}
			scanner.close();
			
			System.out.print((i == 0 ? "f" : "g") + "(x) = ");
			System.out.println(poly[i].dump());
			System.out.println("Degree: " + poly[i].getDegree());
			System.out.println((i == 0 ? "f" : "g") + "(10) = " + poly[i].evaluate(10.0));
			
			System.out.println("Coefficient for 4 is  " + poly[i].getCoefficient(4) + "\n");
		}
		
		System.out.println("f(x) + g(x) ==");
		System.out.println(poly[0].padd(poly[1]).dump());

		System.out.println("\nf(x)*g(x) ==> (Note: f(x) didn't change after the previous addition.)");
		System.out.println(poly[0].pmult(poly[1]).dump());
	}
}