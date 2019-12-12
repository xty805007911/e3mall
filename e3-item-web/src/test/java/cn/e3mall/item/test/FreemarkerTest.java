package cn.e3mall.item.test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerTest {

	@Test
	public void test1() throws Exception{
		//获取configuration对象
	      Configuration configuration = new Configuration(Configuration.getVersion());
	      //配置文件所在文件夹路径
	      configuration.setDirectoryForTemplateLoading(new File("D:\\eclipse-win64\\workspace\\e3-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
	      //配置字符集
	      configuration.setDefaultEncoding("utf-8");
	      //获取一个模板
	      Template template = configuration.getTemplate("freemarker_test.ftl");
	      //创建一个模板使用的数据集
	      Map<Object, Object> dataModel = new HashMap<>();
	      //向数据集中添加数据
	      //2.pojo
	      Student student = new Student(10, "xty", "bjfu");
	      dataModel.put("stu", student);
	      //1.map
	      dataModel.put("name", " world!");
	      //3.list
	      Student s1 = new Student(1, "a1", "addr1");
	      Student s2 = new Student(2, "a2", "addr2");
	      Student s3 = new Student(3, "a3", "addr3");
	      Student s4 = new Student(4, "a4", "addr4");
	      List<Student> list = new ArrayList<>();
	      list.add(s1);
	      list.add(s2);
	      list.add(s3);
	      list.add(s4);
	      dataModel.put("studentList", list);
	      //4.日期
	      dataModel.put("date", new Date());
	      //5.null值的处理
	      String val = "11";
	      dataModel.put("val", val);
	      //6.引入
	      dataModel.put("key", " word!");
	      
	      
	      
	      //创建writer对象，指定生成文件名
	      Writer writer = new FileWriter(new File("E:\\creator.html"));
	      //调用模板对象的process输出文件
	      template.process(dataModel, writer);
	      //关流
	      writer.close();
	}
	
	@Test
	public void test2() throws Exception{
		//获取configuration对象
	      Configuration configuration = new Configuration(Configuration.getVersion());
	      //配置文件所在文件夹路径
	      configuration.setDirectoryForTemplateLoading(new File("D:\\eclipse-win64\\workspace\\e3-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
	      //配置字符集
	      configuration.setDefaultEncoding("utf-8");
	      //获取一个模板
	      Template template = configuration.getTemplate("hello.ftl");
	      //创建一个模板使用的数据集
	      Map<Object, Object> dataModel = new HashMap<>();
	      //向数据集中添加数据
	      
	      //1.map
	      dataModel.put("key", " world!");
	      
	      //创建writer对象，指定生成文件名
	      Writer writer = new FileWriter(new File("E:\\hello.html"));
	      //调用模板对象的process输出文件
	      template.process(dataModel, writer);
	      //关流
	      writer.close();
	}
}
