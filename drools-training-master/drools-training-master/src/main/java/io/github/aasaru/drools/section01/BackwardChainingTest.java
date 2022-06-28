package io.github.aasaru.drools.section01;

import org.kie.api.KieServices;

import org.kie.api.runtime.KieSession;

import io.github.aasaru.drools.model.Location;



public class BackwardChainingTest {

	public static void main(String[] args) {
		try {
			
	      KieSession ksession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("BackwardChainingStep1");
	      ksession.insert(new Location("desk", "office"));
	      ksession.insert(new Location("flashlight", "desk"));
	      ksession.insert(new Location("envelop", "desk"));
	      ksession.insert(new Location("key", "envelop"));
	
	      ksession.insert("go1");
	      
	      ksession.fireAllRules();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

}
