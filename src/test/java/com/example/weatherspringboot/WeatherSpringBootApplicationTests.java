package com.example.weatherspringboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:weather-test",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "jwt.secret=MTIzNDU2Nzg5MDEyMzQ1Njc4OTAxMjM0NTY3ODkwMTI=",
        "jwt.expiration=60"
})
class WeatherSpringBootApplicationTests {

    @Test
    void contextLoads() {
    }

}
