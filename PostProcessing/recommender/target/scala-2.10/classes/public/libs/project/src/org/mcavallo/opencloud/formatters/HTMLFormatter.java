package org.mcavallo.opencloud.formatters;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import org.mcavallo.opencloud.Cloud;
import org.mcavallo.opencloud.Tag;

/**
 * Class to convert a tag cloud in HTML/CSS code. 
 */
public class HTMLFormatter {

    /** CSS code fragments */
    private Map<String, List<CssStatement>> cssFragments = new TreeMap<String, List<CssStatement>>();
    
    /** Top HTML template */
    private String htmlTemplateTop = "<div>\n";
    
    /** HTML template repeated for each tag */
    private String htmlTemplateTag = "<a href=\"%tag-link%\" style=\"font-size: %tag-weight%px\">%tag-name%</a>\n";
    
    /** Bottom HTML template */
    private String htmlTemplateBottom = "</div>\n";

    /** Specifiers used to insert variable content in templates */
    private static final String templateVarTagName = "%tag-name%";
    private static final String templateVarTagLink = "%tag-link%";
    private static final String templateVarTagWeight = "%tag-weight%";
    private static final String templateVarTagWeightInt = "%tag-weight-int%";
    private static final String templateVarTagScore = "%tag-score%";
    private static final String templateVarTagNormScore = "%tag-norm-score%";
    private static final String templateVarTagScoreInt = "%tag-score-int%";
    private static final String templateVarTagNormScoreInt = "%tag-norm-score-int%";
    
    /**
     * Default constructor.
     */
    public HTMLFormatter() {
    }
    
    /**
     * Copy constructor
     * @param other HTMLFormatter object to copy
     */
    public HTMLFormatter(HTMLFormatter other) {
		this.setCssFragments(new TreeMap<String, List<CssStatement>>(other.getCssFragments()));
		this.setHtmlTemplateTop(other.getHtmlTemplateTop());
		this.setHtmlTemplateTag(other.getHtmlTemplateTag());
		this.setHtmlTemplateBottom(other.getHtmlTemplateBottom());
    }

    /**
     * Adds a generic CSS statements.
     * @param fragment The CSS fragment to add.
     */
    public void addCss(String fragment) {
    	addCss("", fragment);
    }

    /**
     * Adds CSS statements associated with a selector.
     * The selector can contain the <code>%tag-weight-int%</code> specifier. In this case the statements
     * will be repeated for each weight level and the <code>%tag-weight-int%</code> specifier will
     * be substituted by the level number.
     * @param selector Selector (e.g. "body", "#id", "a.class:hover") 
     * @param statements Statements to add (e.g. "font-size: 14px;")
     */
    public void addCss(String selector, String statements) {
    	addCss(selector, new CssStatement(statements));
    }

    /**
     * Adds a CSS statement associated with a CSS selector.
     * The selector can contain the <code>%tag-weight-int%</code> specifier. In this case the statements
     * will be repeated for each weight level and the <code>%tag-weight-int%</code> specifier will
     * be substituted by the level number.
     * @param selector Selector (e.g. "body", "#id", "a.class:hover")
     * @param property Property (e.g. "font-size")
     * @param value Value (e.g. "14px")
     */
    public void addCss(String selector, String property, String value) {
    	if (selector == null || selector.length() == 0|| property == null || value == null)
    		return;
    	
    	addCss(selector, property + ": " + value + ";");
    }

    /**
     * Adds a CSS statement associated with a CSS selector.
     * @param selector Selector 
     * @param cssStatement CssStatement object
     */
    protected void addCss(String selector, CssStatement cssStatement) {
    	if (selector == null || ! isValid(cssStatement) )
    		return;
    	
    	List<CssStatement> currentStatements = getCssFragments().get(selector);
    	
    	if (currentStatements == null) {
    		currentStatements = new LinkedList<CssStatement>();
    		currentStatements.add(cssStatement);
    		getCssFragments().put(selector, currentStatements);
    	} else {
    		currentStatements.add(cssStatement);
    	}
    }

    /**
     * Adds a CSS statement associated with a CSS selector. The statement will vary with the
     * weight level. If the selector does not contain a <code>%tag-weight-int%</code> specifier, the level
     * number will be appended to the selector string.
     * @param selector Selector (e.g. "body", "#id", "a.class:hover")
     * @param property Property (e.g. "font-size")
     * @param lowValue Value associated with the lowest level (e.g. "10px")
     * @param highValue Value associated with the highest level (e.g. "30px")
     */
    public void addCss(String selector, String property, String lowValue, String highValue) {
    	if (selector == null || property == null || lowValue == null || highValue == null)
    		return;
    	
    	addCss(selector, new CssStatement(property, lowValue, highValue));
    }
    
