package ineor.vat.json;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import ineor.vat.controller.RestExceptionHandler;
/**
 * Represents error details JSON object used in {@link RestExceptionHandler}
 * @author Oleg
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDetails {
	@JsonProperty("description")
	private String description;
	@JsonProperty ("date_time")
	private LocalDateTime time;
	
	@JsonProperty("description")
	public String getDescription() {
		return description;
	}
	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}
	
	@JsonProperty ("date_time")
	public LocalDateTime getTime() {
		return time;
	}
	@JsonProperty ("date_time")
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	public ErrorDetails(String description, LocalDateTime time) {
		super();
		this.description = description;
		this.time = time;
	}
	
}
