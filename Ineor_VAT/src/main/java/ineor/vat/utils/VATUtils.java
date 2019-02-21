package ineor.vat.utils;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import ineor.vat.json.Country;
import ineor.vat.json.JsonVAT;
import ineor.vat.json.Rate;
import ineor.vat.json.RatePeriod;

/**
 * Utility helper class with static methods to obtain required functionality
 * @author Oleg
 */
public class VATUtils {
	protected VATUtils(){}
	
	/**
	 * Returns filtered and sorted list of countries by parameters
	 * @see {@link Country}<br>
	 * {@link VATUtils#getHighestVATCountries(JsonVAT, int, String, LocalDate)}<br>
	 * {@link VATUtils#getLowestVATCountries(JsonVAT, int, String, LocalDate)}<br>
	 * @param jsonVat - incoming JSON object to be filtered
	 * @param limit - maximum size of resulted list
	 * @param vatType - type of VATs to filter (standard, reduced, super_reduced, parking)
	 * @param date - date of VATs
	 * @param comparator - comparator used for sorting
	 * @return filtered and sorted list of countries by parameters
	 */
	private static List<Country> getSortedCountryRates(JsonVAT jsonVat, int limit, 
				String vatType,LocalDate date, Comparator<Rate> comparator){
        return jsonVat.getRates().stream()
                .filter(r->{
                	Optional<RatePeriod> last = findLatestActualPeriod(r.getPeriods(),date);
                	return last.isPresent() && last.get().getRateValue().getVATbyType(vatType)!=null;
                })
                .sorted(comparator)
                .limit(limit)
                .map(r-> buildCountry(r,date))
                .collect(Collectors.toList());
    }
	/**
	 * Constructs a country object by given Rate. Used internally in {@link VATUtils#getSortedCountryRates}
	 *@see {@link Country} <br>
	 *{@link Rate} 
	 */
	private static Country buildCountry(Rate rate,LocalDate date){
		Country result = new Country();
		result.setCountryCode(rate.getCountryCode());
		result.setName(rate.getName());
		result.setActualPeriod(findLatestActualPeriod(rate.getPeriods(), date).orElse(null));
		return result;
	}
	/**
	 * Returns the latest {@link RatePeriod}, actual on given date <br>
	 * Helper function used by other methods.
	 */
	protected static Optional<RatePeriod> findLatestActualPeriod(Collection<RatePeriod> periods, LocalDate onDate){
        return periods.stream()
                .filter(p->p.isActualOn(onDate))
                .max(Comparator.comparing(RatePeriod::getEffectiveFrom));
    }
	
	/**
	 * Returns function to be used in comparator for sorting country rates
	 */
	private final static Function<Rate,Float> byActualPeriod(LocalDate forDate, String vatType){
		  
	      return r->findLatestActualPeriod(r.getPeriods(),forDate)
	                           .orElse(null).
	                           getRateValue().getVATbyType(vatType);
	}
	
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
	public static List<Country> getLowestVATCountries(JsonVAT vatinfo, int limit,String vatType, LocalDate forDate){
        return  getSortedCountryRates(vatinfo,limit,
        				              vatType,forDate,
        				              Comparator.comparing(byActualPeriod(forDate,vatType)));
    }
	
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
	
    public static List<Country> getHighestVATCountries(JsonVAT vatinfo, int limit,String vatType, LocalDate forDate){
        return  getSortedCountryRates(vatinfo,limit,
        							  vatType,forDate,
        							  Comparator.comparing(byActualPeriod(forDate,vatType)).reversed());
    }
}
