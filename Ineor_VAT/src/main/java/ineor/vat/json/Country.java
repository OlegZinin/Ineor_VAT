package ineor.vat.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import ineor.vat.controller.RestAPIController;

/**
 * Represents JSON object to be serialized as output result of invoking {@link RestAPIController#highestVATCountries} 
 * and {@link RestAPIController#lowestVATCountries}. <br>
 * It has a bit different structure comparing to the {@link Rate} and uses only one actual {@link RatePeriod}<br>
 * 
 * @author Oleg
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "name", "country_code", "period" })
public class Country {
	/**
	 * Name of the country
	 */
	@JsonProperty("name")
	protected String name;
	/**
	 * Two-letter country code
	 */
	@JsonProperty("country_code")
	protected String countryCode;
	/**
	 * The latest actual rate period storing actual VAT value
	 */
	@JsonProperty("period")
	private RatePeriod actualPeriod;
	
	/**
	 * The latest actual rate period storing actual VAT value
	 */
	@JsonProperty("period")
	public RatePeriod getActualPeriod() {
		return actualPeriod;
	}
	/**
	 * The latest actual rate period storing actual VAT value
	 */
	@JsonProperty("period")
	public void setActualPeriod(RatePeriod actualPeriod) {
		this.actualPeriod = actualPeriod;
	}
	/**
	 * Name of the country
	 */
	@JsonProperty("name")
	public String getName() {
		return name;
	}
	/**
	 * Name of the country
	 */
	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Two-letter country code
	 */
	@JsonProperty("country_code")
	public String getCountryCode() {
		return countryCode;
	}
	/**
	 * Two-letter country code
	 */
	@JsonProperty("country_code")
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	@Override
	public String toString() {
		return "Country [name=" + name + ", countryCode=" + countryCode + ", actualPeriod=" + actualPeriod + "]";
	}
}
