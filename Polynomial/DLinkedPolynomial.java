/*
 * (C) 2016 CSE2010 HW #2
 * 
 * Complete the codes ...
 */
import java.util.*;
import java.lang.Math;

/*
 * class DLinkedPolynomial
 */
public class DLinkedPolynomial implements Polynomial {
	private DLinkedList<Term> poly;

	DLinkedPolynomial() {
		poly = new DLinkedList<Term>();
	}

	@Override
	public int getDegree() {
		int expo = -1;
		DNode<Term> current = poly.getFirstNode();
		do {
			if (current.getInfo().expo > expo) 
				expo = current.getInfo().expo;
			current = poly.getNextNode(current);
		} while (current.getNext() != null);
		return expo;
	}

	@Override
	public double getCoefficient(int exponent) {
		double coeff = -1;
		DNode<Term> current = poly.getFirstNode();
		do {
			if (current.getInfo().expo == exponent) 
				coeff = current.getInfo().coeff;
			current = poly.getNextNode(current);
		} while (current.getNext() != null);
		return coeff;
	}

	@Override
	public Polynomial padd(Polynomial p) {
		DLinkedPolynomial addedPoly = new DLinkedPolynomial();
		DNode<Term> current = poly.getFirstNode();
		do {
			addedPoly.addTerm(current.getInfo());
			current = poly.getNextNode(current);
		} while (current.getNext() != null);
		for (int i = 0; i < p.getDegree(); i++) {
			if (p.getCoefficient(i) != -1) {
				addedPoly.addTerm(new Term(p.getCoefficient(i), i));
				break;
			}
		}
		return addedPoly;
	}

	Term multTerms(Term x, Term y) {
		return new Term(x.coeff * y.coeff, x.expo + y.expo);
	}

	@Override
	public Polynomial pmult(Polynomial p) {
		DLinkedPolynomial multipliedPoly = new DLinkedPolynomial();
		DNode<Term> current = poly.getFirstNode();
		do {
			for (int i = 0; i <= p.getDegree(); i++) {
				if (p.getCoefficient(i) != -1) {
					multipliedPoly.addTerm(multTerms(current.getInfo(), new Term(p.getCoefficient(i),i)));
				}
			}
			current = poly.getNextNode(current);
		} while (current.getNext() != null);
		return multipliedPoly;
	}

	@Override
	public void addTerm(Term term) {
		DNode<Term> newnode = new DNode<Term>(term, null, null);
		if (poly.isEmpty()) { 
			poly.addFirst(newnode);
		} else {
			DNode<Term> current = poly.getFirstNode();
			do {
				if (current.getInfo().expo < term.expo && current.getNext() == poly.getTrailer() || current.getInfo().expo < term.expo && current.getPrev() == poly.getHeader()) {
					poly.addBefore(current, newnode);
					return;
				} else if (current.getInfo().expo > term.expo && current.getNext() == poly.getTrailer() || current.getInfo().expo > term.expo && current.getNext().getInfo().expo < term.expo) {
					poly.addAfter(current, newnode);
					return;
				} else if (current.getInfo().expo == term.expo) {
					newnode.setInfo(new Term(current.getInfo().coeff + term.coeff, term.expo));
					current.getPrev().setNext(newnode);
					newnode.setPrev(current.getPrev());
					newnode.setNext(current.getNext());
					current.getNext().setPrev(newnode);
					
					current.setNext(null);
					current.setPrev(null);
					return;
				} else {
					current = poly.getNextNode(current);
				}
			} while (current.getNext() != null);
			poly.addAfter(current, newnode);
		}
	}

	@Override
	public double evaluate(double val) {
		double sum = 0;

		DNode<Term> current = poly.getFirstNode();
		do {
			sum += current.getInfo().coeff * Math.pow(val, current.getInfo().expo);
			current = poly.getNextNode(current);
		} while (current.getNext() != null);

		return sum;
	}

	@Override
	public String dump() {
		if (poly.isEmpty())
			return "Empty Polynomial";
		else {
			StringBuilder builder = new StringBuilder();
			DNode<Term> current = poly.getFirstNode();
			do {
                builder.append("(" + current.getInfo().coeff + ", " + current.getInfo().expo + ") ");
                current = poly.getNextNode(current);
            } while (current.getNext() != null);
			builder.append("\n");
			return builder.toString();
		}
	}
	public DLinkedList<Term> getPoly() {
		return poly;
	}
}
