package mpdgr.planealert.web.apiclients.flightaware;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Data
public class FlightAwareClient {
    public String getWebContent(String flightNo) {
        RestTemplate faTemplate = new RestTemplate();
        String url = String.format("https://flightaware.com/live/flight/%s", flightNo);
        System.out.println("FlightAware call");
        return faTemplate.getForObject(url, String.class);
   }
}

