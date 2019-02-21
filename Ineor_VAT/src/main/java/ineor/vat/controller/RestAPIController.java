package ineor.vat.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ineor.vat.json.Country;
import ineor.vat.json.JsonVAT;
import ineor.vat.utils.VATService;
/**
 * Rest controller
 * @author Oleg
 */
@RestController
public class RestAPIController {
	/**
	 * Service used for obtaining required data
	 */
	@Autowired
	private VATService vatService;
	
	
	/**
	 * Constructor just for testing
	 * @param vatService
	 */
	protected RestAPIController( VATService vatService) {
		this.vatService = vatService;
	}
	/**
	 * This method can be used for evaluating list of countries with the lowest VAT.<br>
	 * It filters the incoming JSON request by provided limit (maximum size of output),<br>
	 * VAT type and date. <br>  
	 * Request url example:<br>
	 * <b>{@code /api/1.0/getLowestVATCountries/?limit=5&vatType=standard&forDate=2019-02-20}</b>
	 * @param jsonVAT - incoming JSON request body
	 * @param limit - maximum size of output list. Not required, defaults to 3.
	 * @param vatType - type of VAT to filter (standard, reduce, super_reduce,parking). Not required, defaults to "standard"
	 * @param forDate - date of requesting VATs in format <b>yyyy-MM-dd</b>. Not required, defaults to current date
	 * @return the list of countries with the lowest VAT.
	 */
    @RequestMapping(VATService.LOWEST_CNTR_REST_URL)
    public List<Country> lowestVATCountries(@RequestBody JsonVAT jsonVAT,
    							   @RequestParam(defaultValue="3") int limit,
    							   @RequestParam(defaultValue="standard") String vatType,
    							   @RequestParam (required=false)
    							   @DateTimeFormat(iso=ISO.DATE)LocalDate forDate) {
  
    	if(forDate==null)
    		forDate = LocalDate.now();
    	
        return  vatService.getLowestVATCountries(jsonVAT, limit,vatType, forDate);
    }
    
	/**
	 * This method can be used for evaluating list of countries with the highest VAT.<br>
	 * It filters the incoming JSON request by provided limit (maximum size of output),<br>
	 * VAT type and date. <br>  
	 * Request url example:<br>
	 * <b>{@code /api/1.0/getHighestVATCountries/?limit=5&vatType=standard&forDate=2019-02-20}</b>
	 * @param jsonVAT - incoming JSON request body
	 * @param limit - maximum size of output list. Not required, defaults to 3.
	 * @param vatType - type of VAT to filter (standard, reduce, super_reduce,parking). Not required, defaults to "standard"
	 * @param forDate - date of requesting VATs in format <b>yyyy-MM-dd</b>. Not required, defaults to current date
	 * @return the list of countries with the highest VAT.
	 */

    @RequestMapping(VATService.HIGHEST_CNTR_REST_URL)
    public List<Country> highestVATCountries(@RequestBody JsonVAT jsonVAT,
    							   @RequestParam(defaultValue="3") int limit,
    							   @RequestParam(defaultValue="standard") String vatType,
    							   @RequestParam (required=false)
    							   @DateTimeFormat(iso=ISO.DATE)LocalDate forDate) {
  
    	if(forDate==null)
    		forDate = LocalDate.now();
        return  vatService.getHighestVATCountries(jsonVAT, limit,vatType, forDate);
    }

}
