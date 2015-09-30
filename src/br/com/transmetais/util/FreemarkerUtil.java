package br.com.transmetais.util;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerUtil {
	private static Configuration cfg = new Configuration();

	// private static final String TEMPLATES_FOLDER = System
	// .getProperty("user.dir") + AutosBr.TEMPLATES_FOLDER;

	private static final String TEMPLATES_FOLDER = TransmetaisWeb.TEMPLATES_FOLDER;

	public static final String parseTemplate(Map map, String templateName)
			throws TemplateException, IOException {

		cfg.setDirectoryForTemplateLoading(new File(TEMPLATES_FOLDER));

		cfg.setObjectWrapper(new DefaultObjectWrapper());

		// recupera o template
		Template t = cfg.getTemplate(templateName);

		StringWriter writer = new StringWriter();

		/** Freemarker **/
		t.process(map, writer);

		writer.flush();
		writer.close();

		return writer.toString();
	}


}
