package ineor.vat.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ineor.vat.json.Country;
import ineor.vat.json.RatePeriod;
import ineor.vat.json.RateValue;
import ineor.vat.utils.VATService;
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class RestAPIControllerTest {
	private MockMvc mockMvc;
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),                        
            Charset.forName("utf8")                     
            );
    
    private VATService vatServiceMock;
    private byte[] jSonVAT;
    private static final String JSON_PATH="/post.json";
    private List<Country> mockResult;
    @Before
    public void init() throws IOException, URISyntaxException{
    	jSonVAT = Files.readAllBytes(
    					Paths.get(getClass().getResource(JSON_PATH).toURI()));
    	vatServiceMock = Mockito.mock(VATService.class);
    	RestAPIController mocked = new RestAPIController(vatServiceMock);
    	mockMvc = MockMvcBuilders.standaloneSetup(mocked).build();
    	Country c1 = new Country();
    	RatePeriod rp = new RatePeriod();
    	c1.setActualPeriod(rp);
    	c1.setCountryCode("RU");
    	c1.setName("Russia");
    	rp.setEffectiveFrom(LocalDate.now());
    	RateValue rateValue = new RateValue();
    	rateValue.setStandard(Float.valueOf(12.5f));
    	rp.setRateValue(rateValue);
    	
    	Country c2 = new Country();
    	rp = new RatePeriod();
    	c2.setActualPeriod(rp);
    	c2.setCountryCode("SL");
    	c2.setName("Slovenia");
    	rp.setEffectiveFrom(LocalDate.now());
    	rateValue = new RateValue();
    	rateValue.setStandard(Float.valueOf(12.5f));
    	rp.setRateValue(rateValue);
    	mockResult = Arrays.asList(c1,c2);
    	Mockito.when(vatServiceMock.getLowestVATCountries(Mockito.any(),
				Mockito.anyInt(), Mockito.anyString(), Mockito.any()))
    			.thenReturn(mockResult);

    	Mockito.when(vatServiceMock.getHighestVATCountries(Mockito.any(),
				Mockito.anyInt(), Mockito.anyString(), Mockito.any()))
    			.thenReturn(mockResult);

    }
    @Test
    public void lowestAndHighestVATTest() throws Exception{
    	for(String url: Arrays.asList(VATService.HIGHEST_CNTR_REST_URL,VATService.LOWEST_CNTR_REST_URL)){
    	mockMvc.perform(post(url).contentType(APPLICATION_JSON_UTF8).content(jSonVAT))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].country_code", is("RU")))
        .andExpect(jsonPath("$[0].name", is("Russia")))
        .andExpect(jsonPath("$[0].period.rates.standard", is(12.5)))
        .andExpect(jsonPath("$[1].country_code", is("SL")))
        .andExpect(jsonPath("$[1].name", is("Slovenia")))
        .andExpect(jsonPath("$[1].period.rates.standard", is(12.5))) ;
    	}
    }
  
}