    /**
     * Adds a CSS statement associated with a CSS selector. The statement will vary with the
     * weight level. If the selector does not contain a <code>%tag-weight-int%</code> specifier, the level
     * number will be appended to the selector string.
     * @param selector Selector (e.g. "body", "#id", "a.class:hover")
     * @param property Property (e.g. "font-size")
     * @param values Array of values associated with levels. The last value correspond to the
     * 				 highest level. If the array size equals the number of levels, the
     * 				 first value correspond to the lowest level. If the array size is less
     * 				 than the number of levels, the first value is used for the remaining levels.    
     */
    public void addCss(String selector, String property, String[] values) {
    	if (selector == null || property == null || values == null)
    		return;
    	
    	addCss(selector, new CssStatement(property, values));
    }

    /**
     * Check whether a CssStatement object is valid.
     * @param cssStatement CssStatement object 
     * @return True if the CssStatement is valid.
     */
    protected boolean isValid(CssStatement cssStatement) {
		return (cssStatement != null && cssStatement.getProperty() != null);
	}

	/**
	 * Converts a tag cloud in HTML/CSS code. Tags are sorted using the given comparator.
	 * The generated CSS code is inserted inline using the <code>&lt;style&gt;</code> tag.
	 * @param cloud The Cloud to convert.
	 * @param comparator Comparator used to sort tgs.
	 * @return The output HTML and CSS code.
	 */
	public String html(Cloud cloud, Comparator<? super Tag> comparator) {
		List<Tag> outputTags;
		Tag tag;
		String html = "";
		String tagHtml;

		if (comparator == null) {
			outputTags = cloud.tags();
		} else {
			outputTags = cloud.tags(comparator);
		}
		
		String css = css(cloud);
		if (css != null && css.length() == 0) {
			html += "<style>\n" + css + "</style>\n";
		}
		
		html += getHtmlTemplateTop();
		
		Iterator<Tag> it = outputTags.iterator();
		while (it.hasNext()) {
			tag = it.next();
			tagHtml = new String(getHtmlTemplateTag());
			tagHtml = tagHtml.replaceAll(templateVarTagName, tag.getName());
			tagHtml = tagHtml.replaceAll(templateVarTagLink, tag.getLink());
			tagHtml = tagHtml.replaceAll(templateVarTagWeight, Double.toString(tag.getWeight()));
			tagHtml = tagHtml.replaceAll(templateVarTagWeightInt, Integer.toString(tag.getWeightInt(cloud.getRounding())));
			tagHtml = tagHtml.replaceAll(templateVarTagNormScore, Double.toString(tag.getNormScore()));
			tagHtml = tagHtml.replaceAll(templateVarTagScore, Double.toString(tag.getScore()));
			tagHtml = tagHtml.replaceAll(templateVarTagScoreInt, Integer.toString(tag.getScoreInt()));
			tagHtml = tagHtml.replaceAll(templateVarTagNormScoreInt, Integer.toString(tag.getNormScoreInt()));
			html += tagHtml;
		}
		
		html += getHtmlTemplateBottom();
		
		return html;
	}

 	/**
	 * Converts a tag cloud in HTML/CSS code.
	 * The generated CSS code is inserted inline using the <code>&lt;style&gt;</code> tag.
	 * @param cloud The Cloud to convert.
  	 * @return The output HTML and CSS code.
	 */
	public String html(Cloud cloud) {
		return html(cloud, null);
	}

