package modles;

/**
 * Created by jiang on 16/8/19.
 */
import java.io.Serializable;

import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieScanner;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;


public class KieSessionFactoryStream implements Serializable {
    private  KieSession ksession ;
    private  KieContainer kContainer = null;
    private  KieScanner kScanner = null;

    public   KieSession getKieSession() {
        if (ksession == null)
            ksession = getNewKieSession();
        return ksession;
    }

    public   KieSession getNewKieSession() {
        KieServices kieServices = KieServices.Factory.get();
        KieModule kieModule = null;
        try {
            kContainer = kieServices.newKieContainer(kieServices.newReleaseId("cn.bmkp.jiang", "droolsCost", "LATEST"));
            kScanner = kieServices.newKieScanner(kContainer);
            kScanner.start(10000L);
        } catch ( Exception e ) {
            System.out.println("Error - " + e.getMessage());
        }

        KieBaseConfiguration config = kieServices.newKieBaseConfiguration();
        config.setOption(EventProcessingOption.STREAM);

        if ( kContainer == null ) return null;
        KieBase kbase = kContainer.newKieBase(config);
        return kbase.newKieSession();

    }
    public void refresh(){
        if (ksession != null) {
            ksession.dispose();
            ksession = getNewKieSession();
        }



    }
}

//public class KieSessionFactoryStream implements Serializable {
//    private static KieSession ksession ;
//    private static KieContainer kContainer = null;
//    private static KieScanner kScanner = null;
//
//    public  static KieSession getKieSession() {
//        if (ksession == null)
//            ksession = getNewKieSession();
//        return ksession;
//    }
//
//    public  static KieSession getNewKieSession() {
//        KieServices kieServices = KieServices.Factory.get();
//        KieModule kieModule = null;
//        try {
//            kContainer = kieServices.newKieContainer(kieServices.newReleaseId("cn.bmkp.jiang", "droolsCost", "LATEST"));
//            kScanner = kieServices.newKieScanner(kContainer);
//            kScanner.start(10000L);
//        } catch ( Exception e ) {
//            System.out.println("Error - " + e.getMessage());
//        }
//
//        KieBaseConfiguration config = kieServices.newKieBaseConfiguration();
//        config.setOption(EventProcessingOption.STREAM);
//
//        if ( kContainer == null ) return null;
//        KieBase kbase = kContainer.newKieBase(config);
//        return kbase.newKieSession();
//
//    }
//}
