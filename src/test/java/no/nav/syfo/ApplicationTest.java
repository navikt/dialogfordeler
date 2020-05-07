package no.nav.syfo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class ApplicationTest {
    @MockBean
    @Qualifier("Eia2JmsTemplate")
    private JmsTemplate eia2JmsTemplate;

    @MockBean
    @Qualifier("Padm2JmsTemplate")
    private JmsTemplate padm2JmsTemplate;

    @MockBean
    @Qualifier("EbrevJmsTemplate")
    private JmsTemplate ebrevJmsTemplate;

    @MockBean
    @Qualifier("UtsendingJmsTemplate")
    private JmsTemplate utsendingJmsTemplate;

    @Test
    public void test() {
    }
}
