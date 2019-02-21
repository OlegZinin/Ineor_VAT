package ineor.vat.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import ineor.vat.controller.RestAPIController;

/**
 * Represents initial incoming JSON object in the RequestBody in {@link RestAPIController#highestVATCountries} 
 * and {@link RestAPIController#lowestVATCountries } 
 * @author Oleg
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "rates" })
public class JsonVAT {
	@JsonProperty("rates")
	private List<Rate> rates = null;
	
	@JsonProperty("rates")
	public List<Rate> getRates() {
		return rates;
	}
	@JsonProperty("rates")
	public void setRates(List<Rate> rates) {
		this.rates = rates;
	}
	@Override
	public String toString() {
		return "JsonVAT [rates=" + rates + "]";
	}
}
