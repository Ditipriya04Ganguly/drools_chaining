

package io.github.aasaru.drools.section06;

import io.github.aasaru.drools.Common;
import io.github.aasaru.drools.domain.Passport;
import io.github.aasaru.drools.domain.VisaApplication;
import io.github.aasaru.drools.repository.ApplicationRepository;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.Agenda;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public class VisaIssue {
  public static void main(final String[] args) {
    int step=5;
    System.out.println("Running step " + step);
    KieSession ksession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("VisaIssueStep" + step);

    ksession.addEventListener(new AgendaGroupEventListener(System.out));

    List<Passport> passports = ApplicationRepository.getPassports();

    if (step == 5) {
      if (Common.promptForYesNoQuestion("Do you want to set all passports as expired?")) {
        System.out.println("Setting all passports as expired before Drools session starts");
        passports.forEach(passport -> passport.setExpiresOn(LocalDate.MIN));
      }
    }

    passports.forEach(ksession::insert);


    List<VisaApplication> visaApplications = ApplicationRepository.getVisaApplications();
    visaApplications.forEach(ksession::insert);



    if (step == 5) {
      Agenda agenda = ksession.getAgenda();
      agenda.getAgendaGroup("issue-visa").setFocus();
      agenda.getAgendaGroup("validate-application").setFocus();
      agenda.getAgendaGroup("validate-passport").setFocus();
    }


    System.out.println("==== DROOLS SESSION START ==== ");
    ksession.fireAllRules();
    if (Common.disposeSession) {
      ksession.dispose();
    }
    System.out.println("==== DROOLS SESSION END ==== ");

  }

}
