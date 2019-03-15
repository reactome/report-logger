package org.reactome.server.digester;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactome.server.config.ReportJpaConfig;
import org.reactome.server.domain.TargetDigester;
import org.reactome.server.repository.TargetDigesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ReportJpaConfig.class},
                      loader = AnnotationConfigContextLoader.class)
@Transactional
public class ReportDigesterTest {

    @Autowired
    public TargetDigesterRepository targetDigesterRepository;

    @Test
    public void weeklyTargetReport() {
        Date lastWeek = Date.from(LocalDateTime.now().minusWeeks(1).atZone(ZoneId.systemDefault()).toInstant());
        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        List<TargetDigester> list = targetDigesterRepository.findByFromAndToDate(lastWeek, now);
        for (TargetDigester targetDigester : list) {
            System.out.println(targetDigester.getTerm() + " - " + targetDigester.getUniqueIPs() + " - " + targetDigester.getCount());
        }
    }
}
