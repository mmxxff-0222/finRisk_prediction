package com.mengxf.riskreport2;

import com.alibaba.fastjson2.JSONObject;
import com.mengxf.riskreport2.Utils.Const;
import com.mengxf.riskreport2.Utils.FeaturesFormat;
import com.mengxf.riskreport2.Utils.Msg;
import com.mengxf.riskreport2.pojo.BorrowerPojo;
import com.mengxf.riskreport2.pojo.FinInfoPojo;
import com.mengxf.riskreport2.pojo.LoanPojo;
import com.mengxf.riskreport2.pojo.UserPojo;
import com.mengxf.riskreport2.service.ReportServiceImpl;
import com.mengxf.riskreport2.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.SpringVersion;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

@SpringBootTest
class RiskReport2ApplicationTests {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    ReportServiceImpl reportService;


    @Test
    void contextLoads() {
        List<UserPojo> users = new ArrayList<>();
        int code = userService.getAllUsers(users);
        System.out.println(code);
        System.out.println(users);

    }

    @Test
    void unitTest(){
//        userService.addUser("Tom Chen","123456","13331121958",2);

        FinInfoPojo finInfo = new FinInfoPojo();
        int flag = userService.findFinHealthByName("Tom Chen",finInfo);
        if (flag == Const.SUCCESS){
            System.out.println(finInfo);
        }
        System.out.println(Msg.findFinInfo(flag));
    }

    @Test
    void tmpTest(){
        Map<String,Object> original = reportService.loadData("Vpzizbd");
        for (Map.Entry<String, Object> entry : original.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }


    }

    @Test
    void postFlaskTest(){
//        Map<String, Object> aanal = reportService.loadData("Aanal");
//        Map<String, Object> features = reportService.getFeatures(aanal);

        Map<String,Map<String,Integer>> codeDict = FeaturesFormat.loadCodeDict();
//        // 使用 for-each 循环遍历 Map，并打印键值对
//        for (Map.Entry<String, Map<String, Integer>> entry : codeDict.entrySet()) {
//            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
//        }
        Set<String> strings = codeDict.keySet();
//        System.out.println("keys:" + strings);
        for (String featureName : strings){
            System.out.println(featureName);
        }

    }

    /**
     * debug: 无docx生成，['Qgzlisi', 'Uvtbzal', 'Riongtr', 'Aipjbsqz', 'Zosyqjs']
     */
    @Test
    void noDocxDebug(){
        Map<String,Object> original = reportService.loadData("Qgzlisi");
//        String riskGrade = reportService.getRiskGrade(original);
//        System.out.println(riskGrade);
        // 预处理map数据，筛选出25个需要的特征
        Map<String, Object> featuresMap = new LinkedHashMap<>();
        for (String key : Const.FEATURES_LIST){
            if (original.containsKey(key)){
                featuresMap.put(key,original.get(key));
            }
            else {
                System.out.println("no key");
            }
        }
        featuresMap = FeaturesFormat.featuresNormalization(featuresMap);
        System.out.println("features:");
        for (Map.Entry<String, Object> entry : featuresMap.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
        String resPonse = reportService.flaskPostRequest(featuresMap);
        JSONObject jsonResponse = JSONObject.parseObject(resPonse);
        System.out.println(jsonResponse);
    }

    @Test
    public void toDoc() {
        // 根据 Word 模板文件路径创建 File 对象
        File docxFile = new File(Const.TEMPLATE_PATH + "report.docx");
        // 根据 Word 模板文件创建 ZipFile 对象
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(docxFile);
            // 获取 Word 模板文件中的所有条目（entries）
            Enumeration<? extends ZipEntry> zipEntrys = zipFile.entries();
            // 创建用于写入生成 Word 文档的 ZipOutputStream 对象
            String toFilePath = Const.TEMPLATE_PATH + "out.docx";
            ZipOutputStream zipout = new ZipOutputStream(new FileOutputStream(toFilePath));

            int len = -1;
            byte[] buffer = new byte[1024];
            // 遍历 Word 模板文件中的所有条目
            while (zipEntrys.hasMoreElements()) {
                // 获取下一个条目
                ZipEntry next = zipEntrys.nextElement();
                // 获取当前条目的输入流
                InputStream is = zipFile.getInputStream(next);
                // 将当前条目写入生成的 Word 文档中
                zipout.putNextEntry(new ZipEntry(next.toString()));
                // 如果当前条目是 word/document.xml，将其内容替换为 documentFile 的内容
                if ("word/document.xml".equals(next.toString())) {
                    // 创建 documentFile 的输入流
                    String xmlTemp = Const.TEMPLATE_PATH + "temp.xml";
                    //1.获取文件BufferedWriter
                    File f_o = new File(xmlTemp);
                    InputStream in = new FileInputStream(f_o);
                    // 从 documentFile 中读取内容并写入生成的 Word 文档中
                    while ((len = in.read(buffer)) != -1) {
                        zipout.write(buffer, 0, len);
                    }
                    // 关闭 documentFile 的输入流
                    in.close();
                } else {
                    // 如果当前条目不是 word/document.xml，直接将其内容写入生成的 Word 文档中
                    while ((len = is.read(buffer)) != -1) {
                        zipout.write(buffer, 0, len);
                    }
                    // 关闭当前条目的输入流
                    is.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("over");
    }
}
