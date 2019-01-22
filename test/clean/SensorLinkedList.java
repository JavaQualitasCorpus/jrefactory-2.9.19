
/**
 * Title:        Prototype Shun Daemon Wizard<p>
 * Description:  This project is a prototype for a shun daemon configuration file wizard.
 * The basis for this wizard is Andrew Molitor's prototype shell script.<p>
 * Copyright:    Copyright (c) 2000Joi Ellis<p>
 * Company:      Aravox Technologies<p>
 * @author Joi Ellis
 * @version 1.0
 */
package com.aravox.ShunWizard;

import java.util.Collection;
import java.util.LinkedList;
import java.util.ListIterator;

public class SensorLinkedList extends AravoxLinkedList {

  public SensorLinkedList() {
    super();
  }

  public SensorLinkedList(Collection c) {
    super(c);
  }

  public Browsable sampleObject() {
    return SensorNode.sampleObject();
  }

/*  public Browsable findName(String name) {
    int index = 0;
    Browsable node;
    if (name==null) {
      return null;
    } else {
      ListIterator li = listIterator();
      while (li.hasNext()) {
        node = (Browsable) li.next();
        if (node.getName().equalsIgnoreCase(name)) {
          return node;
        }
      }
    }
    return null;
  }
*/
}
