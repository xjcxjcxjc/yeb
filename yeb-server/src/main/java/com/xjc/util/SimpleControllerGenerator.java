package com.xjc.util;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author : XJC
 * @Time : 2022/1/5 21:17
 * @Description :
 */
public class SimpleControllerGenerator extends AbsControllerGenerator{

    public static void main(String[] args) {
        List<OpsBean> list=new ArrayList<OpsBean>();
        Collections.addAll(list ,
                new OpsBean("工资账套","ISalaryService","Salary"
                        ,"/salary/sob",true
                        ,true,true,true,false)
//                new OpsBean("职位","IPositionService","Position"
//                        ,"/pos",true
//                        ,false,false,false,false),
//                new OpsBean("职称","IJoblevelService","Joblevel"
//                        ,"/jl",true
//                        ,false,false,false,false),
//                new OpsBean("民族","INationService","Nation"
//                        ,"/na",true
//                        ,false,false,false,false),
//                new OpsBean("政治面貌","IPoliticsStatusService","PoliticsStatus"
//                        ,"/pol",true
//                        ,false,false,false,false)
                );
        SimpleControllerGenerator.direct(list);
    }

    public static void direct(List<OpsBean> opsBeanList){
        for (OpsBean opsBean:opsBeanList){
            SimpleControllerGenerator simpleControllerGenerator=new SimpleControllerGenerator();
            simpleControllerGenerator.setApiKeyValue(opsBean.getApiKey())
                    .setPojo(opsBean.getPojo())
                    .setService(opsBean.getService())
                    .setUri(opsBean.getUrl())
                    .setNeedGetAllOps(opsBean.isSelectAll())
                    .setNeedSaveOps(opsBean.isAdd())
                    .setNeedUpdateOps(opsBean.isUpdate())
                    .setNeedDeleteByIdOps(opsBean.isDelete())
                    .setNeedDeleteByIdsOps(opsBean.isDeletebouch());
            System.out.println(simpleControllerGenerator.generate());
        }
    }



    @Override
    public void generateGetAll() {
        StringBuffer stringBuffer=getStringBuffer();
        stringBuffer.append("@ApiOperation(\"获取全部的"+getApiKeyValue()+"\")\n" +
                "@GetMapping(\""+getUri()+"\")\n");
        stringBuffer.append("public List<"+getPojo()+"> getAll"+getPojo()+"(){\n");
        stringBuffer.append("    return "+getService_att()+".list();\n" +
                "}\n");
    }

    @Override
    public void generateSave() {
        StringBuffer stringBuffer=getStringBuffer();
        stringBuffer.append("@ApiOperation(\"添加"+getApiKeyValue()+"\")\n" +
                "@PostMapping(\""+getUri()+"\")\n");
        stringBuffer.append("RespBean add"+getPojo()+"("+getPojo()+" "+getPojo_att()+"){\n");
        stringBuffer.append(
                "    if ("+getService_att()+".save("+getPojo_att()+")){\n" +
                "       return RespBean.success(\"添加成功\");\n" +
                "    }else {\n" +
                "       return RespBean.error(\"添加失败\");\n" +
                "    }\n" +
                        "}\n");
    }

    @Override
    public void generateUpdate() {
        StringBuffer stringBuffer=getStringBuffer();
        stringBuffer.append("@ApiOperation(\"更新"+getApiKeyValue()+"\")\n" +
                "@PutMapping(\""+getUri()+"\")\n");
        stringBuffer.append("RespBean update"+getPojo()+"(@RequestBody "+getPojo()+" "+getPojo_att()+"){\n");
        stringBuffer.append(
                "    if ("+getService_att()+".updateById("+getPojo_att()+")){\n" +
                        "      return RespBean.success(\"更新成功\");\n" +
                        "    }else {\n" +
                        "      return RespBean.error(\"更新失败\");\n" +
                        "    }\n" +
                        "}\n");
    }

    @Override
    public void generateDeleteById() {
        String id_url="/{id}";
        if ("/".equals(getUri())){
            id_url="{id}";
        }

        StringBuffer stringBuffer=getStringBuffer();
        stringBuffer.append("@ApiOperation(\"用id删除"+getApiKeyValue()+"\")\n" +
                "@DeleteMapping(\""+getUri()+id_url+"\")\n");
        stringBuffer.append("RespBean delete"+getPojo()+"ById"+"(@PathVariable Integer id){\n");
        stringBuffer.append(
                "        if ("+getService_att()+".removeById(id)){\n" +
                        "      return RespBean.success(\"删除成功\");\n" +
                        "    }else {\n" +
                        "      return RespBean.error(\"删除失败\");\n" +
                        "    }\n" +
                        "}\n");
    }

    @Override
    public void generateDeleteByByIds() {
        StringBuffer stringBuffer=getStringBuffer();
        stringBuffer.append("@ApiOperation(\"批量删除"+getApiKeyValue()+"\")\n" +
                "@DeleteMapping(\""+getUri()+"\")\n");
        stringBuffer.append("RespBean delete"+getPojo()+"ById"+"(Integer[] ids){\n");
        stringBuffer.append(
                "        if ("+getService_att()+".removeByIds(Arrays.asList(ids))){\n" +
                        "      return RespBean.success(\"删除成功\");\n" +
                        "    }else {\n" +
                        "      return RespBean.error(\"删除失败\");\n" +
                        "    }\n" +
                        "}\n\n\n");
    }



}
