package cn.e3mall.item.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 生成静态文件
* Title: HtmlGenController <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年11月28日
 */
@Controller
public class HtmlGenController {
	@Autowired
	private FreeMarkerConfig freemarkerConfig;

	/**
	 * 生成静态文件测试
	 * <p>Title: genHtml</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年11月28日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/item/genHtml.html")
	public String genHtml() throws Exception {
		Configuration configuration = freemarkerConfig.getConfiguration();
		//获取模板
		Template template = configuration.getTemplate("hello.ftl");
		//创建数据集
		Map<Object, Object> dataModel = new HashMap<>();
		dataModel.put("key", " world!");
		//创建输出流
		Writer writer = new FileWriter(new File("E:\\spring-freemarker.html"));
		//生成文件
		template.process(dataModel, writer);
		//关闭输出流
		writer.close();
		return "spring-freemarker.html";
	}
}
