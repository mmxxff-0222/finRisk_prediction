package com.mengxf.riskreport2.service;

import com.alibaba.fastjson2.JSONObject;
import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.mengxf.riskreport2.Utils.Const;
import com.mengxf.riskreport2.Utils.FeaturesFormat;
import com.mengxf.riskreport2.pojo.BorrowerPojo;
import com.mengxf.riskreport2.pojo.FinInfoPojo;
import com.mengxf.riskreport2.pojo.LoanPojo;
import com.mengxf.riskreport2.pojo.UserPojo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * ReportServiceImpl
 *
 * @author Meng xf
 * @since 2024/3/20 15:53
 */
@Service
public class ReportServiceImpl {

    @Autowired
    UserServiceImpl userService;

    private Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);

    /**
     * 生成报告入口方法
     *
     * @param name
     * @return
     */
    @SneakyThrows
    public Map<String, Object> doReport(String name){

        /* ------------------------------------------------------------------------ */
        /* You should do this ONLY ONCE in the whole application life-cycle:        */
        Map<String, Object> response = new HashMap<>();

        /* Create and adjust the configuration singleton */
//        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        String templatePath = Const.TEMPLATE_PATH;
        cfg.setDirectoryForTemplateLoading(new File(templatePath));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        /* ------------------------------------------------------------------------ */
        /* You usually do these for MULTIPLE TIMES in the application life-cycle:   */

        /* Create a data-model */
        Map<String,Object> mapData =loadData(name);
        // TODO  loadFlag 检验异常
        int loadFlag = Integer.parseInt(mapData.get("loadFlag").toString());
        response.put("reportCode", loadFlag);

        /* ------------------------------------------------------------------------ */
        /* templateName                                                             */
        String templateName = "document.ftl";
        String docxTemplate = templatePath + "report.docx";
        /* ------------------------------------------------------------------------ */
        /* 输出报告                                                                  */
        String url = "";
        try {
            // 生成报告存放路径及文件名
            String outPath = Const.OUTPUT_PATH + name + "/" ;
            File pathFile = new File(outPath);
            if(!pathFile.exists()){
                pathFile.mkdirs();
            }
            String toFilePath = outPath + name + "'s risk report.docx";
            //填充完数据的临时xml文件路径
            String xmlTemp =outPath + "temp.xml";

            //1.获取文件BufferedWriter
            File f_o = new File(xmlTemp);
            BufferedWriter bw = new BufferedWriter (new OutputStreamWriter(new FileOutputStream(f_o),"UTF-8"));

            //2.把map中的数据动态由freemarker传给xml
            process(templateName, mapData, bw);


            //3.把填充完成的xml写入到docx中
            outDocx2(f_o, docxTemplate, toFilePath);

            // 4.将doc转化为pdf
//            asposeWord2Pdf(toFilePath,toFilePath.replace(".docx",".pdf"));

            url = toFilePath;
        } catch (Exception exception) {
            exception.printStackTrace();
            url = exception.getStackTrace().toString();

        }finally {
            response.put("data", url);
        }
        return response;

    }

    /**
     * 加载填充模版内的数据,调用时需要通过 map.get("loadFlag") 检验异常
     * @param name
     * @return
     */
    public Map<String,Object> loadData(String name){
        int flag = 1;
        Map<String,Object> map = new HashMap<>();
        // user 信息 2
        UserPojo userInfo = userService.findUserByName(name);
        if (userInfo == null){
            map.put("loadFlag", Const.USER_LOSS);
            return map;
        }
        map.put("name", userInfo.getUserName());
        map.put("phone", userInfo.getPhone());

        // borrower 信息 5
        BorrowerPojo borrowerInfo = new BorrowerPojo();
        flag = userService.findBorrowerByName(name, borrowerInfo);
        if (flag != Const.SUCCESS){
            map.put("loadFlag", Const.BORROWER_LOSS);
            return map;
        }
        map.put("emp_length", borrowerInfo.getEmp_length());
        map.put("addr_state", borrowerInfo.getAddr_state());
        map.put("home_ownership", borrowerInfo.getHome_ownership());
        map.put("annual_inc", borrowerInfo.getAnnual_inc());
        map.put("verification_status", borrowerInfo.getVerification_status());

        // loan 信息 7
        List<LoanPojo> loans = new ArrayList<>();
        flag = userService.findLoanByName(name,loans);
        if (flag != Const.SUCCESS){
            map.put("loadFlag", Const.LOAN_LOSS);
            return map;
        }
        LoanPojo loanInfo = loans.get(0); // 如果返回多个贷款记录，默认取第一条
        map.put("purpose", loanInfo.getPurpose());
        map.put("application_type", loanInfo.getApplication_type());
        map.put("loan_amnt",loanInfo.getLoan_amnt());
        map.put("term",loanInfo.getTerm());
        map.put("int_rate",loanInfo.getInt_rate());
        map.put("installment",loanInfo.getInstallment());
        map.put("grade",loanInfo.getGrade());

        // financial_health 信息 13
        FinInfoPojo finInfo = new FinInfoPojo();
        flag = userService.findFinHealthByName(name, finInfo);
        if (flag != Const.SUCCESS ){
            map.put("loadFlag", Const.FININFO_LOSS);
            return map;
        }
        map.put("dti",finInfo.getDti());
        map.put("delinq_2yrs",finInfo.getDelinq_2yrs());
        map.put("open_acc",finInfo.getOpen_acc());
        map.put("pub_rec",finInfo.getPub_rec());
        map.put("acc_now_delinq",finInfo.getAcc_now_delinq());
        map.put("tot_coll_amt",finInfo.getTot_coll_amt());
        map.put("tot_cur_bal",finInfo.getTot_cur_bal());
        map.put("revol_bal",finInfo.getRevol_bal());
        map.put("revol_util",finInfo.getRevol_util());
        map.put("total_acc",finInfo.getTotal_acc());
        map.put("total_pymnt",finInfo.getTotal_pymnt());
        map.put("total_rec_late_fee",finInfo.getTotal_rec_late_fee());
        map.put("last_pymnt_amnt",finInfo.getLast_pymnt_amnt());

        //risk_grade
        String risk_grade = getRiskGrade(map); // 由机器学习模型预测所得
        map.put("risk_grade", risk_grade);

        map.put("loadFlag", Const.SUCCESS);
        return map;
    }

    /**
     * 根据map中的特征预测风险等级
     * @param map
     * @return
     */
    public String getRiskGrade(Map<String, Object> map) {
        // 预处理map数据，筛选出25个需要的特征
        Map<String, Object> featuresMap = new LinkedHashMap<>();
        for (String key : Const.FEATURES_LIST){
            if (map.containsKey(key)){
                featuresMap.put(key,map.get(key));
            }
            else {
                return "NONE";
            }
        }
        //对25个特征进行标准化处理
        featuresMap = FeaturesFormat.featuresNormalization(featuresMap);
        // 访问Flask服务端，预测结果 TODO 以下JSON判断写到Utils类中
        String resPonse = flaskPostRequest(featuresMap); // TODO ERROR 待处理 3.26 12:02
        JSONObject jsonResponse = JSONObject.parseObject(resPonse);
        if (jsonResponse.get("code").toString().equals("200")){
            JSONObject jsonData = JSONObject.parseObject(jsonResponse.get("data").toString());
            int index = Integer.parseInt(jsonData.get("predict").toString());
            return Const.RISK_LIST.get(index);
        }
        return "NONE";
    }

    /**
     * 服务端向机器学习模型的服务端发送请求，接收预测结果
     * @param featuresMap
     * @return
     */
    public String flaskPostRequest(Map<String, Object> featuresMap) {
        RestTemplate restTemplate = new RestTemplate();
        String url = Const.FLASK_SERVER + "/predict";
        // request
        Map<String, Object> data = new HashMap<>();
        data.put("features",featuresMap);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(data);

        String response = restTemplate.postForObject(url, request, String.class);
        return response;
    }


    /**
     * 用param中的数据通过freemarker替换ftl中的占位符
     * @param templatefile 模板文件
     * @param param        需要填充的内容
     * @param out		   填充完成输出的文件
     * @throws IOException
     * @throws TemplateException
     */
    private void process(String templatefile, Map param, Writer out) throws IOException, TemplateException{
        //获取模板
        Template template = cfg.getTemplate(templatefile);
        template.setOutputEncoding("UTF-8");
        //合并数据
        template.process(param, out);
        if(out!=null){
            out.close();
        }
    }

    /**
     * 把填充完成的xml写入到docx中
     * @param documentFile
     * @param docxTemplate
     * @param toFilePath
     * @param images
     * @throws Exception
     */
    public void outDocx(File documentFile, String docxTemplate, String toFilePath, Map<String,MultipartFile> images) throws Exception {

        File docxFile = new File(docxTemplate);
        ZipFile zipFile = new ZipFile(docxFile);
        Enumeration<? extends ZipEntry> zipEntrys = zipFile.entries();
        ZipOutputStream zipout = new ZipOutputStream(new FileOutputStream(toFilePath));

        int len = -1;
        byte[] buffer = new byte[1024];
        while (zipEntrys.hasMoreElements()) {
            ZipEntry next = zipEntrys.nextElement();
            InputStream is = zipFile.getInputStream(next);
            //把输入流的文件传到输出流中 如果是word/document.xml由我们输入
            zipout.putNextEntry(new ZipEntry(next.toString()));
            if ("word/document.xml".equals(next.toString())) {
                InputStream in = new FileInputStream(documentFile);
                while ((len = in.read(buffer)) != -1) {
                    zipout.write(buffer, 0, len);
                }
                in.close();
            } else if (images != null && next.toString().startsWith("word/media/") &&
                    images.containsKey(next.toString().substring(next.toString().lastIndexOf("/") + 1))) {
                Set<String> imageNames = images.keySet();
                for (String imageName : imageNames) {
                    if (("word/media/" + imageName).equals(next.toString())){
                        MultipartFile image = images.get(imageName);
                        InputStream in = image.getInputStream();
                        while ((len = in.read(buffer)) != -1) {
                            zipout.write(buffer, 0, len);
                        }
                        in.close();
                    }
                }
            } else {
                while ((len = is.read(buffer)) != -1) {
                    zipout.write(buffer, 0, len);
                }
                is.close();
            }
        }
        zipout.close();
        System.out.println("word生成成功");
    }

    public void outDocx2(File documentFile, String docxTemplate, String toFilePath) throws Exception {
        // 根据 Word 模板文件路径创建 File 对象
        File docxFile = new File(docxTemplate);
        // 根据 Word 模板文件创建 ZipFile 对象
        ZipFile zipFile = new ZipFile(docxFile);
        // 获取 Word 模板文件中的所有条目（entries）
        Enumeration<? extends ZipEntry> zipEntrys = zipFile.entries();
        // 创建用于写入生成 Word 文档的 ZipOutputStream 对象
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
                InputStream in = new FileInputStream(documentFile);
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
        // 关闭生成的 Word 文档的输出流
        zipout.close();
        System.out.println("word生成成功");
    }


    /**
     * 根据word文件输出对应的pdf
     * @param inPath
     * @param outPath
     * @throws Exception
     */
    private void asposeWord2Pdf(String inPath, String outPath) throws Exception {
        if (!getLicense() || StringUtils.isEmpty(inPath) || StringUtils.isEmpty(outPath)) {
            // 验证License 若不验证则转化出的pdf文档会有水印产生
            return;
        }

        // 新建一个空白pdf文档
        File file = new File(outPath);
        OutputStream os = new FileOutputStream(file);
        Document doc = new Document(inPath); // Address是将要被转化的word文档
        doc.save(os, SaveFormat.PDF);// 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF,
        // EPUB, XPS, SWF 相互转换

        os.close();
        System.out.println("word转换pdf成功");
    }

    /**
     * 去水印
     * @return
     */
    private boolean getLicense() {
        boolean result = false;
        InputStream is = null;
        try {
            Resource resource = new ClassPathResource("license.xml");
            is = resource.getInputStream();
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}
