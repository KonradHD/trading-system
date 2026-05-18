package com.tradingsystem.trading_bot;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.NONE,
	properties = "spring.autoconfigure.exclude=org.springframework.cloud.autoconfigure.LifecycleMvcEndpointAutoConfiguration"
)
class TradingBotApplicationTests {


}
