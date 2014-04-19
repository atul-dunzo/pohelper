package com.testhelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Descriptors are small objects that control a single unit of the generation process and serve as a
 * handle to an element to be generated.  The HintsDescriptor stores the information for a single
 * Analysis record corresponding to a specific HTML tag in the DOM which is targeted for an
 * Analysis record to be displayed to the user.
 * User: pgrandje
 * Date: 9/16/12
 */
public class HintsDescriptor {

    public enum LocatorType {ID, CSS_LOCATOR};

    // TODO: Move the hints file indicators to they're own class.
    static public final String NEW_TAG_DELIMITER = "<*** UI Element ***>";
    static public final char IGNORE_CHAR = '*';
    static public final String TEXT_MARKER = "Text: ";
    static public final String ATTRIBUTE_MARKER = "Attribute: ";
    static public final String LOCATOR_MARKER = "Locator: ";


    private String tag;
    private String text;
    // TODO: Change the Hints attributes to use a HashMap--I don't need a separate HintsAttribute class just for named pairs.
    private ArrayList<HintsAttribute> attributes;
    private LocatorType locatorType;
    private String locatorValue;

    public class Locator {
        LocatorType type;
        String locatorValue;
        Locator(LocatorType type, String value) {
            this.type = type;
            this.locatorValue = value;
        }
    }

    // TODO: Make sure the HintsDesriptor internal Locator is set by the setters below.
    private Locator locator;


    public HintsDescriptor() {
        attributes = new ArrayList<HintsAttribute>();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<HintsAttribute> getAttributes() {
        return this.attributes;
    }

    public void addAttribute(HintsAttribute attribute) {
        this.attributes.add(attribute);
    }


    public LocatorType getLocatorType() {
        return locatorType;
    }

    public void setLocatorType(LocatorType locatorType) {
        this.locatorType = locatorType;
    }

    public String getLocatorValue() {
        return locatorValue;
    }

    public Locator getLocator() {
        locator = new Locator(locatorType, locatorValue);
        return locator;
    }

    public void setLocatorValue(String locatorValue) {
        this.locatorValue = locatorValue;
    }

}