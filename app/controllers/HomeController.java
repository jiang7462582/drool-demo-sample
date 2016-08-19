package controllers;

import cn.bmkp.jiang.droolscost.User;
import modles.GetKie;
import modles.UserCostRule;
import org.kie.api.runtime.KieSession;
import play.mvc.*;

import views.html.*;

import java.io.InputStream;

import org.drools.compiler.kproject.ReleaseIdImpl;
import org.drools.core.io.impl.UrlResource;
import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;

import com.fasterxml.jackson.databind.JsonNode;

import javax.inject.Inject;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    @Inject
    GetKie kie;

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {

//        String url = "http://localhost:8080/kie-drools/maven2/cn/bmkp/jiang/droolsCost/1.0/droolsCost-1.0.jar";
//
//        KieServices ks = KieServices.Factory.get();
//        KieRepository kr = ks.getRepository();
//        UrlResource urlResource = (UrlResource) ks.getResources().newUrlResource(url);
//        ReleaseIdImpl releaseId = new ReleaseIdImpl("cn.bmkp.jiang", "droolsCost", "1.0");
//        urlResource.setUsername("admin");
//        urlResource.setPassword("123456");
//        urlResource.setBasicAuthentication("enabled");
//        try {
//            InputStream is = urlResource.getInputStream();
//            KieModule kModule = kr.addKieModule(ks.getResources().newInputStreamResource(is));
//            KieContainer kContainer = ks.newKieContainer(kModule.getReleaseId());
//            StatelessKieSession kSession = kContainer.newStatelessKieSession("defaultStatelessKieSession");
//           // PaymentInfo m = new PaymentInfo();
//            User user = new User();
//            user.setDistance(7L);
//            user.setWaitTime(30L);
//
//           // m.setMoneyAmount(5001);
//            //kSession.execute(m);
//            KieSession kieSession = kContainer.newKieSession();
//            kieSession.insert(user);
//            kieSession.fireAllRules();
//            System.out.println(user.getCost());
//
//
//        }catch (Exception e){
//            System.out.println(e.getCause());
//        }

        return ok(index.render("Your new application is ready."));
    }


    public Result userCost(){
        JsonNode rb = request().body().asJson();
        Long distance = rb.get("distance").asLong();
        Long waitTime = rb.get("waitTime").asLong();


        String url = "http://localhost:8080/kie-drools/maven2/cn/bmkp/jiang/droolsCost/1.0/droolsCost-1.0.jar";
        //String url = "http://106.75.130.135:8099/kie-web/maven2/zebra/rule/1.0/rule-1.0.jar";
        KieServices ks = KieServices.Factory.get();
        KieRepository kr = ks.getRepository();
        UrlResource urlResource = (UrlResource) ks.getResources().newUrlResource(url);
        ReleaseIdImpl releaseId = new ReleaseIdImpl("cn.bmkp.jiang", "droolsCost", "1.0");
        urlResource.setUsername("admin");
        urlResource.setPassword("123456");
        urlResource.setBasicAuthentication("enabled");
        Double cost = null;
        try {
            InputStream is = urlResource.getInputStream();
            KieModule kModule = kr.addKieModule(ks.getResources().newInputStreamResource(is));
            KieContainer kContainer = ks.newKieContainer(kModule.getReleaseId());
//            StatelessKieSession kSession = kContainer.newStatelessKieSession("defaultStatelessKieSession");
            // PaymentInfo m = new PaymentInfo();
            User user = new User();
            user.setDistance(distance);
            user.setWaitTime(waitTime);

            // m.setMoneyAmount(5001);
            //kSession.execute(m);
            KieSession kieSession = kContainer.newKieSession();
            kieSession.insert(user);
            kieSession.fireAllRules();
            cost = user.getCost();



        }catch (Exception e){
            return internalServerError("Oops");
        }
        return ok("用户最终消费:"+cost);
    }

    public Result cost(){
        JsonNode rb = request().body().asJson();
        Long distance = rb.get("distance").asLong();
        Long waitTime = rb.get("waitTime").asLong();
        Double cost = UserCostRule.userCost(distance,waitTime);

        return ok("用户最终消费:"+cost);

    }

    public Result testCost(){
        JsonNode rb = request().body().asJson();
        Long distance = rb.get("distance").asLong();
        Long waitTime = rb.get("waitTime").asLong();
        KieSession kieSession = kie.getKieSession();
        User user = new User();
        user.setDistance(distance);
        user.setWaitTime(waitTime);
        kieSession.insert(user);
        kieSession.fireAllRules();
        Double cost = user.getCost();


        return ok("testCost:"+cost);
    }





}
