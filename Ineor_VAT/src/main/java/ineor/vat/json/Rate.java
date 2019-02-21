package ineor.vat.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Represents Rate JSON object storing a list of {@link RatePeriod}s<br>
 * Extends {@link Country} adding information of its rate periods
 * @see
 * {@link Country}<br>
 * {@link RatePeriod}
 * @author Oleg
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "name", "country_code", "periods" })
public class Rate extends Country{

	/**
	 * List of existing {@link RatePeriod}s for a country
	 */
	@JsonProperty("periods")
	private List<RatePeriod> periods = null;

	/**
	 * List of existing {@link RatePeriod}s for a country
	 */
	@JsonProperty("periods")
	public List<RatePeriod> getPeriods() {
		return periods;
	}

	/**
	 * List of existing {@link RatePeriod}s for a country
	 */
	@JsonProperty("periods")
	public void setPeriods(List<RatePeriod> periods) {
		this.periods = periods;
	}

	@Override
	public String toString() {
		return "Rate [periods=" + periods + "]";
	}
		
}