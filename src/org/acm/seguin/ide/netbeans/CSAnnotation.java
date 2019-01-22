/*
 * JRefactoryAnnotation.java
 *
 * Created on 04 May 2004, 13:15
 */

package org.acm.seguin.ide.netbeans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.openide.text.Annotation;
import org.openide.text.Annotatable;
import org.openide.text.Line;


/**
 *
 * @author  mikea
 */
public class CSAnnotation extends Annotation implements PropertyChangeListener {

	/** The error message shown on mouseover on the pmd icon */
	private String errormessage = null;
	private static List annotations = new ArrayList();
	
	private CSAnnotation() {}
	
	public static final CSAnnotation getNewInstance() {
		CSAnnotation jra = new CSAnnotation();
		annotations.add( jra );
		return jra;
	}
	
	public static final void clearAll() {
		Iterator iterator = annotations.iterator();
		while( iterator.hasNext() ) {
			((Annotation)iterator.next()).detach();
		}
		annotations.clear();
	}

	/**
	 * The annotation type.
	 *
	 * @return pmd-annotation
	 */
	public String getAnnotationType() {
		return "jrefactory-cs-annotation";
	}


	/**
	 * Sets the current errormessage
	 *
	 * @param message the errormessage
	 */
	public void setErrorMessage( String message ) {
		errormessage = message;
	}


	/**
	 * A short description of this annotation
	 *
	 * @return the short description
	 */
	public String getShortDescription() {
		return errormessage;
	}


	/**
	 * Invoked when the user change the content on the line where the annotation is
	 * attached
	 *
	 * @param propertyChangeEvent the event fired
	 */
	public void propertyChange( PropertyChangeEvent propertyChangeEvent ) {
            String type = propertyChangeEvent.getPropertyName();
            if (type == null || type == Annotatable.PROP_TEXT) {
		Line line = ( Line )propertyChangeEvent.getSource();
		line.removePropertyChangeListener( this );
		detach();
            }
	}
}
