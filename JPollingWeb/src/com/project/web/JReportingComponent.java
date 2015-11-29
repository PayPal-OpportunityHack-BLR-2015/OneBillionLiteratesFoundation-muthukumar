/**
* Project development
**/
package com.project.web;

import java.util.List;

import com.project.jpolling.dao.answer.AnswersDAO;
import com.project.jpolling.dao.mapping.MappingDAO;
import com.project.jpolling.to.answer.AnswerTO;
import com.project.jpolling.to.user.UserTO;

/**
 * @author P.Ayyasamy
 * @since 1.0
 */
public class JReportingComponent {

	
	public String generateReport(long questionId, int start, UserTO  userTO){
		StringBuffer content =new StringBuffer();
		
		try{
		content.append("<table width='100%' class='body_content'>");
		content.append("<tbody><tr>");
		content.append("<td><div class='headings'>");
		content.append("<table width='100%'>");
		content.append("<tbody><tr>");
		content.append("<td width='6%' align='right'><img width='32' height='32' alt='X' src='/JPollingWeb/StaticContent/Images/report.png'/></td>");
		content.append("<td width='63%'><h4 align='left' class='quick_links'> Reports</h4></td>");
		content.append("<td width='31%'><table align='right'>");
		content.append("<tbody><tr>");
		content.append("<td class='quick_links'>&nbsp;</td>");
		content.append("</tr>");
		content.append("</tbody></table></td>");
		content.append("</tr>");
		content.append("</tbody></table>");
		content.append("</div>");
		content.append("<div class='headings' align='center'> Group Report</div>");
		content.append("<div id='chartdiv' style='width:600px; height:400px; background-color:#FFFFFF'></div>");
		StringBuffer chartData = new StringBuffer();
		if(questionId>0){ // Pie chart
			MappingDAO mapping = new MappingDAO();
			AnswersDAO answer = new AnswersDAO();
			List<AnswerTO> answerList = answer.getAnswerList(questionId);
			//chartData.append("\"");
			for(AnswerTO answerTO: answerList){
				double percentage = mapping.getPercentageForAnswerId(answerTO);
				chartData.append(answerTO.getId()).append(";").append(percentage).append("\\n");
			}
			//chartData.append("\"");
			content.append(generatePieChart(chartData.toString()));
		} else{
			content.append("<div id='questions_div'>");
			content.append(new JPollingHelper().generateQuestionList(userTO,start,true));
			content.append("</div>");	
			content.append(generatecolumnChart(chartData.toString()));
		}
		content.append("</td>");
		content.append("</tr>");
		content.append("</tbody></table>");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return content.toString();
	}
	
	
	/**
	 * @return
	 *
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	private String generatecolumnChart(String chartData) {
		StringBuffer content = new StringBuffer();
		try{
			//content.append("<input type='hidden' id='chart_data' value='").append("A1;30\\nB1;40\\nC1;20\\nD1;10").append("' />");
			
			//content.append("<input type='hidden' id='chart_settings' value='");
			//content.append("<settings><data_type>csv</data_type><legend><enabled>0</enabled></legend><pie><inner_radius>30</inner_radius><height>7</height><angle>10</angle><gradient></gradient></pie><animation><start_time>1</start_time><pull_out_time>1</pull_out_time></animation><data_labels><show>{title}</show><max_width>100</max_width></data_labels></settings>");
			//content.append("' />");
			
			content.append("<script type='text/javascript'>\n");
			//content.append("alert('got Hit on reports'); \n");
			content.append("var params =");
			content.append(" {" );
			content.append(" bgcolor:'#FFFFFF'");
			content.append(" };\n" );
			content.append("var flashVars = ");
			content.append("{\n");
			content.append("path:\"/JPollingWeb/StaticContent/JS/amcharts/flash/\",\n");
			content.append("chart_data:\"21 Jan;91;96;69\\n22 Jan;87;112;101\\n23 Jan;68;79;66\\n24 Jan;30;32;23\\n25 Jan;52;57;41\",\n");
			content.append("chart_settings: \"<settings><data_type>csv</data_type><plot_area><margins><left>50</left><right>40</right><top>50</top><bottom>50</bottom></margins></plot_area><grid><category><dashed>1</dashed><dash_length>4</dash_length></category><value><dashed>1</dashed><dash_length>4</dash_length></value></grid><axes><category><width>1</width><color>E7E7E7</color></category><value><width>1</width><color>E7E7E7</color></value></axes><values><value><min>0</min></value></values><legend><enabled>0</enabled></legend><angle>0</angle><column><width>85</width><balloon_text>{title}: {value} downloads</balloon_text><grow_time>3</grow_time><sequenced_grow>1</sequenced_grow></column><graphs><graph gid='0'><title>Stock</title><color>ADD981</color></graph><graph gid='1'><title>Column</title><color>7F8DA9</color></graph><graph gid='2'><title>Line</title><color>FEC514</color></graph></graphs><labels><label lid='0'><text><![CDATA[<b>Daily downloads</b>]]></text><y>18</y><text_color>000000</text_color><text_size>13</text_size><align>center</align></label></labels></settings>\"\n");
			content.append("};\n");
			content.append("window.onload = function()");
			content.append("{\n");
			content.append("if (AmCharts.recommended() == 'js')");
			content.append("{\n");
			content.append("var amFallback = new AmCharts.AmFallback();\n");
			content.append("amFallback.chartSettings = flashVars.chart_settings;\n");
			content.append("amFallback.pathToImages = '/JPollingWeb/StaticContent/JS/amcharts/images/';\n");
			content.append("amFallback.chartData = flashVars.chart_data;\n");
			content.append("amFallback.type='column';\n");
			content.append("amFallback.write('chartdiv');\n");
			content.append("}\n");
			content.append("else");
			content.append("{\n");
			content.append("swfobject.embedSWF('/JPollingWeb/StaticContent/JS/amcharts/flash/ampie.swf', 'chartdiv', '600', '400', '8.0.0', '/JPollingWeb/StaticContent/JS/amcharts/flash/expressInstall.swf', flashVars, params);");
			content.append("}");
			content.append("}\n");
			content.append("</script>");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return content.toString();
	}

	/**
	 * 
	 * @return
	 *
	 * @Author P.Ayyasamy
	 * @since 1.0
	 */
	public String generatePieChart(String chartData){
		StringBuffer xml = new StringBuffer();
		xml.append("<input type='hidden' id='chart_data' value='").append("A1;30\\nB1;40\\nC1;20\\nD1;10").append("' />");
		
		xml.append("<input type='hidden' id='chart_settings' value='");
		xml.append("<settings><data_type>csv</data_type><legend><enabled>0</enabled></legend><pie><inner_radius>30</inner_radius><height>7</height><angle>10</angle><gradient></gradient></pie><animation><start_time>1</start_time><pull_out_time>1</pull_out_time></animation><data_labels><show>{title}</show><max_width>100</max_width></data_labels></settings>");
		xml.append("' />");
		
		xml.append("<script type='text/javascript'>");
		xml.append("alert('got Hit on reports'); \n");
		xml.append("var params =");
		xml.append(" {" );
		xml.append(" bgcolor:'#FFFFFF'");
		xml.append(" };\n" );
		xml.append("var flashVars = ");
		xml.append("{");
		xml.append("path:\"/JPollingWeb/StaticContent/JS/amcharts/flash/\",");
		xml.append("chart_data:\"").append(chartData).append("\",");
		//xml.append("chart_data:\"A1;30\\nB1;40\\nC1;20\\nD1;10\",");
		xml.append("chart_settings:\"<settings><data_type>csv</data_type><legend><enabled>0</enabled></legend><pie><inner_radius>30</inner_radius><height>7</height><angle>10</angle><gradient></gradient></pie><animation><start_time>1</start_time><pull_out_time>1</pull_out_time></animation><data_labels><show>{title}</show><max_width>100</max_width></data_labels></settings>\"");
		
		xml.append(" };\n");
		xml.append("window.onload = function()");
		xml.append("{\n");
		xml.append("if (AmCharts.recommended() == 'js')");
		xml.append("{\n");
		xml.append("var amFallback = new AmCharts.AmFallback();\n");
		xml.append("amFallback.chartSettings = flashVars.chart_settings;\n");
		xml.append("amFallback.chartData = flashVars.chart_data;\n");
		xml.append("amFallback.type='pie';\n");
		xml.append("amFallback.write('chartdiv');\n");
		xml.append("}\n");
		xml.append("else");
		xml.append("{\n");
		xml.append("swfobject.embedSWF('/JPollingWeb/StaticContent/JS/amcharts/flash/ampie.swf', 'chartdiv', '600', '400', '8.0.0', '/JPollingWeb/StaticContent/JS/amcharts/flash/expressInstall.swf', flashVars, params);");
		xml.append("}");
		xml.append("}");
		xml.append("</script>");
		return xml.toString();
		
	}
	
	
}
