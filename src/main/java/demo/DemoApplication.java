package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@SpringBootApplication
@RestController
@RequestMapping("/demo")
public class DemoApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @RequestMapping(value = "/blocking", method = RequestMethod.GET)
    public HttpEntity<String> testBlockingResult() {

        try {
            Thread.sleep(1_000); // sleep 1 second.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<String>("body string", HttpStatus.OK);
    }

    @RequestMapping(value = "/nonBlocking", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<String>> testNonBlockingResult() {

        DeferredResult<ResponseEntity<String>> deferredResult = new DeferredResult<ResponseEntity<String>>();

        new Thread(

            () -> {
    
                try {
                    Thread.sleep(1_000); // sleep 1 second.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
    
                deferredResult.setResult(new ResponseEntity<String>("body string", HttpStatus.OK));
    
            }

        ).start();

        return deferredResult;
    }

}
