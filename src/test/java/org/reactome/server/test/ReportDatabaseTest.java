package org.reactome.server.test;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactome.server.config.ReportJpaConfig;
import org.reactome.server.target.Target;
import org.reactome.server.target.TargetRepository;
import org.reactome.server.target.UserAgentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

/**
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {ReportJpaConfig.class},
        loader = AnnotationConfigContextLoader.class)
@Transactional
public class ReportDatabaseTest {

    @Autowired
    private TargetRepository targetRepository;

    @Test
    public void saveTargets() {
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36";

        String googblebot = "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)";
        String yahoobot = "Mozilla/5.0 (compatible; Yahoo! Slurp; http://help.yahoo.com/help/us/ysearch/slurp)";
        String yandexbot = "Mozilla/5.0 (compatible; YandexBot/3.0; +http://yandex.com/bots)";


        UserAgentStringParser parser = UADetectorServiceFactory.getOnlineUpdatingParser();
        ReadableUserAgent browser = parser.parse(userAgent);
        ReadableUserAgent ruagoogle = parser.parse(googblebot);
        ReadableUserAgent ruayahoo = parser.parse(yahoobot);
        ReadableUserAgent ruayandex = parser.parse(yandexbot);

        targetRepository.save(new Target("A6NCF5", "192.168.0.2", userAgent, 64, new UserAgentType(browser.getType().getName())));

        List<Target> targets = new ArrayList<>(5);
        targets.add(new Target("A0A1B0GVZ2", "200.139.10.20", userAgent, 64, new UserAgentType(browser.getType().getName())));
        targets.add(new Target("CK2N1_HUMAN", "192.168.2.3", googblebot, 64, new UserAgentType(ruagoogle.getType().getName())));
        targets.add(new Target("CK2N1_HUMAN", "192.168.2.31", yahoobot, 64, new UserAgentType(ruayahoo.getType().getName())));
        targets.add(new Target("CK2N1_HUMAN", "192.168.2.32", yandexbot, 64, new UserAgentType(ruayandex.getType().getName())));
        targets.add(new Target("TTY12", "192.168.4.5", userAgent, 64, new UserAgentType(browser.getType().getName())));
        targets.add(new Target("C4orf32", "192.168.5.6", userAgent, 64, new UserAgentType(browser.getType().getName())));
        targets.add(new Target("C4orf32", "192.168.5.6", userAgent, 64, new UserAgentType(browser.getType().getName())));
        targetRepository.saveAll(targets);

        List<Target> all = targetRepository.findAll();
        assertTrue(all.size() > 5);

        Optional<Target> t = targetRepository.findOne(Example.of(new Target("TTY12")));
        assertTrue(t.isPresent());

        Target target = t.get();
        assertTrue(target.getTerm().equals("TTY12"));
    }
}