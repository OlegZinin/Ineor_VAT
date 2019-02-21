package ineor.vat.json;

import java.lang.reflect.Field;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents Rate value JSON object storing VAT values of different types.<br>
 * Currently there are six VAT types:<br>
 * ["standard","reduced","reduced1","reduced2","parking","super_reduced"]<br>
 * Adding one more VAT type means adding one more field of Float to this class 
 * with the name corresponding to that of VAT type.  
 * 
 * @author Oleg
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RateValue {
	@JsonProperty("standard")
	private Float standard;
	@JsonProperty("standard")
	public Float getStandard() {
		return standard;
	}
	@JsonProperty("standard")
	public void setStandard(Float standard) {
		this.standard = standard;
	}
	
	@JsonProperty("reduced")
	private Float reduced;
	@JsonProperty("reduced")
	public Float getReduced() {
		return reduced;
	}
	@JsonProperty("reduced")
	public void setReduced(Float reduced) {
		this.reduced = reduced;
	}

	@JsonProperty("reduced1")
	private Float reduced1;
	@JsonProperty("reduced1")
	public Float getReduced1() {
		return reduced1;
	}
	@JsonProperty("reduced1")
	public void setReduced1(Float reduced1) {
		this.reduced1 = reduced1;
	}
	@JsonProperty("reduced2")
	private Float reduced2;
	@JsonProperty("reduced2")
	public Float getReduced2() {
		return reduced2;
	}
	@JsonProperty("reduced2")
	public void setReduced2(Float reduced2) {
		this.reduced2 = reduced2;
	}
	
	@JsonProperty("super_reduced")
	private Float super_reduced;
	@JsonProperty("super_reduced")
	public Float getSuperReduced() {
		return super_reduced;
	}
	@JsonProperty("super_reduced")
	public void setSuperReduced(Float super_reduced) {
		this.super_reduced = super_reduced;
	}
	
	@JsonProperty("parking")
	private Float parking;
	@JsonProperty("parking")
	public Float getParking() {
		return parking;
	}
	@JsonProperty("parking")
	public void setParking(Float parking) {
		this.parking = parking;
	}	
	
	/**
	 * Returns the value of VAT by the given vatType.<br>
	 * The method uses Reflection API to obtain field of this class by given name
	 * and then returns its value.<br>
	 * <font color=red><b>Result must be checked to {@code null}, as in case of non-existent field, null is returned</b> </font>
	 */
	public Float getVATbyType(String vatType){
		if(vatType == null)
			return null;
		try {
			Field field = this.getClass().getDeclaredField(vatType.toLowerCase());
			field.setAccessible(true);
			return (Float) field.get(this);
		} catch (Exception e) {
			return null;
		}
	}
	@Override
	public String toString() {
		return "RateValue [standard=" + standard + ", reduced=" + reduced + ", reduced1=" + reduced1 + ", reduced2="
				+ reduced2 + ", super_reduced=" + super_reduced + ", parking=" + parking + "]";
	}
}
