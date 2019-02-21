package ineor.vat.json;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Represents RatePeriod JSON object storing information of actual date and rate value
 * @see {@link RateValue}
 * @author Oleg
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "effective_from", "rates" })
public class RatePeriod {
	/**
	 * Date of actuality for the rate value
	 */
	@JsonProperty("effective_from")
	private LocalDate effectiveFrom;
	
	/**
	 * {@link RateValue} for this Rate period
	 */
	@JsonProperty("rates")
	private RateValue rateValue;

	/**
	 * Date of actuality for the rate value
	 */
	@JsonProperty("effective_from")
	public LocalDate getEffectiveFrom() {
		return effectiveFrom;
	}
	/**
	 * Date of actuality for the rate value
	 */
	@JsonProperty("effective_from")
	public void setEffectiveFrom(LocalDate effectiveFrom) {
		this.effectiveFrom = effectiveFrom;
	}

	/**
	 * {@link RateValue} for this Rate period
	 */
	@JsonProperty("rates")
	public RateValue getRateValue() {
		return rateValue;
	}
	/**
	 * {@link RateValue} for this Rate period
	 */
	@JsonProperty("rates")
	public void setRateValue(RateValue rateValue) {
		this.rateValue = rateValue;
	}
	/**
	 * Returns {@code true} if this rate period is actual on given date
	 */
    public boolean isActualOn(LocalDate onDate){
        return getEffectiveFrom().isBefore(onDate);
    }
	@Override
	public String toString() {
		return "RatePeriod [effectiveFrom=" + effectiveFrom + ", rateValue=" + rateValue + "]";
	}
    
}
