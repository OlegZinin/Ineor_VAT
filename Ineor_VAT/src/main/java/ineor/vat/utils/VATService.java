package ineor.vat.utils;

import java.time.LocalDate;
import java.util.List;

import ineor.vat.json.Country;
import ineor.vat.json.JsonVAT;
/**
 * Service interface to be implemented by actual functional data providers
 * @author Oleg
 */
public interface VATService {
	
	public String LOWEST_CNTR_REST_URL = "/api/1.0/getLowestVATCountries";
	
	
	public String HIGHEST_CNTR_REST_URL = "/api/1.0/getHighestVATCountries";
	
	
	/**
	 * Returns list of countries with the highest VAT, filtering incoming {@link JsonVAT} with given parameters.<br>
	 * 
	 * @see
	 * {@link Country}
	 * @param vatinfo - incoming JSON object to be filtered
	 * @param limit - maximum size of resulted list
	 * @param vatType - type of VATs to filter (standard, reduced, super_reduced, parking)
	 * @param forDate - date of VATs
	 * @return filtered and sorted list of countries by parameters
	 */
	List<Country> getHighestVATCountries(JsonVAT vatinfo, int limit,String vatType, LocalDate forDate);
	
	/**
	 * Returns list of countries with the lowest VAT, filtering incoming {@link JsonVAT} with given parameters.<br>
	 * 
	 * @see
	 * {@link Country}
	 * @param vatinfo - incoming JSON object to be filtered
	 * @param limit - maximum size of resulted list
	 * @param vatType - type of VATs to filter (standard, reduced, super_reduced, parking)
	 * @param forDate - date of VATs
	 * @return filtered and sorted list of countries by parameters
	 */
	List<Country> getLowestVATCountries(JsonVAT vatinfo, int limit,String vatType, LocalDate forDate);
}
