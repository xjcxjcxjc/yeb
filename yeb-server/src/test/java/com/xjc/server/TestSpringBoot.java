package com.xjc.server;
/*
 * @Author : XJC
 * @Time : 2021/12/14 15:33
 * @Description :
 *
 */
import com.xjc.util.FastDFSClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSpringBoot {
    @Autowired
    private FastDFSClient fastDFSClient;

    @Test
    public void test() {
        File file=new File("C:\\Users\\XJC\\Desktop\\1.jpg");
        try {
            System.out.println(fastDFSClient.uploadFile(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
