package org.reactome.server.test;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactome.server.config.ReportJpaConfig;
import org.reactome.server.domain.*;
import org.reactome.server.repository.SearchRecordRepository;
import org.reactome.server.repository.TargetRecordRepository;
import org.reactome.server.repository.TargetResourceRepository;
import org.reactome.server.repository.UserAgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.soap.SOAPBinding;
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

    @Autowired private TargetRecordRepository targetRecordRepository;
    @Autowired private SearchRecordRepository searchRecordRepository;
    @Autowired private TargetResourceRepository targetResourceRepository;
    @Autowired private UserAgentRepository userAgentRepository;

    @Test
    public void saveSearchRecord() {
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36";

        UserAgentStringParser parser = UADetectorServiceFactory.getOnlineUpdatingParser();
        ReadableUserAgent browser = parser.parse(userAgent);

        UserAgentType uaBrowser = userAgentRepository.findByName(browser.getType().getName());
        if (uaBrowser == null) uaBrowser = userAgentRepository.saveAndFlush(new UserAgentType(browser.getType().getName()));

        searchRecordRepository.save(new SearchRecord("A6NCF5N", "192.168.10.12", userAgent, 64, uaBrowser));

        assertTrue("SearchRecord must be 1", searchRecordRepository.findAll().size() == 1);
    }

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

        TargetResource tr = targetResourceRepository.findByName("UNIPROT");
        if (tr == null) targetResourceRepository.save(new TargetResource("UNIPROT"));

        UserAgentType uaBrowser = userAgentRepository.findByName(browser.getType().getName());
        if (uaBrowser == null) uaBrowser = userAgentRepository.saveAndFlush(new UserAgentType(browser.getType().getName()));

        UserAgentType uaGoogle = userAgentRepository.findByName(ruagoogle.getType().getName());
        if (uaGoogle == null) uaGoogle = userAgentRepository.saveAndFlush(new UserAgentType(ruagoogle.getType().getName()));

        UserAgentType uaYahoo = userAgentRepository.findByName(ruayahoo.getType().getName());
        if (uaYahoo == null) uaYahoo = userAgentRepository.saveAndFlush(new UserAgentType(ruayahoo.getType().getName()));

        UserAgentType uaYandex = userAgentRepository.findByName(ruayandex.getType().getName());
        if (uaYandex == null) uaYandex = userAgentRepository.saveAndFlush(new UserAgentType(ruayandex.getType().getName()));

        targetRecordRepository.save(new TargetRecord("A6NCF5", "192.168.0.2", userAgent, 64, uaBrowser, tr));

        List<TargetRecord> targets = new ArrayList<>(5);
        targets.add(new TargetRecord("A0A1B0GVZ2", "200.139.10.20", userAgent, 64, uaBrowser, tr));
        targets.add(new TargetRecord("CK2N1_HUMAN", "192.168.2.3", googblebot, 64, uaGoogle, tr));
        targets.add(new TargetRecord("CK2N1_HUMAN", "192.168.2.31", yahoobot, 64, uaYahoo, tr));
        targets.add(new TargetRecord("CK2N1_HUMAN", "192.168.2.32", yandexbot, 64, uaYandex, tr));
        targets.add(new TargetRecord("TTY12", "192.168.4.5", userAgent, 64, uaBrowser, tr));
        targets.add(new TargetRecord("C4orf32", "192.168.5.6", userAgent, 64, uaBrowser, tr));
        targets.add(new TargetRecord("C4orf32", "192.168.5.6", userAgent, 64, uaBrowser, tr));
        targetRecordRepository.saveAll(targets);

        List<? extends AbstractRecord> all = targetRecordRepository.findAll();
        assertTrue(all.size() > 5);

        Optional<TargetRecord> t = targetRecordRepository.findOne(Example.of(new TargetRecord("TTY12")));
        assertTrue(t.isPresent());

        TargetRecord target = t.get();
        assertTrue(target.getTerm().equals("TTY12"));
    }
}