	/**
	 * Returns the CSS code associated with a tag cloud.
	 * @param cloud The Cloud object
	 * @return The output CSS code.
	 */
	public String css(Cloud cloud) {
		String css = "";

		int minWeight;
		int maxWeight;
		int numLevels;
		
		if (cloud.getRounding() == Cloud.Rounding.FLOOR) {
			minWeight = (int) Math.floor(cloud.getMinWeight());
			maxWeight = (int) Math.floor(cloud.getMaxWeight());
		} else if (cloud.getRounding() == Cloud.Rounding.ROUND) {
			minWeight = (int) Math.round(cloud.getMinWeight());
			maxWeight = (int) Math.round(cloud.getMaxWeight());
		} else { 
			minWeight = (int) Math.ceil(cloud.getMinWeight());
			maxWeight = (int) Math.ceil(cloud.getMaxWeight());
		}
		numLevels = maxWeight - minWeight + 1;
		
		for (String selector : cssFragments.keySet()) {
			List<CssStatement> cssStatements = cssFragments.get(selector);

			if (selector.length() == 0) {
				for (CssStatement cs : cssStatements) {
					if (cs.getProperty() != null) {
						css += cs.getProperty() + "\n\n";
					}
				}
			} else if (selector.contains(templateVarTagWeightInt)) {
				for (int level=0; level<numLevels; level++) {
					css += selector.replace(templateVarTagWeightInt, Integer.toString(level)) + " {\n";
					
					for (CssStatement cs : cssStatements) {
						String tempCss = generateVariableCss(cs, level, numLevels);
						
						if (tempCss != null && tempCss.length() != 0) {
							css += "\t" + tempCss;
						}
					}
					
					css += "}\n\n";
				}
			} else {
				for (int level=0; level<numLevels; level++) {
					
					css += selector + " {\n";
					
					for (CssStatement cs : cssStatements) {
						if (cs.getProperty() != null)
							css += "\t" + cs.getProperty() + "\n";
					}
					
					css += "}\n\n";
				}
			}
		}
		
		return css;
	}

	/**
	 * Returns the CSS code associated with a CssStatement and a given weight level.
	 * @param cs CssStatement object.
	 * @param level Weight level to consider
	 * @param numLevels Total number of level in the cloud
	 * @return CSS code associated with given CssStatement and weight level.
	 */
	protected String generateVariableCss(CssStatement cs, int level, int numLevels) {
		String css = "";
		
		if (cs.getProperty() != null) {
			if (cs.getLowValue() != null && cs.getHighValue() != null) {
				double low = cs.getLowValue().doubleValue();
				double high = cs.getHighValue().doubleValue();
				double value = low + ((high - low) * level) / (numLevels - 1);
				
				css += cs.getProperty() + ": ";
				if (cs.getLowValue() instanceof Double || cs.getLowValue() instanceof Float ||
						cs.getHighValue() instanceof Double || cs.getHighValue() instanceof Float) {
					css += value;
				} else {
					css += ((int) value);
				}

				css += ";\n";
			} else if (cs.getValues() != null && cs.getValues().length > 0) {
				int index = cs.getValues().length - numLevels + level;
				
				if (index < 0) {
					index = 0;
				}
				
				String value = cs.getValues()[index];
				
				if (value != null && value.length() != 0) {
					css += cs.getProperty() + ": " + value + ";\n";
				}
			} else {
				css += cs.getProperty() + "\n";
			}
		}
		
		return css;
	}

	/**
	 * @return Bottom HTML template.
	 */
	public String getHtmlTemplateBottom() {
		return htmlTemplateBottom;
	}

	/**
	 * Sets the Bottom HTML template that will inserted after all tags.
	 * @param htmlTemplateBottom Bottom HTML template.
	 */
	public void setHtmlTemplateBottom(String htmlTemplateBottom) {
		this.htmlTemplateBottom = htmlTemplateBottom;
	}

	/**
	 * @return Tag HTML template.
	 */
	public String getHtmlTemplateTag() {
		return htmlTemplateTag;
	}

	/**
	 * Sets the Tag HTML template that will repeated for each tag.
	 * Can contain one of the following specifier:
	 * 	<code>%tag-name%</code>	Tag name.
	 * 	<code>%tag-link%</code>	Tag link.
	 * 	<code>%tag-weight%</code>	Tag weight as a double.
	 * 	<code>%tag-weight-int%</code>	Tag weight as an intger.
	 * 	<code>%tag-score%</code>	Tag score.
	 * 	<code>%tag-norm-score%</code>	Tag normalized score.
	 * 
	 * @param htmlTemplateTag Tag HTML template.
	 */
	public void setHtmlTemplateTag(String htmlTemplateTag) {
		this.htmlTemplateTag = htmlTemplateTag;
	}

	/**
	 * @return Top HTML template.
	 */
	public String getHtmlTemplateTop() {
		return htmlTemplateTop;
	}

