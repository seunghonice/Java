/*
 * (C) 2016 CSE2010 HW #2
 * 
 * DO NOT MODIFY THIS CLASS!
 */

public interface Polynomial {
	/*
	 * Returns the degree of the polynomial
	 */
	public int getDegree();

	/*
	 * Returns the coefficient for the given input term "exponent"
	 */
	public double getCoefficient(final int exponent);
	
	/*
	 * Add this object with a given input "p" and returns the new object.
	 * This object should "NOT" be changed as a result of this operation.
	 */
	public Polynomial padd(final Polynomial p);
	
	/*
	 * Multiply this object with a given input "p" and returns the new object.
	 * This object should "NOT" be changed as a result of this operation.
	 */
	Polynomial pmult(final Polynomial p);

	/*
	 * Insert a new term to this polynomial object.
	 * Merge terms if a term with the same exponent
	 * as the given input term already exists.
	 */
	public void addTerm(final Term term);
	
	/*
	 * Returns the result of the polynomial evaluation for a given input "x"
	 */
	public double evaluate(final double val);
	
	/*
	 * Returns the contents of the current polynomial as a sequence of
	 * (coeff_n, expo_n) (coeff_n-1, expo_n-1) ... (coeff_0, expo_0) 
	 * in decreasing order of terms.
	 */
	public String dump();

}