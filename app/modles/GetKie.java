package modles;

import org.drools.compiler.kproject.ReleaseIdImpl;
import org.drools.core.io.impl.UrlResource;
import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.io.InputStream;

/**
 * Created by jiang on 16/8/18.
 */
public class GetKie {

    private static KieSession kieSession = null;

    private static String URL = "http://localhost:8080/kie-drools/maven2/cn/bmkp/jiang/droolsCost/1.0/droolsCost-1.0.jar";

    private static void init(){
        try {
            KieServices ks = KieServices.Factory.get();
            KieRepository kr = ks.getRepository();
            UrlResource urlResource = (UrlResource) ks.getResources().newUrlResource(URL);
            ReleaseIdImpl releaseId = new ReleaseIdImpl("cn.bmkp.jiang", "droolsCost", "1.0");
            urlResource.setUsername("admin");
            urlResource.setPassword("123456");
            urlResource.setBasicAuthentication("enabled");
            InputStream is = urlResource.getInputStream();
            KieModule kModule = kr.addKieModule(ks.getResources().newInputStreamResource(is));
            KieContainer kContainer = ks.newKieContainer(kModule.getReleaseId());
            GetKie.kieSession = kContainer.newKieSession();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public KieSession getKieSession(){
        if(GetKie.kieSession == null){
            init();
        }
        return GetKie.kieSession;
    }

}