	/**
	 * Sets the Top HTML template that will inserted at the beginning of the 
	 * cloud.
	 * @param htmlTemplateTop Top HTML template.
	 */
	public void setHtmlTemplateTop(String htmlTemplateTop) {
		this.htmlTemplateTop = htmlTemplateTop;
	}

	/**
	 * @return The cssFragments map
	 */
	protected Map<String, List<CssStatement>> getCssFragments() {
		return cssFragments;
	}

	/**
	 * @param cssFragments The cssFragments map to set
	 */
	protected void setCssFragments(Map<String, List<CssStatement>> cssFragments) {
		this.cssFragments = cssFragments;
	}
}



/**
 * Generic CSS statement.
 */
class CssStatement {
	/** Contains the property name or the entire statement */
	private String property = null;
	
	/** Value of the property corresponding with the lowest weight level */
	private Number lowValue = null;
	
	/** Value of the property corresponding with the highest weight level */
	private Number highValue = null;

	/** Unit measure of the property value */
	private String unitMeasure = null;
	
	/** List of values corresponding with weight levels */
	private String [] values = null;

	/**
	 * Default constructor
	 */
	public CssStatement() {
	}

	/**
	 * Creates an object containing constant CSS statements.
	 * @param statements Generic statements.
	 */
	public CssStatement(String statements) {
		setProperty(statements);
	}

	/**
	 * Creates a CSS statement where a property varies proportionally with the weight level.
	 * @param property Property name.
	 * @param lowValue Value associated with the lowest weight level
	 * @param highValue Value associated with the highest weight level
	 */
	public CssStatement(String property, String lowValue, String highValue) {
		setProperty(property);
		setLowValue(lowValue);
		setHighValue(highValue);
	}

	/**
	 * Creates a CSS statement where a property varies depending on the weight level.
	 * @param property Property name.
	 * @param values Array of values associated with weight levels.
	 */
	public CssStatement(String property, String[] values) {
		setProperty(property);
		setValues(values);
	}

	/**
	 * @return Value associated with the highest weight level.
	 */
	public Number getHighValue() {
		return highValue;
	}
	
	/**
	 * Sets the property value associated with the highest weight level.
	 * @param highValue Highest value of the property.
	 */
	public void setHighValue(String highValue) {
		Scanner scanner = new Scanner(highValue);
		if (scanner.hasNextDouble()) {
			setHighValue(new Double(scanner.nextDouble()));
		} else if (scanner.hasNextInt()) {
			setHighValue(new Integer(scanner.nextInt()));
		}
		
		if (scanner.hasNext()) {
			setUnitMeasure(scanner.next());
		}
	}
	
	/**
	 * Sets the property value associated with the highest weight level.
	 * @param highValue Highest value of the property as a Number object.
	 */
	public void setHighValue(Number highValue) {
		this.highValue = highValue;
	}

	/**
	 * @return Value associated with the lowest weight level.
	 */
	public Number getLowValue() {
		return lowValue;
	}
	
	/**
	 * Sets the property value associated with the lowest weight level.
	 * @param lowValue Lowest value of the property.
	 */
	public void setLowValue(String lowValue) {
		Scanner scanner = new Scanner(lowValue);
		if (scanner.hasNextDouble()) {
			setLowValue(new Double(scanner.nextDouble()));
		} else if (scanner.hasNextInt()) {
			setLowValue(new Integer(scanner.nextInt()));
		}
		
		if (scanner.hasNext()) {
			setUnitMeasure(scanner.next());
		}
	}
	
	/**
	 * Sets the property value associated with the lowest weight level.
	 * @param lowValue Lowest value of the property as Number object.
	 */
	public void setLowValue(Number lowValue) {
		this.lowValue = lowValue;
	}

	/**
	 * @return the property name or the complete statement. 
	 */
	public String getProperty() {
		return property;
	}
	
	/**
	 * Sets the property name of the statement or the entire statement.
	 * @param property The property name or statement.
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * @return Array of values associated with weight levels.
	 */
	public String[] getValues() {
		return values;
	}

	/**
	 * Sets a list of values associated with weight levels.
	 * @param values Array of values
	 */
	public void setValues(String[] values) {
		this.values = values;
	}

	/**
	 * @return Unit measure of the property.
	 */
	public String getUnitMeasure() {
		return unitMeasure;
	}

	/**
	 * Sets the unit measure of the property.
	 * @param unitMeasure Unit measure
	 */
	public void setUnitMeasure(String unitMeasure) {
		this.unitMeasure = unitMeasure;
	}

}
