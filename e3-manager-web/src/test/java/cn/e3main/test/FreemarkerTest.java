package cn.e3main.test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerTest {

	@Test
	public void createFtl() throws Exception {
		//获取configuration对象
		Configuration configuration = new Configuration(Configuration.getVersion());
		//配置文件所在文件夹路径
		configuration.setDirectoryForTemplateLoading(new File("D:\\eclipse-win64\\workspace\\e3-manager-web\\src\\main\\webapp\\WEB-INF\\ftl"));
		//配置字符集
		configuration.setDefaultEncoding("utf-8");
		//获取一个模板
		Template template = configuration.getTemplate("hello.ftl");
		//创建一个模板使用的数据集
		Map<Object, Object> dataModel = new HashMap<>();
		//向数据集中添加数据
		dataModel.put("name", " world!");
		//创建writer对象，指定生成文件名
		Writer writer = new FileWriter(new File("E:\\helloworld.html"));
		//调用模板对象的process输出文件
		template.process(dataModel, writer);
		//关流
		writer.close();
		
	}
}
