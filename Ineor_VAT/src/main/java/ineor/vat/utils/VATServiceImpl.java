package ineor.vat.utils;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import ineor.vat.json.Country;
import ineor.vat.json.JsonVAT;
@Component
public class VATServiceImpl implements VATService {
	@Override
	public List<Country> getHighestVATCountries(JsonVAT vatinfo, int limit, String vatType, LocalDate forDate) {
		return VATUtils.getHighestVATCountries(vatinfo, limit, vatType, forDate);
	}
	@Override
	public List<Country> getLowestVATCountries(JsonVAT vatinfo, int limit, String vatType, LocalDate forDate) {
		return VATUtils.getLowestVATCountries(vatinfo, limit, vatType, forDate);
	}
}
