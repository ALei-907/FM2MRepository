package com.example.operationalmonitor.controller;

import com.example.operationalmonitor.service.HelloServiceImpl;
import io.prometheus.client.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

/**
 * @Author LeiLiMin
 * @Description:
 * @date: 2022/5/7
 */
@RestController
@RequestMapping("/v1")
public class HelloController {
    @Autowired
    private HelloServiceImpl helloService;

    /**
     * Grafana: https://grafana.com/docs/grafana/latest/installation/mac/
     * 安装配置grafana
     *
     * @param clientName
     * @return
     */
    @GetMapping("/Hello")
    public String hello(@RequestParam String clientName) {
        helloService.counterIncr();
        return "Hello " + clientName;
    }

    @GetMapping("/gaugeIncr")
    public String gaugeIncr() {
        helloService.gaugeIncr();
        return "gaugeIncr";
    }

    @GetMapping("/gaugeDecr")
    public String gaugeDecr() {
        helloService.gaugeDecr();
        return "gaugeDecr";
    }

    @GetMapping("/exception")
    public String exception() {
        int i = 1 / 0;
        return "exception";
    }

    @GetMapping("/hello")
    public String hello() {
        return "\n hello";
    }

    // 登录
    // public static void login(String idTokenString) throws Exception {
    //     GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance())
    //             // 指定访问后端应用的CLIENT_ID:
    //             .setAudience(Collections.singletonList(CLIENT_ID))
    //             // 如果存在多个后端应用
    //             //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
    //             .build();
    //
    //     // (Receive idTokenString by HTTPS POST)
    //     GoogleIdToken idToken = verifier.verify(idTokenString);
    //     if (idToken != null) {
    //         GoogleIdToken.Payload payload = idToken.getPayload();
    //
    //         String userId = payload.getSubject();
    //         String email = payload.getEmail();
    //         boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
    //         String name = (String) payload.get("name");
    //         String pictureUrl = (String) payload.get("picture");
    //         String locale = (String) payload.get("locale");
    //         String familyName = (String) payload.get("family_name");
    //         String givenName = (String) payload.get("given_name");
    //
    //         // Use or store profile information
    //         // ...
    //     } else {
    //         System.out.println("Invalid ID token.");
    //     }
    // }

    // 支付
    // public void verifyReceipt(){
    //     GoogleCredential credentials = GoogleCredential.fromStream(new ClassPathResource("私钥文件.json").getInputStream())
    //             .createScoped(Sets.newHashSet(AndroidPublisherScopes.ANDROIDPUBLISHER));
    //
    //     AndroidPublisher androidPublisher = new AndroidPublisher.Builder(
    //             GoogleNetHttpTransport.newTrustedTransport(),
    //             GsonFactory.getDefaultInstance(),
    //             new HttpCredentialsAdapter(credentials)).build();
    //     //一次性商品
    //     ProductPurchase productPurchase = androidPublisher.purchases().products().get(packageName, productId, purchaseToken).execute();
    //     //订阅
    //     //SubscriptionPurchase subscriptionPurchase = androidPublisher.purchases().subscriptions().get(packageName, productId, purchaseToken).execute();
    //     System.out.println(JsonUtil.toJson(productPurchase));
    //     return productPurchase;
    // }


    // /**
    //  * 参数需要：
    //  *  类型: String
    //  *  名称: GoogleSercet
    //  *  注意: 要当包含clientId,clientSercet
    //  */
    // public void pay2() throws IOException, GeneralSecurityException {
    //     // 获取服Google务帐户Json文件
    //     ResourceLoader resourceLoader = new DefaultResourceLoader();
    //     Resource resource = resourceLoader.getResource("classpath:static/刚下载的json文件，这里放到了static目录下");
    //     // 构建访问参数
    //     GoogleCredential credential = GoogleCredential.fromStream(resource.getInputStream()).createScoped(List.of(AndroidPublisherScopes.ANDROIDPUBLISHER));
    //     // 发起数据请求
    //     GsonFactory jsonFactory = GsonFactory.getDefaultInstance();
    //     AndroidPublisher publisher = new AndroidPublisher.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, credential).setApplicationName("应用程序名").build();
    //     // 获取所需要的数据
    //     AndroidPublisher.Purchases purchases = publisher.purchases();
    //     final AndroidPublisher.Purchases.Products.Get request = purchases.products().get("googlePayDto.getPackageName()", "googlePayDto.getProductId()", "googlePayDto.getPurchaseToken()");
    //     final ProductPurchase purchase = request.execute();
    //     // 处理业务
    // }


}
