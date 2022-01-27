package com.xjc.util;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author : XJC
 * @Time : 2022/1/5 20:48
 * @Description : 使用该controller构造器，自动创建Controller代码
 */
@Data
@NoArgsConstructor
public abstract class AbsControllerGenerator {

//    Api，uri，service，操作的对象，需要生成哪些方法
    private String apiKeyValue;
    private String uri;
    private String service;
    private String pojo;

    private String service_att;
    private String pojo_att;

    private boolean needGetAllOps=true;
    private boolean needSaveOps=true;
    private boolean needUpdateOps=true;
    private boolean needDeleteByIdOps=true;
    private boolean needDeleteByIdsOps=true;

    //公用stringbuffer
    private StringBuffer stringBuffer=new StringBuffer();


    public AbsControllerGenerator(String apiKeyValue, String uri, String service, String pojo) {
        this.apiKeyValue = apiKeyValue;
        this.uri = uri;
        this.service = service;
        this.pojo = pojo;
    }

    public AbsControllerGenerator setNeedGetAllOps(boolean needGetAllOps) {
        this.needGetAllOps = needGetAllOps;
        return this;
    }

    public AbsControllerGenerator setNeedSaveOps(boolean needSaveOps) {
        this.needSaveOps = needSaveOps;
        return this;
    }

    public AbsControllerGenerator setNeedUpdateOps(boolean needUpdateOps) {
        this.needUpdateOps = needUpdateOps;
        return this;
    }

    public AbsControllerGenerator setNeedDeleteByIdOps(boolean needDeleteByIdOps) {
        this.needDeleteByIdOps = needDeleteByIdOps;
        return this;
    }

    public AbsControllerGenerator setNeedDeleteByIdsOps(boolean needDeleteByIdsOps) {
        this.needDeleteByIdsOps = needDeleteByIdsOps;
        return this;
    }

    public AbsControllerGenerator setApiKeyValue(String apiKeyValue) {
        this.apiKeyValue = apiKeyValue;
        return this;
    }

    public AbsControllerGenerator setUri(String uri) {
        this.uri = uri;
        return this;
    }

    public AbsControllerGenerator setService(String service) {
        this.service = service;
        return this;
    }

    public AbsControllerGenerator setPojo(String pojo) {
        this.pojo = pojo;
        return this;
    }

    public void init(){
        StringBuffer stringBuffer=getStringBuffer();
        char sfirst=Character.toLowerCase(service.charAt(0));
        service_att=sfirst+service.substring(1);
        char pofirst=Character.toLowerCase(pojo.charAt(0));
        pojo_att=pofirst+pojo.substring(1);

        stringBuffer.append("@Autowired\n");
        stringBuffer.append(getService()+" "+getService_att()+";\n");
    }

    public abstract void generateGetAll();
    public abstract void generateSave();
    public abstract void generateUpdate();
    public abstract void generateDeleteById();
    public abstract void generateDeleteByByIds();

    public String generate(){
        init();
        if (needGetAllOps){
             generateGetAll();
        }
        if (needSaveOps){
            generateSave();
        }
        if (needUpdateOps){
            generateUpdate();
        }
        if (needDeleteByIdOps){
            generateDeleteById();
        }
        if (needDeleteByIdsOps){
            generateDeleteByByIds();
        }
        return stringBuffer.toString();
    }

}
