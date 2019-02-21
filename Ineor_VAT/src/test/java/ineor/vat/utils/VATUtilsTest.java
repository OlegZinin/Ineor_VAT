package ineor.vat.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ineor.vat.json.Country;
import ineor.vat.json.JsonVAT;
import ineor.vat.json.Rate;
import ineor.vat.json.RatePeriod;
import ineor.vat.json.RateValue;

public class VATUtilsTest extends VATUtils{
	private static final Random random = new Random();
    private JsonVAT vatInfo;
    private List<LocalDate> dates;
    private List<Integer> limits;
    private List<String> vatTypes;
 
    @Before
    public void init() {
        vatInfo = generateVatInfo();
        int currentYear = LocalDate.now().getYear();
        dates = Stream.generate(
                () -> LocalDate.of(random.nextInt(currentYear + 1), 1 + random.nextInt(12), 1 + random.nextInt(28))
        ).limit(30).collect(Collectors.toList());

        limits = Stream.generate(
                () -> random.nextInt(10))
                .limit(10).collect(Collectors.toList());
        vatTypes= Arrays.asList("standard","reduced","super_reduced","reduced1","parking");
    }

    @Test
    public void testMaximumVAT(){
        limits.forEach( limit->
                dates.forEach(
                        d-> {
                        	String valType = vatTypes.get(random.nextInt(vatTypes.size()));
                            List<Country> mRates = VATUtils.getHighestVATCountries(
                            						vatInfo,limit,
                            						valType,
                            						d);
                            Float maxVAT=mRates.stream().filter(c->c.getActualPeriod()!=null)
                            			   .map(c->c.getActualPeriod().getRateValue().getVATbyType(valType))
                            			   .max(Comparator.naturalOrder()).orElse(null);
                            Assert.assertTrue(mRates.size()<=limit);
                            if(!mRates.isEmpty()) {
                                vatInfo.getRates().stream()
                                        .forEach(r -> {
                                            Optional<RatePeriod> last = VATUtils.findLatestActualPeriod(r.getPeriods(),d);
                                            last.ifPresent(l->
                                            Assert.assertFalse(
                                            String.format("Given income:\n%s\nFound VAT = %f, but max = %f",
                                            			  vatInfo.getRates(),l.getRateValue().getVATbyType(valType),maxVAT),
                                            					l.getRateValue().getVATbyType(valType)!=null && 
                                            					l.getRateValue().getVATbyType(valType)>maxVAT));
                                        });
                            }
                        }
                )
        );
    }

    @Test
    public void testMinimumVAT(){
        limits.forEach( limit->
        dates.forEach(
                d-> {
                	String valType = vatTypes.get(random.nextInt(vatTypes.size()));
                    List<Country> mRates = VATUtils.getLowestVATCountries(
                    						vatInfo,limit,
                    						valType,
                    						d);
                    Float minVAT=mRates.stream().filter(c->c.getActualPeriod()!=null)
                    			   .map(c->c.getActualPeriod().getRateValue().getVATbyType(valType))
                    			   .min(Comparator.naturalOrder()).orElse(null);
                    Assert.assertTrue(mRates.size()<=limit);
                    if(!mRates.isEmpty()) {
                        vatInfo.getRates().stream()
                                .forEach(r -> {
                                    Optional<RatePeriod> last = VATUtils.findLatestActualPeriod(r.getPeriods(),d);
                                    last.ifPresent(l->
                                    Assert.assertFalse(
                                    String.format("Given income:\n%s\nFound VAT = %f, but max = %f",
                                    			  vatInfo.getRates(),l.getRateValue().getVATbyType(valType),minVAT),
                                    					l.getRateValue().getVATbyType(valType)!=null && 
                                    					l.getRateValue().getVATbyType(valType)<minVAT));
                                });
                    }
                }
        )
);

    }

    @Test
    public void  testActualPeriod(){

        dates.forEach(d->testPeriod(d,vatInfo));
    }
    private static void testPeriod(LocalDate d, JsonVAT vat){
        vat.getRates()
                    .forEach(r->
                    {
                        Optional<RatePeriod> latestActual = VATUtils.findLatestActualPeriod(r.getPeriods(),d);
                        r.getPeriods()
                                .forEach(rp ->
                                        {
                                            if(rp.isActualOn(d)) {
                                                Assert.assertTrue(rp.getEffectiveFrom().isBefore(d));
                                                latestActual.ifPresent(latest->
                                                    Assert.assertFalse(rp.getEffectiveFrom().isAfter(latest.getEffectiveFrom()))
                                                );
                                            }
                                            else Assert.assertFalse(rp.getEffectiveFrom().isBefore(d));

                                        }
                                );
                    }
                    );
    }
    public static JsonVAT generateVatInfo(){
    	JsonVAT vatInfo = new JsonVAT();
        List<Rate> rates = new ArrayList<>();
        vatInfo.setRates(rates);

        int currentYear = LocalDate.now().getYear();

        for (int i = 0;i<random.nextInt(50);i++){
            Rate rate = new Rate();
            List<RatePeriod> periods = new ArrayList<>();
            rate.setPeriods(periods);
            for(int j=0;j<random.nextInt(10);j++){
                RatePeriod period = new RatePeriod();
                period.setEffectiveFrom(LocalDate.of(random.nextInt(currentYear+1),1+random.nextInt(12),1+random.nextInt(28)));
                RateValue rateInfo = new RateValue();
                rateInfo.setStandard((1+random.nextFloat())*(1+random.nextInt(50)));
                rateInfo.setReduced((1+random.nextFloat())*(1+random.nextInt(50)));
                rateInfo.setReduced1((1+random.nextFloat())*(1+random.nextInt(50)));
                rateInfo.setSuperReduced((1+random.nextFloat())*(1+random.nextInt(50)));
                rateInfo.setParking((1+random.nextFloat())*(1+random.nextInt(50)));
                period.setRateValue(rateInfo);
                periods.add(period);
            }
            rate.setCountryCode(Locale.getISOCountries()[random.nextInt(Locale.getISOCountries().length)]);
            rates.add(rate);
        }
        return vatInfo;
    }
}
